package com.binar.core.inventoryManagement.consumptionList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.binar.core.requirementPlanning.inputRequierementPlanning.TableData;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.StockFunction;
import com.binar.generalFunction.StockFunction.StockType;

public class ConsumptionListModel {
	
	GeneralFunction generalFunction;
	EbeanServer server;
	StockFunction stock;
	public ConsumptionListModel(GeneralFunction function){
		this.generalFunction=function;
		server=generalFunction.getServer();	
		this.stock=function.getStock();
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

	public Map<Goods, Integer> getTableData(DateTime periode){
		System.out.println("Mendapatkan periode");
		Date startDate=periode.toDate();
		Date endDate=periode.withDayOfMonth(periode.dayOfMonth().getMaximumValue()).toDate();
		List<GoodsConsumption> consumptionOfMonth=server.find(GoodsConsumption.class).where().
				between("consumptionDate", startDate, endDate).findList();
		System.out.println("Start Date : "+startDate);
		System.out.println("End Date : "+endDate);
		
		Map<Goods, Integer> returnValue=new HashMap<Goods, Integer>();		
		for (GoodsConsumption consumption : consumptionOfMonth){
			if(returnValue.containsKey(consumption.getGoods())){
				int quantity=returnValue.get(consumption.getGoods());
				int quantityNow=quantity+consumption.getQuantity();
				
				returnValue.remove(consumption.getGoods());
				returnValue.put(consumption.getGoods(), quantityNow);
			}else{
				returnValue.put(consumption.getGoods(), consumption.getQuantity());
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
		List<GoodsConsumption> consumptionOfMonth=server.find(GoodsConsumption.class).where().
				between("consumptionDate", startDate, endDate).findList();
		System.out.println("Start Date : "+startDate);
		System.out.println("End Date : "+endDate);
		
		Map<DateTime, Integer> returnValue = new HashMap<DateTime, Integer>();
		for (GoodsConsumption consumption : consumptionOfMonth){
			Calendar consDate=Calendar.getInstance();
			consDate.setTime(consumption.getConsumptionDate());
			int day=consDate.get(Calendar.DAY_OF_MONTH);			
			DateTime key=getKeyDateTime(returnValue, day);
		
			if(key==null){
				int quantity=1;
				returnValue.put(new DateTime(consumption.getConsumptionDate()), quantity);
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

	private Goods getGoods(String idGoods){
		return server.find(Goods.class, idGoods);
	}
	
	public List<GoodsConsumption> getConsumptions(String idGoods, DateTime periode){
		Goods goods=getGoods(idGoods);
		if(goods==null){
			return null;
		}
		Date startDate=periode.withDayOfMonth(periode.dayOfMonth().getMinimumValue()).toDate();
		Date endDate=periode.withDayOfMonth(periode.dayOfMonth().getMaximumValue()).toDate();
		
		return server.find(GoodsConsumption.class).where().
				between("consumptionDate", startDate, endDate).
				eq("goods", goods).order().asc("consumptionDate").findList();	
	}
	
	public List<GoodsConsumption> getConsumptionsByDate(DateTime periode){

		try {
			Date startDate=periode.withHourOfDay(periode.hourOfDay().getMinimumValue()).toDate();
			Date endDate=periode.withHourOfDay(periode.hourOfDay().getMaximumValue()).toDate();
			
			return server.find(GoodsConsumption.class).where().
					between("consumptionDate", startDate, endDate).
					order().asc("consumptionDate").findList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<GoodsConsumption>();
		}	
	}
	
	public int getNumberOfConsumptionsByDate(DateTime periode){		
		int numbers =getConsumptionsByDate(periode).size();
		System.out.println("Jumlah data konsumsi : "+numbers);
		return numbers;
	}

	public boolean deleteGoodsConsumption(int consIdFinal) {
		server.beginTransaction();
		try {
			GoodsConsumption goodsCon= server.find(GoodsConsumption.class, consIdFinal);
			String idGoods=goodsCon.getGoods().getIdGoods();
			//fungsi untuk mencegah penghapusan
			if(!stock.isAnyNewestItem(goodsCon)){
				int consumptionCount=goodsCon.getQuantity();
				Goods goods=server.find(Goods.class, idGoods);
				int currentStock=goods.getCurrentStock();
				goods.setCurrentStock(currentStock+consumptionCount);
				server.delete(goodsCon);
				server.update(goods);
				generalFunction.getMinimumStock().update(goods.getIdGoods());
				server.commitTransaction();
				return true;				
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			server.rollbackTransaction();
			return false;
		}finally{
			server.endTransaction();
		}
	}
	
	public boolean edit(GoodsConsumption goodsCon, int consId){
		return true;
	}
}
