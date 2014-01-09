package com.binar.core.procurement.purchaseOrder;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.core.procurement.purchaseOrder.printPurchaseOrder.Const;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.PurchaseOrderItem;
import com.binar.entity.enumeration.EnumPurchaseOrderType;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.vaadin.server.BrowserWindowOpener;

public class PurchaseOrderModel {
	GeneralFunction function;
	EbeanServer server;
	GetSetting setting;
	DateManipulator date;
	public PurchaseOrderModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
		this.setting=function.getSetting();
		this.date=function.getDate();
	}
	public String deletePurchaseOrder(int idPurchaseOrder){
		try {
			server.delete(getPurchaseOrder(idPurchaseOrder));
			return null;
		} catch(PersistenceException e){
			e.printStackTrace();
			return "Data Gagal Dihapus : Data surat pesanan sudah terhubung dengan "
					+ "data lainnya. Hapus data yang terhubung terlebih dahulu";			
		}
		catch (Exception e) {
			return "Data gagal dihapus "+e.getMessage();
		}
	}
	public PurchaseOrder getPurchaseOrder(int idPurchaseOrder){
		return server.find(PurchaseOrder.class, idPurchaseOrder);
	}
	//Untuk mendapatkan data purchase order, jika month = null, maka dihitung semua bulan
	public List<PurchaseOrder> getPurchaseOrderList(DateTime month, DateTime year){
		try {
			if(year==null){
				return null;
			}
			Date startDate;
			Date endDate;
			if(month==null){
				startDate=year.withDayOfYear(year.dayOfYear().getMinimumValue()).withHourOfDay(year.hourOfDay().getMinimumValue()).toDate();
				endDate=year.withDayOfYear(year.dayOfYear().getMaximumValue()).withHourOfDay(year.hourOfDay().getMaximumValue()).toDate();
				System.out.println("Print " + startDate.toString() + " " +endDate.toString());
			}else{
				startDate=month.withDayOfMonth(month.dayOfMonth().getMinimumValue()).withYear(year.getYear()).toDate();	
				endDate=month.withDayOfMonth(month.dayOfMonth().getMaximumValue()).withYear(year.getYear()).toDate();
			}
			System.out.println("Month null" + endDate.toString()+" "+startDate.toString());
			
			return server.find(PurchaseOrder.class).where().between("date", startDate, endDate).order().asc("date").findList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}
