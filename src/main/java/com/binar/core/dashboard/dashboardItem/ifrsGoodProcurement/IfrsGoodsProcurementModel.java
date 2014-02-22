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
import com.binar.entity.Invoice;
import com.binar.entity.InvoiceItem;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.PurchaseOrderItem;
import com.binar.entity.ReqPlanning;
import com.binar.entity.enumeration.EnumStockStatus;
import com.binar.generalFunction.AcceptancePyramid;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;

public class IfrsGoodsProcurementModel {

	GeneralFunction function;
	EbeanServer server;
	final String PROCUREMENT_TEXT="Jumlah Pengadaan Bulan Ini";
	final String REQUIREMENT_TEXT="Rencana Kebutuhan Bulan Ini";
	DateManipulator date;
	AcceptancePyramid accept;
	public IfrsGoodsProcurementModel(GeneralFunction function) {
		this.function=function;
		this.accept=function.getAcceptancePyramid();
		this.server=function.getServer();
		this.date=function.getDate();
	}
	public String getCurrentMonth(){
		LocalDate now=new LocalDate();
		return date.dateToText(now.toDate());
	}
	//untuk mendapatkan data yang ditujukan untuk chart
	public Map<String, Integer> getChartData(Map<String, Integer> inputData){
		Map<String, Integer> returnValue=new HashMap<String, Integer>();
		returnValue.put("Pengadaan Bulan Ini", inputData.get(PROCUREMENT_TEXT));
		returnValue.put("Rencana Kebutuhan Belum Diadakan", inputData.get(REQUIREMENT_TEXT)-inputData.get(PROCUREMENT_TEXT));
		return returnValue;
	}
	public Map<String, Integer> getReceptionRequirementCount(){
		
		try {
			//inisialisasi tanggal dan return Value
			
			LocalDate baseDate=new LocalDate();
			LocalDate endDate=baseDate.withDayOfMonth(baseDate.dayOfMonth().getMaximumValue());
			LocalDate startDate=baseDate.withDayOfMonth(baseDate.dayOfMonth().getMinimumValue());
			
			Map<String,Integer> returnValue=new HashMap<String, Integer>();
			//mendapatkan jumlah rencana kebutuhan
			int invoiceCount=getInvoiceCount(startDate.toDate(), endDate.toDate());
			int reqPlanningCount=getReqPlanningCount(startDate.toDate(), endDate.toDate());
			
			if(invoiceCount==0 && reqPlanningCount ==0){
				return null;
			}
			returnValue.put(PROCUREMENT_TEXT, invoiceCount);
			returnValue.put(REQUIREMENT_TEXT,reqPlanningCount);

			return returnValue;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	private Integer getReqPlanningCount(Date startDate, Date endDate){
		List<ReqPlanning> reqList=server.find(ReqPlanning.class).where().eq("acceptance", accept.getAcceptedByAllCriteria())
				.between("period", startDate, endDate).findList();
		System.out.println("Req Planning size "+reqList.size());
		int returnValue=0;
		for(ReqPlanning planning:reqList){
//			returnValue=returnValue+planning.getAcceptedQuantity();
			returnValue=returnValue+1;
		}
		System.out.println(returnValue);
		return returnValue;
	}
	
	//mendapatkan jumlah barang yang diadakan melalui surat pesanan
	private Integer getInvoiceCount(Date startDate, Date endDate){
		List<Invoice> invoiceList=server.find(Invoice.class).where()
				.between("invoiceDate", startDate, endDate).findList();
		int returnValue=0;
		System.out.println("Procurement Count"+invoiceList.size());
		for(Invoice invoice:invoiceList){
			for(InvoiceItem item:invoice.getInvoiceItem()){
				returnValue=returnValue+1;
//				returnValue=returnValue+item.getQuantity();
				System.out.println("Quantity "+item.getPurchaseOrderItem().getSupplierGoods().getGoods().getName()+" "+item.getQuantity());
			}
		}
		return returnValue;
	}

	

}
