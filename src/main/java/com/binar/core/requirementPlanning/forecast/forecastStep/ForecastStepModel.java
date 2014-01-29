package com.binar.core.requirementPlanning.forecast.forecastStep;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.generalFunction.GeneralFunction;

public class ForecastStepModel {
	
	GeneralFunction function;
	EbeanServer server;
	public ForecastStepModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	public Goods getGoods(String idGoods){
		return server.find(Goods.class, idGoods );
	}
	public Map<String, String> getGoodsData(){
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
	public List<Integer> generateConsumptionData(String idGoods, int period){
		
		List<Integer> returnValue =new ArrayList<Integer>();
		LocalDate time=new LocalDate();
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
			time=time.minusMonths(1);
			i++;
		}
		return returnValue;
	}

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

		return returnValue;
	}



}
