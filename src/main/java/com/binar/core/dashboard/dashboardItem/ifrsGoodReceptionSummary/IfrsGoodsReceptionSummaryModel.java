package com.binar.core.dashboard.dashboardItem.ifrsGoodReceptionSummary;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.GoodsReception;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.PurchaseOrderItem;
import com.binar.entity.ReqPlanning;
import com.binar.entity.enumeration.EnumStockStatus;
import com.binar.generalFunction.GeneralFunction;
import com.binar.view.RequirementPlanningView;

public class IfrsGoodsReceptionSummaryModel {

	GeneralFunction function;
	EbeanServer server;
	public IfrsGoodsReceptionSummaryModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	
	public Map<Integer, String> getReceptionProcurementCount(){
		
		try {
			//inisialisasi tanggal dan return Value
			
			LocalDate baseDate=new LocalDate();
			LocalDate endDate=baseDate.withDayOfMonth(baseDate.dayOfMonth().getMaximumValue());
			LocalDate startDate=baseDate.withDayOfMonth(baseDate.dayOfMonth().getMinimumValue());
			
			Map<Integer, String> returnValue=new HashMap<Integer, String>();
			//mendapatkan jumlah rencana kebutuhan
			int procurementCount=getProcurementCount(startDate.toDate(), endDate.toDate());
			int receptionCount=getReceptionCount(startDate.toDate(), endDate.toDate());
			
			if(procurementCount==0 && receptionCount ==0){
				return null;
			}
			returnValue.put(procurementCount, "Pengadaan Bulan Ini");
			returnValue.put(receptionCount, "Barang Yang Sudah Diterima");
			
			
			return returnValue;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
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
	//mendapatkan jumlah barang yang sudah diterima
	private Integer getReceptionCount(Date startDate, Date endDate){
		List<GoodsReception> receptionList=server.find(GoodsReception.class).
				where().between("date", startDate, endDate).findList();
		int returnValue=0;
		for(GoodsReception reception:receptionList){
			returnValue=returnValue+reception.getQuantityReceived();
		}
		
		return returnValue;
		
	}
}

