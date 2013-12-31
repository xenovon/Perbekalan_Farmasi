package com.binar.core.inventoryManagement.receptionList.inputReception;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.OptimisticLockException;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.GoodsReception;
import com.binar.entity.InvoiceItem;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.binar.generalFunction.TextManipulator;

public class InputReceptionModel {

	GeneralFunction function;
	EbeanServer server;
	TextManipulator text;
	GetSetting setting;
	
	public InputReceptionModel(GeneralFunction function){
		this.function=function;
		this.server=function.getServer();
		this.setting=function.getSetting();
		this.text=function.getTextManipulator();
	}
	
	public Map<String,String> getInvoiceItemsData(){
		List<GoodsReception> goodRecsList=server.find(GoodsReception.class).findList();
		Map<String, String> data=new TreeMap<String, String>();
		for(GoodsReception goodRecs:goodRecsList){
			data.put(text.intToAngka(goodRecs.getIdGoodsReceipt()), text.intToAngka(goodRecs.getIdGoodsReceipt()));
		}
		return data;
	}
	
	public Map<String,String> getGoodsData(){
		List<Goods> goodsList=server.find(Goods.class).findList();
		Map<String, String> data=new TreeMap<String, String>();
		for(Goods goods:goodsList){
			data.put(goods.getIdGoods(), goods.getName());
		}
		return data;
	}
	
	public String insertData(FormReception data){
		//mulai transaksi
		
		server.beginTransaction();
		try {
			InvoiceItem invoItem=server.find(InvoiceItem.class, data.getInvoiceItemId());			
			//mulai insert
			String goodsSelected = invoItem.getPurchaseOrderItem().getSupplierGoods().getGoods().getIdGoods();
			GoodsReception reception = new GoodsReception();
			reception.setQuantityReceived(Integer.parseInt(data.getQuantity())); 
			reception.setDate(data.getReceptionDate());
			reception.setExpiredDate(data.getExpiredDate());
			reception.setTimestamp(new Date());	
			reception.setInformation(data.getInformation());
			reception.setInvoiceItem(invoItem);
			
			server.save(reception);
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
	
	public String getGoodsbyInvoiceItem(String invoiceItemId){
		InvoiceItem invoItem=server.find(InvoiceItem.class,invoiceItemId);
		if(invoItem!=null){
			return invoItem.getPurchaseOrderItem().getSupplierGoods().getGoods().getIdGoods();
		}else{
			return "";
		}
	}
	
	public String saveEdit(FormReception data, int idRecs){
		//mulai transaksi
		
		server.beginTransaction();
		try {
			GoodsReception reception=server.find(GoodsReception.class, idRecs);
			reception.setQuantityReceived(Integer.parseInt(data.getQuantity()));
			reception.setDate(data.getReceptionDate());
			reception.setInformation(data.getInformation());			
			reception.setExpiredDate(data.getExpiredDate());
			server.update(reception);	
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
	
	public GoodsReception getSingleReception(int id){
		return server.find(GoodsReception.class, id);
	}
}
