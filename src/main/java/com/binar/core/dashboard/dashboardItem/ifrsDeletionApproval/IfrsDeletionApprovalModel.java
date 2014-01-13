package com.binar.core.dashboard.dashboardItem.ifrsDeletionApproval;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.entity.enumeration.EnumStockStatus;
import com.binar.generalFunction.GeneralFunction;

public class IfrsDeletionApprovalModel {

	GeneralFunction function;
	EbeanServer server;
	public IfrsDeletionApprovalModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	public List<DeletedGoods> getDeletedGoodsList(){
		//
		
		Date startDate=DateTime.now().minusMonths(1).dayOfMonth().withMinimumValue().hourOfDay().withMinimumValue().toDate();
		Date endDate=DateTime.now().dayOfMonth().withMaximumValue().toDate();
		List<DeletedGoods> deletedGoods=server.find(DeletedGoods.class).
				where().between("deletion_date", startDate, endDate).eq("isAccepted",false).findList();

		return deletedGoods;
		
	}
	
	public void dummyGood(){
		Goods goods= server.find(Goods.class, "BRG-5x");
		goods.setStockStatus(EnumStockStatus.LESS);
		
	}
}
