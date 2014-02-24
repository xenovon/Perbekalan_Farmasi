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
import com.binar.entity.Invoice;
import com.binar.entity.InvoiceItem;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.PurchaseOrderItem;
import com.binar.entity.ReqPlanning;
import com.binar.entity.enumeration.EnumStockStatus;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.view.RequirementPlanningView;

public class IfrsGoodsReceptionSummaryModel {

	GeneralFunction function;
	EbeanServer server;
	final String PROCUREMENT_TEXT="Pengadaan Bulan Ini";
	final String RECEPTION_TEXT="Barang Yang Sudah Diterima";
	DateManipulator date;
	public IfrsGoodsReceptionSummaryModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
		this.date=function.getDate();
	}
	public String getCurrentMonth(){
		LocalDate now=new LocalDate();
		return date.dateToText(now.toDate());
	}
	
	public Map<String, Integer> getChartData(Map<String, Integer> data){
		Map<String, Integer> returnValue=new HashMap<String, Integer>();
		returnValue.put("Barang Pengadaan Sudah Diterima", data.get(RECEPTION_TEXT));
		returnValue.put("Barang Pengadaan Belum Diterima", data.get(PROCUREMENT_TEXT)-data.get(RECEPTION_TEXT));
		return returnValue;
	}
	public Map<String, Integer> getReceptionProcurementCount(){
		
		try {
			//inisialisasi tanggal dan return Value
			
			LocalDate baseDate=new LocalDate();
			LocalDate endDate=baseDate.withDayOfMonth(baseDate.dayOfMonth().getMaximumValue());
			LocalDate startDate=baseDate.withDayOfMonth(baseDate.dayOfMonth().getMinimumValue());
			
			Map<String, Integer> returnValue=new HashMap<String, Integer>();
			//mendapatkan jumlah rencana kebutuhan
			int invoiceCount=getInvoiceCount(startDate.toDate(), endDate.toDate());
			int receptionCount=getReceptionCount(startDate.toDate(), endDate.toDate());
			
			if(invoiceCount==0 && receptionCount ==0){
				return null;
			}
			returnValue.put(PROCUREMENT_TEXT, invoiceCount);
			returnValue.put(RECEPTION_TEXT, receptionCount);
			
			
			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	//mendapatkan jumlah barang yang diadakan melalui invoice
	private Integer getInvoiceCount(Date startDate, Date endDate){
		List<Invoice> invoiceList=server.find(Invoice.class).where()
				.between("invoiceDate", startDate, endDate).findList();
		int returnValue=0;
		System.out.println("Procurement Count"+invoiceList.size());
		for(Invoice invoice:invoiceList){
			for(InvoiceItem item:invoice.getInvoiceItem()){
//				returnValue=returnValue+1;
				returnValue=returnValue+item.getQuantity();
				System.out.println("Quantity "+item.getPurchaseOrderItem().getSupplierGoods().getGoods().getName()+" "+item.getQuantity());
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
//			returnValue=returnValue+1;
			returnValue=returnValue+reception.getQuantityReceived();
		}
		
		return returnValue;
		
	}
}

