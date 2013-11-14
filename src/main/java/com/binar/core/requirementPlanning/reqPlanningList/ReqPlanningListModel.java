package com.binar.core.requirementPlanning.reqPlanningList;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;

public class ReqPlanningListModel {

	GeneralFunction generalFunction;
	EbeanServer server;
	
	public ReqPlanningListModel(GeneralFunction function){
		this.generalFunction=function;
		server=generalFunction.getServer();
		
	}
	//mendapatkan tabel req planning sesuai bulan yang dipilih
	public List<ReqPlanning> getTableData(DateTime periode){
		Date startDate=periode.toDate();
		Date endDate=periode.withDayOfMonth(periode.dayOfMonth().getMaximumValue()).toDate();
		List<ReqPlanning> returnValue=server.find(ReqPlanning.class).where().
				between("period", startDate, endDate).findList();

		return returnValue;
	}
	public ReqPlanning getReqPlanning(int idReqPlanning){
		return server.find(ReqPlanning.class, idReqPlanning);
	}
}
