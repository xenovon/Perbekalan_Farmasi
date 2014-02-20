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
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;

public class FarmationGoodsConsumptionModel {

	GeneralFunction function;
	EbeanServer server;
	DateManipulator date;
	public FarmationGoodsConsumptionModel(GeneralFunction function) {
		this.function=function;
		this.date=function.getDate();
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
		if(consumptionOfMonth.size()==0){
			return null;
		}
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
	/*
	 * Sisanya ganti "lainnya....."
	 */
	public Map<Integer, String> getGoodsCosumption(){
		try {
			Map<Goods, Integer> allGoods=getGoodsConsumptionByGoods();
			
			SortedMap<Integer, String> returnValue=new TreeMap<Integer, String>();
			SortedMap<Integer, String> buffer=new TreeMap<Integer, String>();
			
			for(Map.Entry<Goods, Integer> entry:allGoods.entrySet()){
				buffer.put(entry.getValue(), entry.getKey().getName());
			}
			
			//hanya mengambil 10 barang dengan konsumsi terbanyak
			//Sorted map sudah mengurutkan barang dari yang kuantitasnya paling kecil, hingga paling besar
			int i=0;
			int count=buffer.size()-10;
			int otherCount=0; //untuk menghitung obat lainnya.
			for(Map.Entry<Integer, String> entry:buffer.entrySet()){
				if(i>=count){
					returnValue.put(entry.getKey(), entry.getValue());
				}else{
					otherCount=otherCount+entry.getKey();
				}
				i++;
			}
			if(otherCount!=0){
				returnValue.put(otherCount, "Lainnya");			
			}
			System.out.println(returnValue.size());
			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
		map.put("224", 120);
		map.put("324", 32);
		map.put("32", 44);
		map.put("24", 920);
		map.put("234", 39);
		map.put("324", 36);
		map.put("2r3", 42);
		map.put("233", 324);
		map.put("253", 332);
		map.put("23fds", 34323);
		map.put("223", 3323);
		map.put("2343", 352);
		
		SortedMap<Integer, String> map2=new TreeMap<Integer, String>();
		SortedMap<Integer, String> returnValue=new TreeMap<Integer, String>();

		for(Map.Entry<String, Integer> entry:map.entrySet()){
			map2.put(entry.getValue(), entry.getKey());
		}
		int i=0;
		int count=map2.size()-10;
		for(Map.Entry<Integer, String> entry:map2.entrySet()){
			if(i>=count){
				returnValue.put(entry.getKey(), entry.getValue());
			}
			i++;
		}
		System.out.println(map2);
		System.out.println(returnValue);
				
	}
	
	public String getCurrentMonth(){
		LocalDate now=new LocalDate();
		return date.dateToText(now.toDate());
	}

}
