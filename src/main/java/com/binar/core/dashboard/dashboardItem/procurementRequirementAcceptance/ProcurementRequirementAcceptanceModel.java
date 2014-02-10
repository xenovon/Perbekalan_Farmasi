package com.binar.core.dashboard.dashboardItem.procurementRequirementAcceptance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.ReqPlanning;
import com.binar.entity.enumeration.EnumStockStatus;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;

public class ProcurementRequirementAcceptanceModel {

	GeneralFunction function;
	EbeanServer server;
	DateManipulator date;
	public ProcurementRequirementAcceptanceModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
		this.date=function.getDate();
	}
	public String getCurrentMonth(){
		LocalDate now=new LocalDate();
		return date.dateToText(now.toDate());
	}

	public List<ReqPlanning> getReqAcceptedList(){
		//
		
		try {
			LocalDate startDate=LocalDate.now().dayOfMonth().withMinimumValue();
			LocalDate endDate=LocalDate.now().dayOfMonth().withMaximumValue();
			
			List<ReqPlanning> reqPlanning=server.find(ReqPlanning.class).where().eq("isAccepted",true).
					between("period",startDate.toDate(), endDate.toDate()).findList();
			
			return reqPlanning;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<ReqPlanning>();
		}
		
		
	}	
}
