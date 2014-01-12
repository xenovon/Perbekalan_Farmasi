package com.binar.core.dashboard.dashboardItem.farmationExpireGoods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.enumeration.EnumStockStatus;
import com.binar.generalFunction.GeneralFunction;

public class FarmationExpiredGoodsModel {

	GeneralFunction function;
	EbeanServer server;
	public FarmationExpiredGoodsModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	public List<Goods> getFarmationExpiredGoods(){
		//
		
		Collection<String> collection=new ArrayList<String>();
		collection.add(EnumStockStatus.LESS.toString());
		collection.add(EnumStockStatus.WARNING.toString());
		
		List<Goods> goods=server.find(Goods.class).where().in("stockStatus", collection).eq("important", true).
				order().asc("currentStock").findList();
		
		return goods;
		
	}
	
	public void dummyGood(){
		Goods goods= server.find(Goods.class, "BRG-5x");
		goods.setStockStatus(EnumStockStatus.LESS);
		
	}
}
