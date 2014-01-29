package com.binar.core.dashboard.dashboardItem.farmationGoodsConsumption;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Map<Goods, Integer> getGoodsConsumptionByGoods(){
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
	
	public static void main(String[] args) {
		System.out.println(LocalDate.now().withDayOfMonth(LocalDate.now().dayOfMonth().getMaximumValue()).toString());
		System.out.println(LocalDate.now().withDayOfMonth(LocalDate.now().dayOfMonth().getMinimumValue()).toString());
	}
}
