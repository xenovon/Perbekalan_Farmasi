package com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.OptimisticLockException;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.Insurance;
import com.binar.entity.Manufacturer;
import com.binar.entity.ReqPlanning;
import com.binar.entity.Supplier;
import com.binar.entity.SupplierGoods;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.FormLayout;

public class InputFormModel {

	GeneralFunction function;
	EbeanServer server;
	GetSetting setting;
	public InputFormModel(GeneralFunction function){
		this.function=function;
		this.server=function.getServer();
		this.setting=function.getSetting();
	}
	
	public List<String> validate(FormLayout layout){
		//inisiasi variabel
		/*
		 * input
		 * input good
		 * input quantitiy
		 * input supplier
		 * input producer
		 * input price
		 * input information
		 */
		return null;
	}
	public Goods getGoods(String idGoods){
		return server.find(Goods.class, idGoods);
	}
	//Mendapatkan data daftar barang untuk combo box
	public Map<String,String> getGoodsData(){
		List<Goods> goodsList=server.find(Goods.class).findList();
		Map<String, String> data=new TreeMap<String, String>();
		for(Goods goods:goodsList){
			//get akses
			if(goods.getInsurance().isShowInDropdown()){
				data.put(goods.getIdGoods(), goods.getName() + " - "+goods.getInsurance().getName());				
			}else{
				data.put(goods.getIdGoods(), goods.getName());				
			}
		}
		return data;
	}
	
