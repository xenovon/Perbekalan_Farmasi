package com.binar.core.requirementPlanning.inputRequierementPlanning;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.joda.time.DateTime;
import org.joda.time.JodaTimePermission;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;
import com.binar.view.RequirementPlanningView;
import com.vaadin.data.Container;

public class InputRequirementPlanningModel {

	GeneralFunction generalFunction;
	EbeanServer server;
	
	
	public InputRequirementPlanningModel(GeneralFunction function) {
		this.generalFunction=function;
		this.server=generalFunction.getServer();
		// TODO Auto-generated constructor stub
	}

	public List<TableData> getTableData(DateTime periode){
		Date startDate=periode.toDate();
		Date endDate=periode.withDayOfMonth(periode.dayOfMonth().getMaximumValue()).toDate();
		
		System.out.println(startDate);
		System.out.println(endDate);
		
		List<ReqPlanning> data=server.find(ReqPlanning.class).
				where().between("period", startDate, endDate).findList();
		
		List<TableData> returnValue=new ArrayList<TableData>(data.size());
		for(ReqPlanning req:data){
			TableData tableData=new TableData();
			tableData.setGoodsName(req.getSupplierGoods().getGoods().getName());
			tableData.setIdReq(req.getIdReqPlanning());
			tableData.setManufacturer(req.getSupplierGoods().getManufacturer().getManufacturerName());
			tableData.setReq(req.getQuantity());
			tableData.setSupp(req.getSupplierGoods().getSupplier().getSupplierName());
			tableData.setAccepted(req.isAccepted());
			returnValue.add(tableData);
		}
		System.out.println(returnValue.size() + returnValue.toString());
		return returnValue;
		
	}
	public ReqPlanning getSingleReqPlanning(int id){
		return server.find(ReqPlanning.class, id);
	}
	
	public boolean deleteReqPlanning(int id){
		try {
			ReqPlanning planning= server.find(ReqPlanning.class, id);
			server.delete(planning);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean edit(ReqPlanning reqPlanning, int reqId){
		return true;
	}
}
