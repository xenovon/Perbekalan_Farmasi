package com.binar.core.dashboard.dashboardItem.ppkExpiredGoodsNonAccepted;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.entity.enumeration.EnumStockStatus;
import com.binar.generalFunction.GeneralFunction;

public class PpkExpiredGoodsNonAcceptedModel {

	GeneralFunction function;
	EbeanServer server;
	public PpkExpiredGoodsNonAcceptedModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	public List<DeletedGoods> getGoodsList(){
		//
		
		DateTime endDate=DateTime.now();
		List<DeletedGoods> deletedGoods=server.find(DeletedGoods.class).
				where().between("deletion_date", endDate.minusMonths(2).toDate(), endDate).eq("isAccepted",false).order().desc("deletion_date").findList();		
		return deletedGoods;
		
		
	}
	
	public void dummyGood(){
		Goods goods= server.find(Goods.class, "BRG-5x");
		goods.setStockStatus(EnumStockStatus.LESS);
		
	}
}
