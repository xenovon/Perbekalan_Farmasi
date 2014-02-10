package com.binar.core.dashboard.dashboardItem.procurementDueDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Invoice;
import com.binar.entity.Goods;
import com.binar.entity.enumeration.EnumStockStatus;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;

public class ProcurementDueDateModel {

	GeneralFunction function;
	EbeanServer server;
	DateManipulator date;
	public ProcurementDueDateModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
		this.date=function.getDate();
	}
	public String getCurrentMonth(){
		LocalDate now=new LocalDate();
		LocalDate notNow=now.minusMonths(3);
		return date.dateToText(notNow.toDate())+"-"+date.dateToText(now.toDate());
	}

	public List<Invoice> getInvoiceData(){
		//barang belum lunas 3 bulan kebelakang
		LocalDate baseDate=new LocalDate();
		LocalDate endDate=baseDate.withDayOfMonth(baseDate.dayOfMonth().getMaximumValue());
		LocalDate startDate=baseDate.withDayOfMonth(baseDate.dayOfMonth().getMinimumValue()).minusMonths(3);

		
		List<Invoice> invoices=server.find(Invoice.class).where().
				between("invoiceDate",startDate.toDate(), endDate.toDate()).order().asc("dueDate").
				findList();
		
		List<Invoice> returnValue=filterInvoice(invoices);
		return returnValue;
		
	}
	private List<Invoice> filterInvoice(List<Invoice> invoices){
		List<Invoice> returnValue=new ArrayList<Invoice>();
		for(Invoice invoice:invoices){
			double totalPrice=invoice.getTotalPrice();
			double amountPaid=invoice.getAmountPaid();
			if(amountPaid<totalPrice){
				returnValue.add(invoice);
			}
		}
		return returnValue;
	}
	public void dummyGood(){
		Goods goods= server.find(Goods.class, "BRG-5x");
		goods.setStockStatus(EnumStockStatus.LESS);
		
	}
}
