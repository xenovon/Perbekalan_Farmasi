package com.binar.core.inventoryManagement.receptionList.inputReception;

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
import com.binar.entity.GoodsConsumption;
import com.binar.entity.GoodsReception;
import com.binar.entity.Invoice;
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
	
	public Map<Integer,String> getInvoiceItemsData(Date startDate, Date endDate){
		List<Invoice> invoices=server.find(Invoice.class).where().between("invoiceDate", startDate, endDate).findList();
		Map<Integer, String> data=new HashMap<Integer, String>();
		for(Invoice invoice:invoices){
			data.put(invoice.getIdInvoice(), invoice.getInvoiceName());
		}
		return data;
	}
	
	public Map<Integer,String> getGoodsData(int idInvoice){
		Invoice invoice=server.find(Invoice.class, idInvoice);
		Map<Integer, String> data=new HashMap<Integer, String>();
		for(InvoiceItem item:invoice.getInvoiceItem()){
			data.put(item.getIdInvoiceItem(),item.getPurchaseOrderItem().getSupplierGoods().getGoods().getName());
		}
		return data;
	}
	
	public List<String> insertData(FormReception data){
		List<String> error=data.validate();
		//jika tidak valid, maka kembalikan errornya
		if(error!=null){
			return error;
		}
		
		error=new ArrayList<String>();
		//mulai transaksi
		server.beginTransaction();
		try {
			InvoiceItem invoItem=server.find(InvoiceItem.class, data.getInvoiceItemId());			
			//mulai insert
			GoodsReception reception = new GoodsReception();
			reception.setQuantityReceived(data.getQuantityInt()); 
			reception.setDate(data.getReceptionDate());
			reception.setExpiredDate(data.getExpiredDate());
			reception.setTimestamp(new Date());	
			reception.setInformation(data.getInformation());
			reception.setInvoiceItem(invoItem);
			reception.setTimestamp(new Date());
			
			Goods goods=invoItem.getPurchaseOrderItem().getSupplierGoods().getGoods();
			int currentStock=goods.getCurrentStock()+data.getQuantityInt();
			goods.setCurrentStock(currentStock);
			
			reception.setStockQuantity(currentStock);
			server.update(goods);
			server.save(reception);
			server.commitTransaction();
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
			return error.size()==0?null:error;
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
	
	public List<String> saveEdit(FormReception data, int idRecs){
		List<String> error=data.validate();
		//jika tidak valid, maka kembalikan errornya
		if(error!=null){
			return error;
		}
		error=new ArrayList<String>();

		//mulai transaksi
		
		server.beginTransaction();
		try {
			GoodsReception reception=server.find(GoodsReception.class, idRecs);
			int oldQuantity=reception.getQuantityReceived();
			reception.setQuantityReceived(Integer.parseInt(data.getQuantity()));
			reception.setDate(data.getReceptionDate());
			reception.setInformation(data.getInformation());			
			reception.setExpiredDate(data.getExpiredDate());
			
			Goods goods=reception.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getGoods();
			int currentStock=goods.getCurrentStock()-oldQuantity+data.getQuantityInt();
			goods.setCurrentStock(currentStock);
			reception.setStockQuantity(currentStock);
			
			server.update(goods);
			server.update(reception);	
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
			return error.size()==0?null:error;
		}
	}
	
	public GoodsReception getSingleReception(int id){
		return server.find(GoodsReception.class, id);
	}
	
	public Map<Integer, String> getSingleReceptionList(GoodsReception reception){
		Map<Integer, String> data=new HashMap<Integer, String>();
		data.put(reception.getInvoiceItem().getIdInvoiceItem(),reception.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getGoods().getName());
		return data;
	}
}
