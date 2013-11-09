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
import com.binar.entity.Manufacturer;
import com.binar.entity.ReqPlanning;
import com.binar.entity.Supplier;
import com.binar.entity.SupplierGoods;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.FormLayout;

public class InputFormModel {

	GeneralFunction function;
	EbeanServer server;
	
	public InputFormModel(GeneralFunction function){
		this.function=function;
		this.server=function.getServer();
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
	//Mendapatkan data daftar barang untuk combo box
	public Map<String,String> getGoodsData(){
		List<Goods> goodsList=server.find(Goods.class).findList();
		Map<String, String> data=new TreeMap<String, String>();
		for(Goods goods:goodsList){
			data.put(goods.getIdGoods(), goods.getName());
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
	//menyimpan data analisis kebutuhan
	public String insertData(FormData data){
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
				supplierGoods.setLastPrice(Integer.parseInt(data.getPrice()));
				supplierGoods.setLastUpdate(new Date());
				supplierGoods.setManufacturer(manufacturer);
				supplierGoods.setSupplier(supplier);
				
				server.save(supplierGoods);
			}
			//mulai insert requirement planning
			ReqPlanning reqPlanning=new ReqPlanning();
			reqPlanning.setAccepted(false);
			reqPlanning.setAcceptedQuantity(0);
			reqPlanning.setInformation(data.getInformation());
			reqPlanning.setPeriod(function.getDate().parseDateMonth(data.getPeriode()).toDate());
			reqPlanning.setPriceEstimation(Double.parseDouble(data.getPrice()));
			reqPlanning.setQuantity(Integer.parseInt(data.getQuantity()));
			reqPlanning.setSupplierGoods(supplierGoods);
			reqPlanning.setTimestamp(new Date());
			
			server.save(reqPlanning);
			
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
	
	
	
	
}
