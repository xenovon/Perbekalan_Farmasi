package com.binar.core.inventoryManagement.deletionList;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.generalFunction.GeneralFunction;

public class DeletionListModel {
	
	GeneralFunction generalFunction;
	EbeanServer server;
	
	public DeletionListModel (GeneralFunction function) {
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

	public Map<Goods, Integer> getTableData(DateTime periode) {
		System.out.println("Mendapatkan periode");
		Date startDate=periode.toDate();
		Date endDate=periode.withDayOfMonth(periode.dayOfMonth().getMaximumValue()).toDate();
		List<DeletedGoods> deletionOfMonth=server.find(DeletedGoods.class).where().
				between("deletionDate", startDate, endDate).findList();
		System.out.println("Start Date : "+startDate);
		System.out.println("End Date : "+endDate);
		
		Map<Goods, Integer> returnValue=new HashMap<Goods, Integer>();		
		for (DeletedGoods deletion : deletionOfMonth){
			if(returnValue.containsKey(deletion.getSupplierGoods().getGoods())){
				int quantity=returnValue.get(deletion.getSupplierGoods().getGoods());
				int quantityNow=quantity+deletion.getQuantity();
				
				returnValue.remove(deletion.getSupplierGoods().getGoods());
				returnValue.put(deletion.getSupplierGoods().getGoods(), quantityNow);
			}else{
				returnValue.put(deletion.getSupplierGoods().getGoods(), deletion.getQuantity());
			}
		}
		return returnValue;
	}
	
	public Map<DateTime, Integer> getTableDataByDate(DateTime periode){
		System.out.println("Mendapatkan periode");
		Date startDate=periode.toDate();
		Date endDate=periode.withDayOfMonth(periode.dayOfMonth().getMaximumValue()).toDate();
		List<DeletedGoods> deletionOfMonth=server.find(DeletedGoods.class).where().
				between("deletionDate", startDate, endDate).findList();
		System.out.println("Start Date : "+startDate);
		System.out.println("End Date : "+endDate);
		
		Map<DateTime, Integer> returnValue = new HashMap<DateTime, Integer>();
		for (DeletedGoods deletion : deletionOfMonth){
			Calendar delDate=Calendar.getInstance();
			delDate.setTime(deletion.getDeletionDate());
			int day=delDate.get(Calendar.DAY_OF_MONTH);			
			DateTime key=getKeyDateTime(returnValue, day);
		
			if(key==null){
				int quantity=1;
				returnValue.put(new DateTime(deletion.getDeletionDate()), quantity);
			}else{
				Integer quantityNow=returnValue.get(key);
				quantityNow = quantityNow + 1;
		
				returnValue.remove(key);
				returnValue.put(key, quantityNow);
			}
		}
		return returnValue; 
	}

	private DateTime getKeyDateTime(Map<DateTime, Integer> input, int day) {
		for (Map.Entry<DateTime, Integer> entry : input.entrySet()) {
        	if(entry.getKey().getDayOfMonth()==day){
        		return entry.getKey();
        	}
        }
        return null;
	}
	
	private Goods getGoods(String idGoods){
		return server.find(Goods.class, idGoods);
	}
	
	public List<DeletedGoods> getDeletions(String idGoods, DateTime periode){
		Goods goods=getGoods(idGoods);
		if(goods==null){
			return null;
		}
		Date startDate=periode.withDayOfMonth(periode.dayOfMonth().getMinimumValue()).toDate();
		Date endDate=periode.withDayOfMonth(periode.dayOfMonth().getMaximumValue()).toDate();
		
		return server.find(DeletedGoods.class).where().
				between("deletionDate", startDate, endDate).
				eq("supplierGoods.goods", goods).order().asc("deletionDate").findList();	
		
	}
	
	public List<DeletedGoods> getDeletionsByDate(DateTime periode){

		Date startDate=periode.withHourOfDay(periode.hourOfDay().getMinimumValue()).toDate();
		Date endDate=periode.withHourOfDay(periode.hourOfDay().getMaximumValue()).toDate();
		
		return server.find(DeletedGoods.class).where().
				between("deletionDate", startDate, endDate).
				order().asc("deletionDate").findList();	
	}
	
	public int getNumberOfDeletionsByDate(DateTime periode){		
		int numbers =getDeletionsByDate(periode).size();
		System.out.println("Jumlah data deletion : "+numbers);
		return numbers;
	}
	
	public boolean deleteDeletedGoods(int delGoodsIdFinal) {
		try {
			DeletedGoods delGoods= server.find(DeletedGoods.class, delGoodsIdFinal);
			server.delete(delGoods);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean edit(DeletedGoods delGoods, int delGoodsId){
		return true;
	}
}
