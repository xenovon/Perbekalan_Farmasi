package com.binar.core.dashboard.dashboardItem.ifrsGoodProcurement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.PurchaseOrderItem;
import com.binar.entity.ReqPlanning;
import com.binar.entity.enumeration.EnumStockStatus;
import com.binar.generalFunction.GeneralFunction;

public class IfrsGoodsProcurementModel {

	GeneralFunction function;
	EbeanServer server;
	public IfrsGoodsProcurementModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	
	public Map<Integer, String> getReceptionRequirementCount(){
		
		try {
			//inisialisasi tanggal dan return Value
			
			LocalDate baseDate=new LocalDate();
			LocalDate endDate=baseDate.withDayOfMonth(baseDate.dayOfMonth().getMaximumValue());
			LocalDate startDate=baseDate.withDayOfMonth(baseDate.dayOfMonth().getMinimumValue());
			
			Map<Integer, String> returnValue=new HashMap<Integer, String>();
			//mendapatkan jumlah rencana kebutuhan
			int procurementCount=getProcurementCount(startDate.toDate(), endDate.toDate());
			int reqPlanningCount=getReqPlanningCount(startDate.toDate(), endDate.toDate());
			
			if(procurementCount==0 && reqPlanningCount ==0){
				return null;
			}
			returnValue.put(procurementCount, "Rencana Kebutuhan Bulan Ini" );
			returnValue.put(reqPlanningCount, "Jumlah Pengadaan Bulan Ini");
			
			return returnValue;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	private Integer getReqPlanningCount(Date startDate, Date endDate){
		List<ReqPlanning> reqList=server.find(ReqPlanning.class).where().eq("isAccepted", true)
				.between("period", startDate, endDate).findList();
		int returnValue=0;
		for(ReqPlanning planning:reqList){
			returnValue=returnValue+planning.getAcceptedQuantity();
		}
		return returnValue;
	}
	
	//mendapatkan jumlah barang yang diadakan melalui surat pesanan
	private Integer getProcurementCount(Date startDate, Date endDate){
		List<PurchaseOrder> purchaseList=server.find(PurchaseOrder.class).where()
				.between("date", startDate, endDate).findList();
		int returnValue=0;
		for(PurchaseOrder purchase:purchaseList){
			for(PurchaseOrderItem item:purchase.getPurchaseOrderItem()){
				returnValue=returnValue+item.getQuantity();
			}
		}
		return returnValue;
	}

	

}
