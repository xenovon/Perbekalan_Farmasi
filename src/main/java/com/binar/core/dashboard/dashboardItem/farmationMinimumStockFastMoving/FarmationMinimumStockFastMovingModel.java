package com.binar.core.dashboard.dashboardItem.farmationMinimumStockFastMoving;

import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.generalFunction.GeneralFunction;

public class FarmationMinimumStockFastMovingModel {

	GeneralFunction function;
	EbeanServer server;
	public FarmationMinimumStockFastMovingModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	public List<Goods> getGoodsList(){
		//
		List<Goods> goods=server.find(Goods.class).where().eq("stockStatus", "WARNING").eq("important", true).
				eq("stockStatus", "LESS").order().asc("currentStock").findList();
		
		return goods;
		
	}
}
