package com.binar.core.dashboard.dashboardItem.farmationGoodsConsumption;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.enumeration.EnumStockStatus;
import com.binar.generalFunction.GeneralFunction;

public class FarmationGoodsConsumptionModel {

	GeneralFunction function;
	EbeanServer server;
	public FarmationGoodsConsumptionModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	private  Map<Goods, Integer> getGoodsConsumptionByGoods(){
		System.out.println("Mendapatkan periode");
		LocalDate date=LocalDate.now();
		Date startDate=date.withDayOfMonth(date.dayOfMonth().getMinimumValue()).toDate();
		Date endDate=date.withDayOfMonth(date.dayOfMonth().getMaximumValue()).toDate();
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
	
	public Map<Integer, Goods> getGoodsCosumption(){
		Map<Goods, Integer> allGoods=getGoodsConsumptionByGoods();
		SortedMap<Integer, Goods> returnValue=new TreeMap<Integer, Goods>();
		SortedMap<Integer, Goods> buffer=new TreeMap<Integer, Goods>();
		
		for(Map.Entry<Goods, Integer> entry:allGoods.entrySet()){
			buffer.put(entry.getValue(), entry.getKey());
		}
		
		//hanya mengambil 5 barang dengan konsumsi terbanyak
		int i=0;
		for(Map.Entry<Integer, Goods> entry:buffer.entrySet()){
			
		}
		return returnValue;
	}
	
	public static void main(String[] args) {
		System.out.println(LocalDate.now().withDayOfMonth(LocalDate.now().dayOfMonth().getMaximumValue()).toString());
		System.out.println(LocalDate.now().withDayOfMonth(LocalDate.now().dayOfMonth().getMinimumValue()).toString());
		
		Map<String, Integer> map=new HashMap<String, Integer>();
		
		map.put("Aa", 42);
		map.put("t", 92);
		map.put("32a", 72);
		map.put("sz", 15);
		map.put("234a", 82);
		map.put("214a", 12);
		
		SortedMap<Integer, String> map2=new TreeMap<Integer, String>();
		
		for(Map.Entry<String, Integer> entry:map.entrySet()){
			map2.put(entry.getValue(), entry.getKey());
		}
				
	}
}
