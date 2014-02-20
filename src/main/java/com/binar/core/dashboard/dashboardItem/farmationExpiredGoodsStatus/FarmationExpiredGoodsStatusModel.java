package com.binar.core.dashboard.dashboardItem.farmationExpiredGoodsStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.entity.enumeration.EnumStockStatus;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;

public class FarmationExpiredGoodsStatusModel {

	GeneralFunction function;
	EbeanServer server;
	DateManipulator date;
	
	public FarmationExpiredGoodsStatusModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
		this.date=function.getDate();
	}
	public List<DeletedGoods> getDeletedGoodsList(){
		DateTime endDate=DateTime.now();
		List<DeletedGoods> deletedGoods=server.find(DeletedGoods.class).
				where().between("deletion_date", endDate.minusMonths(6).toDate(), endDate).order().desc("deletion_date").setMaxRows(15).findList();		
		return deletedGoods;
		
	}
	public String getCurrentMonth(){
		LocalDate now=new LocalDate();
		LocalDate notNow=now.minusMonths(6);
		return date.dateToText(notNow.toDate())+"-"+date.dateToText(now.toDate());
	}
	public void dummyGood(){
		Goods goods= server.find(Goods.class, "BRG-5x");
		goods.setStockStatus(EnumStockStatus.LESS);
		
	}
}
