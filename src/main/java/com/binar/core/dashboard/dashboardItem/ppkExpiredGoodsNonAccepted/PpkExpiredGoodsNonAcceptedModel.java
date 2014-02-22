package com.binar.core.dashboard.dashboardItem.ppkExpiredGoodsNonAccepted;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.entity.enumeration.EnumStockStatus;
import com.binar.generalFunction.AcceptancePyramid;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;

public class PpkExpiredGoodsNonAcceptedModel {

	GeneralFunction function;
	EbeanServer server;
	DateManipulator date;
	AcceptancePyramid accept;
	public PpkExpiredGoodsNonAcceptedModel(GeneralFunction function) {
		this.function=function;
		this.accept=function.getAcceptancePyramid();
		this.date=function.getDate();
		this.server=function.getServer();
	}
	public List<DeletedGoods> getGoodsList(){
		//
		
		DateTime endDate=DateTime.now();
		List<DeletedGoods> deletedGoods=server.find(DeletedGoods.class).
				where().between("deletion_date", endDate.minusMonths(6).toDate(), endDate).le("acceptance",accept.getUnacceptCriteria()).order().desc("deletion_date").findList();		
		return deletedGoods;
		
		
	}
	public String getCurrentMonth(){
		LocalDate now=new LocalDate();
		LocalDate notNow=now.minusMonths(6);
		return date.dateToText(notNow.toDate())+"-"+date.dateToText(now.toDate());
	}

}