	public Map<String, String> getSupplierData(){
		List<Supplier> supplierList=server.find(Supplier.class).findList();
		Map<String, String> data=new TreeMap<String, String>();
		for(Supplier supplier:supplierList){
			data.put(Integer.toString(supplier.getIdSupplier()), supplier.getSupplierName());
		}
		return data;
		
	}
	public Map<String, String> getManufacturer(){
		List<Manufacturer> manufacturerList=server.find(Manufacturer.class).findList();
		Map<String, String> data=new TreeMap<String, String>();
		for(Manufacturer manufacturer:manufacturerList){
			data.put(Integer.toString(manufacturer.getIdManufacturer()),
					manufacturer.getManufacturerName());
		}
		return data;
	}
	public String insertData(List<FormData> data){
		String returnValue=null;
		server.beginTransaction();
		for(FormData datum:data){
			returnValue=insertData(datum, false);
			if(returnValue!=null){
				break;
			}
		}
		if(returnValue!=null){
			server.rollbackTransaction();
		}else{
			server.commitTransaction();
		}
		server.endTransaction();
		return returnValue;

	}
	//menyimpan data analisis kebutuhan
	public String insertData(FormData data, boolean isSingle){
		//mulai transaksi
		
		//jika data tunggal maka menggunakan sistem transaksi
		//jika jamak, maka menggunakan transaksi di insertData(List<FormData> data)
		if(isSingle){
			server.beginTransaction();			
		}
		try {
			SupplierGoods supplierGoods=getSupplierGoods(data.getSupplierId(), 
										data.getManufacturId(), data.getGoodsId());
			if(supplierGoods!=null){  //jika suppliergoods udah ada
				supplierGoods.setLastPrice(Double.parseDouble(data.getPrice()));
				supplierGoods.setLastUpdate(new Date()); //tanggal sekarang
				server.update(supplierGoods);
			}else{ //jika belum ada, buat item suppliergoods baru
				Goods goods=server.find(Goods.class, data.getGoodsId());
				Manufacturer manufacturer=server.find(Manufacturer.class, Integer.parseInt(data.getManufacturId()));
				Supplier supplier=server.find(Supplier.class, Integer.parseInt(data.getSupplierId()));
				
				supplierGoods=new SupplierGoods();
				supplierGoods.setGoods(goods);
				supplierGoods.setLastPrice(Double.parseDouble(data.getPrice()));
				supplierGoods.setLastUpdate(new Date());
				supplierGoods.setManufacturer(manufacturer);
				supplierGoods.setSupplier(supplier);
				
				server.save(supplierGoods);
			}
			//mulai insert requirement planning
			ReqPlanning reqPlanning=new ReqPlanning();
			reqPlanning.setAcceptance(0);
			reqPlanning.setComment("init Value");
			reqPlanning.setAcceptedQuantity(0);
			reqPlanning.setInformation(data.getInformation());
			reqPlanning.setPeriod(function.getDate().parseDateMonth(data.getPeriode()).toDate());
			reqPlanning.setPriceEstimation(Double.parseDouble(data.getPrice()));
			
			double priceDouble=Double.parseDouble(data.getPrice());
			if(data.isPpn()){
				reqPlanning.setPriceEstimationPPN(priceDouble);
				double price=(100/(100+setting.getPPN())*priceDouble);
				reqPlanning.setPriceEstimation(price);
			}else{
				reqPlanning.setPriceEstimation(priceDouble);
				double pricePPN=(priceDouble+(priceDouble*setting.getPPN()/100));
				reqPlanning.setPriceEstimationPPN(pricePPN);
			}

			reqPlanning.setQuantity(Integer.parseInt(data.getQuantity()));
			reqPlanning.setSupplierGoods(supplierGoods);
			reqPlanning.setTimestamp(new Date());
			reqPlanning.setPurchaseOrderCreated(false);
			
			
			
			server.save(reqPlanning);
			
			SupplierGoods supp=reqPlanning.getSupplierGoods();
			supp.setLastPrice(reqPlanning.getPriceEstimationPPN());
			server.update(supp);
			if(isSingle){
				server.commitTransaction();				
			}
			return null;
		} catch (NumberFormatException e) {
			if(isSingle){
				server.rollbackTransaction();				
			}
			e.printStackTrace();
			return "Kesalahan pengisian angka :"+e.getMessage();
		} catch (OptimisticLockException e) {
			if(isSingle){
				server.rollbackTransaction();				
			}
			e.printStackTrace();
			return "Kesalahan tulis data ke database " + e.getMessage();
		}catch(Exception e){
			if(isSingle){
				server.rollbackTransaction();				
			}
			e.printStackTrace();
			return "Kesalahan submit : " + e.getMessage();
		
		}finally{
			if(isSingle){				
				server.endTransaction();			
			}
		}
	}
	public String getGoodsUnit(String goodsId){
		Goods goods=server.find(Goods.class,goodsId);
		if(goods!=null){
			return goods.getUnit();
		}else{
			return "";
		}
	}
	public String getGoodsPrice(String supplierId, String manufacturerId, String goodsId){
		try {
			Supplier supplier=server.find(Supplier.class, Integer.parseInt(supplierId));
			Manufacturer manufacturer = server.find(Manufacturer.class, Integer.parseInt(manufacturerId));
			Goods goods=server.find(Goods.class, goodsId);
			SupplierGoods supplierGoods=server.find(SupplierGoods.class).
										where().eq("supplier", supplier).eq("manufacturer", manufacturer)
										.eq("goods", goods).findUnique();
			if(supplierGoods!=null){
				return String.valueOf(supplierGoods.getLastPrice());
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	//untuk menretrieve data supplier goods
	private SupplierGoods getSupplierGoods(String supplierId, String manufacturerId, String goodsId){
		
		Supplier supplier=server.find(Supplier.class, Integer.parseInt(supplierId));
		
		Manufacturer manufacturer = server.find(Manufacturer.class, Integer.parseInt(manufacturerId));
		
		Goods goods=server.find(Goods.class, goodsId);
		
		SupplierGoods supplierGoods=server.find(SupplierGoods.class).
									where().eq("supplier", supplier).eq("manufacturer", manufacturer)
									.eq("goods", goods).findUnique();
		return supplierGoods;
	}
	//mengubah data analisis kebutuhan, kodenya mirip dengan insert
	public String saveEdit(FormData data, int idReq){
		//mulai transaksi
		
		server.beginTransaction();
		try {
			SupplierGoods supplierGoods=getSupplierGoods(data.getSupplierId(), 
										data.getManufacturId(), data.getGoodsId());
			if(supplierGoods!=null){  //jika suppliergoods udah ada
				supplierGoods.setLastPrice(Double.parseDouble(data.getPrice()));
				supplierGoods.setLastUpdate(new Date()); //tanggal sekarang
				server.update(supplierGoods);
			}else{ //jika belum ada, buat item suppliergoods baru
				Goods goods=server.find(Goods.class, data.getGoodsId());
				Manufacturer manufacturer=server.find(Manufacturer.class, Integer.parseInt(data.getManufacturId()));
				Supplier supplier=server.find(Supplier.class, Integer.parseInt(data.getSupplierId()));
				
				supplierGoods=new SupplierGoods();
				supplierGoods.setGoods(goods);
				supplierGoods.setLastPrice(Double.parseDouble(data.getPrice()));
				supplierGoods.setLastUpdate(new Date());
				supplierGoods.setManufacturer(manufacturer);
				supplierGoods.setSupplier(supplier);
				
				server.save(supplierGoods);
			}
			//mulai insert requirement planning
			ReqPlanning reqPlanning=server.find(ReqPlanning.class, idReq);
			reqPlanning.setAcceptance(0);
			reqPlanning.setAcceptedQuantity(0);
			reqPlanning.setInformation(data.getInformation());
			reqPlanning.setPeriod(function.getDate().parseDateMonth(data.getPeriode()).toDate());
			
			double priceDouble=Double.parseDouble(data.getPrice());
			if(data.isPpn()){
				reqPlanning.setPriceEstimationPPN(priceDouble);
				double price=(100/(100+setting.getPPN())*priceDouble);
				reqPlanning.setPriceEstimation(price);
			}else{
				reqPlanning.setPriceEstimation(priceDouble);
				double pricePPN=(priceDouble+(priceDouble*setting.getPPN()/100));
				reqPlanning.setPriceEstimationPPN(pricePPN);
			}


			
			reqPlanning.setQuantity(Integer.parseInt(data.getQuantity()));
			reqPlanning.setSupplierGoods(supplierGoods);
			
			server.update(reqPlanning);
			
			SupplierGoods supp=reqPlanning.getSupplierGoods();
			supp.setLastPrice(reqPlanning.getPriceEstimationPPN());
			server.update(supp);

			server.commitTransaction();
			return null;
		} catch (NumberFormatException e) {
			server.rollbackTransaction();
			e.printStackTrace();
			return "Kesalahan pengisian angka :"+e.getMessage();
		} catch (OptimisticLockException e) {
			server.rollbackTransaction();
			e.printStackTrace();
			return "Kesalahan tulis data ke database " + e.getMessage();
		}catch(Exception e){
			server.rollbackTransaction();
			e.printStackTrace();
			return "Kesalahan submit : " + e.getMessage();
		
		}finally{
			server.endTransaction();			
		}
	}
	public ReqPlanning getSingleReqPlanning(int id){
		return server.find(ReqPlanning.class, id);
	}
	
	
	
}
