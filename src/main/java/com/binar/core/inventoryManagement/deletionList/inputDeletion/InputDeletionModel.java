package com.binar.core.inventoryManagement.deletionList.inputDeletion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.OptimisticLockException;

import com.avaje.ebean.EbeanServer;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.InvoiceItem;
import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.entity.SupplierGoods;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.binar.generalFunction.TextManipulator;

public class InputDeletionModel {
	
	GeneralFunction function;
	EbeanServer server;
	TextManipulator text;
	GetSetting setting;
	
	public InputDeletionModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
		this.setting=function.getSetting();
		this.text=function.getTextManipulator();
	}
	
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
	public List<String> insertData(List<FormDeletion> data){
		List<String> returnValue = null;
		server.beginTransaction();
		for(FormDeletion datum:data){
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

	
	public List<String> insertData(FormDeletion data, boolean isSingle){
		//mulai transaksi
		List<String> error=data.validate();
		if(error!=null){
			return error;
		}else{
			error=new ArrayList<String>();
		}
		if(isSingle){
			server.beginTransaction();			
		}
		try {
			Goods goods=server.find(Goods.class, data.getIdGoods());	
			//mulai insert
			DeletedGoods deletion = new DeletedGoods();
			deletion.setQuantity(Integer.parseInt(data.getQuantity())); 
			deletion.setDeletionDate(data.getDeletionDate());
			deletion.setTimestamp(new Date());	
			deletion.setInformation(data.getInformation());
			deletion.setGoods(goods);
			deletion.setPrice(Double.parseDouble(data.getPrice()));
			deletion.setComment("Init Value");
			server.save(deletion);
			
			if(isSingle){
				server.commitTransaction();				
			}
			return null;
			
		} catch (NumberFormatException e) {
			if(isSingle){
				server.rollbackTransaction();
			}
			e.printStackTrace();
			error.add("Kesalahan pengisian angka :"+e.getMessage());
		} catch (OptimisticLockException e) {
			if(isSingle){
				server.rollbackTransaction();
			}
			e.printStackTrace();
			error.add("Kesalahan tulis data ke database " + e.getMessage());
		}catch(Exception e){
			if(isSingle){				
				server.rollbackTransaction();
			}
			e.printStackTrace();
			error.add("Kesalahan submit : " + e.getMessage());
		
		}finally{
			if(isSingle){
				server.endTransaction();							
			}
		}
		return error.size()==0?null:error;
	}
	
	public String getGoodsUnit(String goodsId){
		Goods goods=server.find(Goods.class,goodsId);
		if(goods!=null){
			return goods.getUnit();
		}else{
			return "";
		}
	}
	
	public  List<String>  saveEdit(FormDeletion data){
		//mulai transaksi
		List<String> error=data.validate();
		if(error!=null){
			return error;
		}else{
			error=new ArrayList<String>();
		}
		server.beginTransaction();
		try {
			DeletedGoods deletion=server.find(DeletedGoods.class, data.getDeletionId());
			deletion.setQuantity(Integer.parseInt(data.getQuantity()));
			deletion.setDeletionDate(data.getDeletionDate());
			deletion.setInformation(data.getInformation());			
			deletion.setPrice(Double.parseDouble(data.getPrice()));

			server.update(deletion);	
			server.commitTransaction();
			return null;
		} catch (NumberFormatException e) {
			server.rollbackTransaction();
			e.printStackTrace();
			error.add("Kesalahan pengisian angka :"+e.getMessage());
		} catch (OptimisticLockException e) {
			server.rollbackTransaction();
			e.printStackTrace();
			error.add("Kesalahan tulis data ke database " + e.getMessage());
		}catch(Exception e){
			server.rollbackTransaction();
			e.printStackTrace();
			error.add("Kesalahan submit : " + e.getMessage());
		}finally{
			server.endTransaction();			
		}
		
		return error.size()==0?null:error;
	}
	
	public DeletedGoods getSingleDeletion(int id){
		return server.find(DeletedGoods.class, id);
	}
	
	public String deleteDeletionGoods(int idDeletion){
		try {
			DeletedGoods deleted=server.find(DeletedGoods.class, idDeletion);
			server.delete(deleted);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return "Kesalahan menghapus data "+e.getMessage();
		}
	}
	
	public Goods getGoods(String idGoods){
		if(idGoods==null){
			return null;
		}
		return server.find(Goods.class, idGoods);
	}
	//mendapatkan daftar harga untuk ditampilkan saat penghapusan
	public String generatePriceGuide(String idGoods){
		try {
			Goods goods=server.find(Goods.class, idGoods);
			String returnValue="Harga untuk "+goods.getName() +"<br>";
			

			List<SupplierGoods> supplierGoods=server.find(SupplierGoods.class).where().eq("goods",goods).findList();
			
			for(SupplierGoods datum:supplierGoods){
				returnValue=returnValue+datum.getSupplier().getSupplierName()+" - "+text.doubleToRupiah(datum.getLastPrice())+"</br>";
			}
			
			if(supplierGoods.size()==0){
				return "";
			}else{
				return returnValue;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
	}
}
