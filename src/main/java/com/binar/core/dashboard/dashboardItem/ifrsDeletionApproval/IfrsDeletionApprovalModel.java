package com.binar.core.dashboard.dashboardItem.ifrsDeletionApproval;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

public class IfrsDeletionApprovalModel {

	GeneralFunction function;
	EbeanServer server;
	DateManipulator date;
	AcceptancePyramid accept;
	public IfrsDeletionApprovalModel(GeneralFunction function) {
		this.function=function;
		this.accept=function.getAcceptancePyramid();
		this.date=function.getDate();
		this.server=function.getServer();
	}
	public List<DeletedGoods> getDeletedGoodsList(){
		//
		
		Date startDate=DateTime.now().minusMonths(6).dayOfMonth().withMinimumValue().hourOfDay().withMinimumValue().toDate();
		Date endDate=DateTime.now().dayOfMonth().withMaximumValue().toDate();
		List<DeletedGoods> deletedGoods=server.find(DeletedGoods.class).
				where().between("deletion_date", startDate, endDate).le("acceptance",accept.getUnacceptCriteria()).findList();

		return deletedGoods;
		
	}
	
	public void dummyGood(){
		Goods goods= server.find(Goods.class, "BRG-5x");
		goods.setStockStatus(EnumStockStatus.LESS);
		
	}
	public String getCurrentMonth(){
		LocalDate now=new LocalDate();
		LocalDate notNow=now.minusMonths(6);
		return date.dateToText(notNow.toDate())+"-"+date.dateToText(now.toDate());
	}

}
