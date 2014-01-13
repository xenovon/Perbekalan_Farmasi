package com.binar.core.dashboard.dashboardItem.farmationExpireGoods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.GoodsReception;
import com.binar.entity.enumeration.EnumStockStatus;
import com.binar.generalFunction.GeneralFunction;

public class FarmationExpiredGoodsModel {

	GeneralFunction function;
	EbeanServer server;
	public FarmationExpiredGoodsModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	public List<GoodsReception> getFarmationExpiredGoods(){
		//
		
		//cuma mengurutkan kadaluarsa...bukan mendekati kadaluarsa
		//implementasi sementara
		List<GoodsReception> goodsExpired=server.find(GoodsReception.class).
				orderBy().desc("expired_date").setMaxRows(50).findList();
		
		return goodsExpired;
		
	}
	
	public void dummyGood(){
		Goods goods= server.find(Goods.class, "BRG-5x");
		goods.setStockStatus(EnumStockStatus.LESS);
		
	}
}
