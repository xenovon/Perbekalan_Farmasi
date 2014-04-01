package com.binar.core.procurement.invoice;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Invoice;
import com.binar.entity.InvoiceItem;
import com.binar.entity.PurchaseOrder;
import com.binar.generalFunction.GeneralFunction;

public class InvoiceModel {

	GeneralFunction function;
	EbeanServer server;
	public InvoiceModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	
	public List<Invoice> getInvoiceList(DateTime month, DateTime year){
		try {
			if(year==null){
				return null;
			}
			Date startDate;
			Date endDate;
			if(month==null){
				startDate=year.withDayOfYear(year.dayOfYear().getMinimumValue()).withHourOfDay(year.hourOfDay().getMinimumValue()).toDate();
				endDate=year.withDayOfYear(year.dayOfYear().getMaximumValue()).withHourOfDay(year.hourOfDay().getMaximumValue()).toDate();
			}else{
				startDate=month.withDayOfMonth(month.dayOfMonth().getMinimumValue()).withYear(year.getYear()).toDate();	
				endDate=month.withDayOfMonth(month.dayOfMonth().getMaximumValue()).withYear(year.getYear()).toDate();
			}
			System.out.println("Month null" + endDate.toString()+" "+startDate.toString());
			
			return server.find(Invoice.class).where().between("invoiceDate", startDate, endDate).order().asc("invoiceDate").findList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public Invoice getInvoice(int idInvoice){
		return server.find(Invoice.class, idInvoice);
	}
	public String deleteInvoice(int idInvoice){
		try {
			server.delete(getInvoice(idInvoice));
			return null;
		} catch(PersistenceException e){
			e.printStackTrace();
			return "Data Gagal Dihapus : Data faktur sudah terhubung dengan "
					+ "data lainnya. Hapus data yang terhubung terlebih dahulu";			
		}
		catch (Exception e) {
			return "Data gagal dihapus "+e.getMessage();
		}

	}
	public double getPercentage(Invoice invoice){
		double countInvoice=0;
		double countPurchaseOrder=0;
		for(InvoiceItem item:invoice.getInvoiceItem()){
			countInvoice=countInvoice+item.getQuantity();
			countPurchaseOrder=countPurchaseOrder+item.getPurchaseOrderItem().getQuantity();
		}
		System.out.println("JUmlah Invoice "+countInvoice);
		System.out.println("JUmlah PO "+countPurchaseOrder);
		
		double returnValue=countInvoice/countPurchaseOrder*100;
		return returnValue;
	}

}
