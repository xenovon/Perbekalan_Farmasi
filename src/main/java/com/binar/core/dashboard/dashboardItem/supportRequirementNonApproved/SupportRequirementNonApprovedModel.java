package com.binar.core.dashboard.dashboardItem.supportRequirementNonApproved;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.ReqPlanning;
import com.binar.entity.enumeration.EnumStockStatus;
import com.binar.generalFunction.AcceptancePyramid;
import com.binar.generalFunction.GeneralFunction;

public class SupportRequirementNonApprovedModel {

	GeneralFunction function;
	EbeanServer server;
	AcceptancePyramid accept;
	public SupportRequirementNonApprovedModel(GeneralFunction function) {
		this.function=function;
		this.accept=function.getAcceptancePyramid();
		this.server=function.getServer();
	}
	public List<ReqPlanning> getReqList(){
		//
		
		LocalDate startDate=LocalDate.now().dayOfMonth().withMinimumValue();
		LocalDate endDate=LocalDate.now().dayOfMonth().withMaximumValue();
		
		List<ReqPlanning> reqPlanning=server.find(ReqPlanning.class).where().
				between("period",startDate.toDate(), endDate.toDate()).le("acceptance", accept.getUnacceptCriteria()).findList();
		
		return filterReqPlanning(reqPlanning);
		
	}
	private List<ReqPlanning> filterReqPlanning(List<ReqPlanning> list){
		List<ReqPlanning> returnValue=new ArrayList<ReqPlanning>();
		for(ReqPlanning req:list){
			if(accept.isManipulable(req.getAcceptance())){
				returnValue.add(req);
			}
		}
		return  returnValue;
	}

}
