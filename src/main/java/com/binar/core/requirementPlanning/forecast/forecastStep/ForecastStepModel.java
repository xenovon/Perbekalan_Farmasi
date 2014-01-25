package com.binar.core.requirementPlanning.forecast.forecastStep;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.generalFunction.GeneralFunction;

public class ForecastStepModel {
	
	GeneralFunction function;
	EbeanServer server;
	public ForecastStepModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
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

}
