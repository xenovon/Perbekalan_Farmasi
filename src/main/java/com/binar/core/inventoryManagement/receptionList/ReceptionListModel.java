package com.binar.core.inventoryManagement.receptionList;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

















import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.GoodsReception;
import com.binar.generalFunction.GeneralFunction;

public class ReceptionListModel {

	GeneralFunction generalFunction;
	EbeanServer server;
	
	public ReceptionListModel(GeneralFunction function){
		this.generalFunction=function;
		server=generalFunction.getServer();	
	}
	
	public int getTableDataQuantity(DateTime periode, String goodsSelected){
		Map<Goods, Integer> quantityList=getTableData(periode);
		for(Map.Entry<Goods, Integer> entry:quantityList.entrySet()){
			if(entry.getKey().getIdGoods().equals(goodsSelected)){
				return entry.getValue();
			}
		}
		return 0;
	}
	
	Map<Goods, Integer> getTableData(DateTime periode) {
		System.out.println("Mendapatkan periode");
		Date startDate=periode.toDate();
		Date endDate=periode.withDayOfMonth(periode.dayOfMonth().getMaximumValue()).toDate();
		List<GoodsReception> receptionOfMonth=server.find(GoodsReception.class).where().
				between("date", startDate, endDate).findList();
		System.out.println("Start Date : "+startDate);
		System.out.println("End Date : "+endDate);
		
		Map<Goods, Integer> returnValue=new HashMap<Goods, Integer>();		
		for (GoodsReception reception : receptionOfMonth){
			if(returnValue.containsKey(reception.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getGoods())){
				int quantity=returnValue.get(reception.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getGoods());
				int quantityNow=quantity+reception.getQuantityReceived();
				
				returnValue.remove(reception.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getGoods());
				returnValue.put(reception.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getGoods(), quantityNow);
			}else{
				returnValue.put(reception.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getGoods(), reception.getQuantityReceived());
			}
		}
		return returnValue; 
	}

	public int getTableDataQuantityByDate(DateTime periode, DateTime dateSelected){
		Map<DateTime, Integer> quantityList=getTableDataByDate(periode);
		for(Map.Entry<DateTime, Integer> entry:quantityList.entrySet()){
			if(entry.getKey().getDayOfMonth()==dateSelected.getDayOfMonth()){
				return entry.getValue();
			}
		}
		return 0;
	}
	
	public Map<DateTime, Integer> getTableDataByDate(DateTime periode){
		System.out.println("Mendapatkan periode");
		Date startDate=periode.toDate();
		Date endDate=periode.withDayOfMonth(periode.dayOfMonth().getMaximumValue()).toDate();
		List<GoodsReception> receptionOfMonth=server.find(GoodsReception.class).where().
				between("date", startDate, endDate).findList();
		System.out.println("Start Date : "+startDate);
		System.out.println("End Date : "+endDate);
		
		Map<DateTime, Integer> returnValue = new HashMap<DateTime, Integer>();
		for (GoodsReception reception : receptionOfMonth){
			Calendar recDate=Calendar.getInstance();
			recDate.setTime(reception.getDate());
			int day=recDate.get(Calendar.DAY_OF_MONTH);			
			DateTime key=getKeyDateTime(returnValue, day);
		
			if(key==null){
				int quantity=1;
				returnValue.put(new DateTime(reception.getDate()), quantity);
			}else{
				Integer quantityNow=returnValue.get(key);
				quantityNow = quantityNow + 1;
				
				returnValue.remove(key);
				returnValue.put(key, quantityNow);
			}
		}
		return returnValue; 
	}
	
	private DateTime getKeyDateTime(Map<DateTime, Integer> input, int day){
        for (Map.Entry<DateTime, Integer> entry : input.entrySet()) {
        	if(entry.getKey().getDayOfMonth()==day){
        		return entry.getKey();
        	}
        }
        return null;
	}
	
	public List<GoodsReception> getReceptions(String idGoods, DateTime periode){
		Goods goods=getGoods(idGoods);
		if(goods==null){
			return null;
		}
		Date startDate=periode.withDayOfMonth(periode.dayOfMonth().getMinimumValue()).toDate();
		Date endDate=periode.withDayOfMonth(periode.dayOfMonth().getMaximumValue()).toDate();
		
		return server.find(GoodsReception.class).where().
				between("date", startDate, endDate).
				eq("invoiceItem.purchaseOrderItem.supplierGoods.goods", goods).order().asc("date").findList();	
	
	}
	
	private GoodsReception getGoodsReception(String idReception) {
		return server.find(GoodsReception.class, idReception);
	}

	private Goods getGoods(String idGoods) {
		return server.find(Goods.class, idGoods);
	}

	public int getNumberOfReceptionsByDate(DateTime periode){		
		int numbers =getReceptionsByDate(periode).size();
		System.out.println("Jumlah data penerimaan : "+numbers);
		return numbers;
	}
	
	public List<GoodsReception> getReceptionsByDate(DateTime periode) {
		Date startDate=periode.withHourOfDay(periode.hourOfDay().getMinimumValue()).toDate();
		Date endDate=periode.withHourOfDay(periode.hourOfDay().getMaximumValue()).toDate();
		
		return server.find(GoodsReception.class).where().
				between("date", startDate, endDate).
				order().asc("date").findList();	
	}

	public boolean deleteGoodsReception(int recIdFinal) {
		try {
		
			GoodsReception goodsRec= server.find(GoodsReception.class, recIdFinal);
			int quantity=goodsRec.getQuantityReceived();
			
			String idGoods=goodsRec.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getGoods().getIdGoods();
			server.delete(goodsRec);
			Goods goods=server.find(Goods.class, idGoods);
			int currentStock=goods.getCurrentStock();
			goods.setCurrentStock(currentStock-quantity);
			server.update(goods);
			generalFunction.getMinimumStock().update(goods.getIdGoods());

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean edit(GoodsReception goodsRec, int recsId){
		return true;
	}
	
}