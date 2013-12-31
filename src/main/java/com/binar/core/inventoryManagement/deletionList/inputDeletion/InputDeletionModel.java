package com.binar.core.inventoryManagement.deletionList.inputDeletion;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.OptimisticLockException;

import com.avaje.ebean.EbeanServer;
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
	}
	
	public Map<String,String> getGoodsData(){
		List<Goods> goodsList=server.find(Goods.class).findList();
		Map<String, String> data=new TreeMap<String, String>();
		for(Goods goods:goodsList){
			data.put(goods.getIdGoods(), goods.getName());
		}
		return data;
	}
	
	public String insertData(FormDeletion data){
		//mulai transaksi
		
		server.beginTransaction();
		try {
			SupplierGoods suppGoods=server.find(SupplierGoods.class, data.getSuppGoodsId());	
			//mulai insert
			DeletedGoods deletion = new DeletedGoods();
			deletion.setQuantity(Integer.parseInt(data.getQuantity())); 
			deletion.setDeletionDate(data.getdeletionDate());
			deletion.setTimestamp(new Date());	
			deletion.setInformation(data.getInformation());
			deletion.setSupplierGoods(suppGoods);
			server.save(deletion);
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
	
	public String saveEdit(FormDeletion data, int idDel){
		//mulai transaksi
		
		server.beginTransaction();
		try {
			DeletedGoods deletion=server.find(DeletedGoods.class, idDel);
			deletion.setQuantity(Integer.parseInt(data.getQuantity()));
			deletion.setDeletionDate(data.getdeletionDate());
			deletion.setInformation(data.getInformation());			
			
			server.update(deletion);	
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
	
	public DeletedGoods getSingleDeletion(int id){
		return server.find(DeletedGoods.class, id);
	}
}
