package com.binar.core.dashboard.dashboardItem.farmationGoodsWithIncreasingTrend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.enumeration.EnumStockStatus;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;

public class FarmationGoodsWithIncreasingTrendModel {

	GeneralFunction function;
	EbeanServer server;
	DateManipulator date;
	public FarmationGoodsWithIncreasingTrendModel(GeneralFunction function) {
		this.function=function;
		this.date=function.getDate();
		this.server=function.getServer();
	}
	//Mendapatkan data konsumsi untuk setiap barang
	private Map<String, List<Integer>> getGoodsConsumptionData(){
		Map<String, List<Integer>> data=new HashMap<String, List<Integer>>();
		List<Goods> goods=server.find(Goods.class).findList();
		for(Goods singleGoods:goods){
			data.put(singleGoods.getIdGoods(), generateConsumptionData(singleGoods.getIdGoods(), 6));
		}
		return data;
	}
	//Mendapatkan data konsumsi pada satu barang
	private List<Integer> generateConsumptionData(String idGoods, int period){
		List<Integer> returnValue =new ArrayList<Integer>();
		LocalDate time=new LocalDate().minusMonths(period);
		int i=0;
		Goods goods;
		try {
			goods=server.find(Goods.class, idGoods);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		while(i!=period){
			int consumptionCount=getConsumptionPerMonth(time, goods);
			returnValue.add(consumptionCount);
			time=time.plusMonths(1);
			System.out.println("Current Month "+time.toString());
			i++;
		}
		return returnValue;
	}
	//mendapatkan konsumsi satu barang dalam 1 bulan.
	private int getConsumptionPerMonth(LocalDate periode, Goods goods){
		System.out.println("Mendapatkan periode");
		Date startDate=periode.withDayOfMonth(periode.dayOfMonth().getMinimumValue()).toDate();
		Date endDate=periode.withDayOfMonth(periode.dayOfMonth().getMaximumValue()).toDate();

		List<GoodsConsumption> consumptionOfMonth=server.find(GoodsConsumption.class).where().
				between("consumptionDate", startDate, endDate).eq("goods", goods).findList();
		System.out.println("Start Date : "+startDate);
		System.out.println("End Date : "+endDate +" "+consumptionOfMonth.size());
		
		int returnValue=0;
		for (GoodsConsumption consumption : consumptionOfMonth){
			returnValue=returnValue+consumption.getQuantity();
		}
		System.out.println("Consumption Count "+returnValue +" In "+endDate.toString());
		return returnValue;
	}
	//Method untuk data dummy 
	public Map<String,List<Integer>> getChartDataDummy(){
		Random random=new Random();
		Map<String, List<Integer>> data=new HashMap<String, List<Integer>>();
		List<Integer> data1=new ArrayList<Integer>();
		for(int i=0;i<6;i++){
			data1.add(random.nextInt(500)+200);			
		}
		List<Integer> data2=new ArrayList<Integer>();
		for(int i=0;i<6;i++){
			data2.add(random.nextInt(500)+200);			
		}
		List<Integer> data3=new ArrayList<Integer>();
		for(int i=0;i<6;i++){
			data3.add(random.nextInt(500)+200);			
		}
		List<Integer> data4=new ArrayList<Integer>();
		for(int i=0;i<6;i++){
			data4.add(random.nextInt(500)+200);			
		}
		List<Integer> data5=new ArrayList<Integer>();
		for(int i=0;i<6;i++){
			data5.add(random.nextInt(500)+200);			
		}
		data.put("Clopedin", data1);
		data.put("Closenin", data2);
		data.put("Ambroxol", data3);
		data.put("Betadine", data4);
		data.put("Amoxicilin", data5);
		System.out.println(data.toString());
		return data;
	}	
	//method utama
	
	public Map<String,List<Integer>> getChartData(){
		//Dapatkan data bulanan semua barang, selama 6 bulan
		Map<String, List<Integer>> monthlyData=getGoodsConsumptionData();

		//konversikan ke jumlah trend
		Map<String, Integer> data2=new HashMap<String, Integer>();
		for(Entry<String, List<Integer>> entry:monthlyData.entrySet()){
			data2.put(entry.getKey(),countIncreasingTrend(entry.getValue()));
		}
		data2=MapUtil.sortByValue(data2);
		
		Map<String, List<Integer>> filteredData=filterData(data2, monthlyData);
		
		Map<String, List<Integer>> returnValue=new HashMap<String, List<Integer>>();
		
		//konversi string di filtered data jadi nama barang (yang sekarang pake id)
		for(Map.Entry<String, List<Integer>> entry:filteredData.entrySet()){
			Goods goods=server.find(Goods.class, entry.getKey());
			returnValue.put(goods.getName(), entry.getValue());
		}
		return returnValue;
	}
	
	/* Method untuk menghitung nilai trend dari suatu array, makin tinggi kenaikan trend, makin besar nilai kembaliannya
	 * Nilai minus jika array trendnya cenderung menurun
	 */
	private int countIncreasingTrend(List<Integer> value){
		if(value.size()==0){
			return 0;
		}
		
		int returnValue=0;
		for(int i=1;i<value.size();i++){
			int diff=value.get(i)-value.get(i-1);
			returnValue=returnValue+diff;
		}
		return returnValue;
	}
	//Memfilter hanya mengambil 5 data yang trennya paling naik
	private Map<String, List<Integer>> filterData(Map<String,Integer> data,Map<String, List<Integer>> data2){
		Map<String, List<Integer>> dataReturn=new HashMap<String, List<Integer>>();
		int i=0;
		for(Map.Entry<String, Integer> singleData:data.entrySet()){
			System.out.print(singleData.getValue()+" + "+singleData.getKey()+" | ");
			dataReturn.put(singleData.getKey(), data2.get(singleData.getKey()));
			if(i==4){
				break;
			}
			i++;
		}
		System.out.println();
		return dataReturn;
	}
	//kelas untuk sorting map
	public static class MapUtil
	{
	    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map )
	    {
	        List<Map.Entry<K, V>> list =
	            new LinkedList<Map.Entry<K, V>>( map.entrySet() );
	        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
	        {
	            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
	            {
	                return (o2.getValue()).compareTo( o1.getValue() );
	            }
	        } );

	        Map<K, V> result = new LinkedHashMap<K, V>();
	        for (Map.Entry<K, V> entry : list)
	        {
	            result.put( entry.getKey(), entry.getValue() );
	        }
	        return result;
	    }
	}


	/*
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

	public Map<Integer, String> getGoodsCosumptionDummy(){
		Map<Integer, String> map=new HashMap<Integer, String>();
		map.put(800, "Clopedin");
		map.put(600, "Closenin");
		map.put(900, "Ambroxol");
		map.put(600, "Betadine");
		map.put(370, "Kain Kassa");
		
		return map;
	}
	
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
	
	}

*/
	public String getCurrentMonth(){
		LocalDate now=new LocalDate();
		LocalDate notNow=now.minusMonths(6);
		return date.dateToText(notNow.toDate())+"-"+date.dateToText(now.toDate());
	}
}
