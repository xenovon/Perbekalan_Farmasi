package com.binar.core.dashboard.dashboardItem.farmationMinimumStockFastMoving;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.enumeration.EnumStockStatus;
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
		
		Collection<String> collection=new ArrayList<String>();
		collection.add(EnumStockStatus.LESS.toString());
		collection.add(EnumStockStatus.WARNING.toString());
		
		List<Goods> goods=server.find(Goods.class).where().in("stockStatus", collection).eq("important", true).
				order().asc("currentStock").findList();
		
		return goods;
		
	}
}
