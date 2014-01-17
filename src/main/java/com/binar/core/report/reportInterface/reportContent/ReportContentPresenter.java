package com.binar.core.report.reportInterface.reportContent;

import java.util.Collection;
import java.util.Map;

import com.binar.core.procurement.purchaseOrder.printPurchaseOrder.GeneralPrint;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportContentListener;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportType;
import com.binar.core.report.reportInterface.reportContent.reportConsumption.ReportConsumptionViewImpl;
import com.binar.core.report.reportInterface.reportContent.reportDailyConsumption.ReportDailyConsumptionViewImpl;
import com.binar.core.report.reportInterface.reportContent.reportExpiredGoods.ReportExpiredGoodsViewImpl;
import com.binar.core.report.reportInterface.reportContent.reportProcurement.ReportProcurementViewImpl;
import com.binar.core.report.reportInterface.reportContent.reportReceipt.ReportReceiptViewImpl;
import com.binar.core.report.reportInterface.reportContent.reportRequirement.ReportRequirementViewImpl;
import com.binar.core.report.reportInterface.reportContent.reportStock.ReportStockViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public class ReportContentPresenter  implements ReportContentListener{

	ReportConsumptionViewImpl reportConsumption;
	ReportDailyConsumptionViewImpl reportDailyConsumption;
	ReportExpiredGoodsViewImpl reportExpiredGoods;
	ReportProcurementViewImpl reportProcurement;
	ReportReceiptViewImpl reportReceipt;
	ReportRequirementViewImpl reportRequirement;
	ReportStockViewImpl reportStock;
	
	GeneralFunction function;
	
	ReportParameter parameter;
	/*
	 * Struktur MVP disini agak beda, satu presenter bisa nampung > 1 view
	 */
	public ReportContentPresenter(GeneralFunction function, ReportParameter parameter) {
		this.function=function;
		this.parameter=parameter;
	}
	public Component getViewConsumption(){
		if(reportConsumption==null){
			reportConsumption=new ReportConsumptionViewImpl(function);
			reportConsumption.init();
			reportConsumption.setListener(this);
		}
		return reportConsumption;
	}
	public Component getViewDailyConsumption(){
		if(reportDailyConsumption==null){
			reportDailyConsumption=new ReportDailyConsumptionViewImpl(function);
			reportDailyConsumption.init();
			reportDailyConsumption.setListener(this);
		}
		return reportDailyConsumption;

	}
	
	public Component getExpiredGoods(){
		if(reportExpiredGoods==null){
			reportExpiredGoods=new ReportExpiredGoodsViewImpl(function);
			reportExpiredGoods.init();
			reportExpiredGoods.setListener(this);
		}
		return reportExpiredGoods;
	}
	
	public Component getProcurement(){
		if(reportProcurement==null){
			reportProcurement=new ReportProcurementViewImpl(function);
			reportProcurement.init();
			reportProcurement.setListener(this);
		}
		return reportProcurement;
	}
	public Component getReceipt(){
		if(reportReceipt==null){
			reportReceipt=new ReportReceiptViewImpl(function);
			reportReceipt.init();
			reportReceipt.setListener(this);
		}
		return reportReceipt;
	}
	public Component getRequirement(){
		if(reportRequirement==null){
			reportRequirement=new ReportRequirementViewImpl(function);
			reportRequirement.init();
			reportRequirement.setListener(this);
		}
		return reportRequirement;
	}
	public Component getStock(){
		if(reportStock==null){
			reportStock=new ReportStockViewImpl(function);
			reportStock.init();
			reportStock.setListener(this);
		}
		return reportStock;
	}

	@Override
	public void cancelClick(ReportType type) {
		Collection<Window> list;
		switch (type) {
		case CONSUMPTION:
			list=reportConsumption.getUI().getWindows();
			for(Window w:list){
				reportConsumption.getUI().removeWindow(w);
			}
			
			break;
		case DAILY_CONSUMPTION: 
			list=reportConsumption.getUI().getWindows();
			for(Window w:list){
				reportConsumption.getUI().removeWindow(w);
			}
			break;
		case EXPIRED_GOODS:
			list=reportExpiredGoods.getUI().getWindows();
			for(Window w:list){
				reportExpiredGoods.getUI().removeWindow(w);
			}
			break;	
		case PROCUREMENT:
			list=reportProcurement.getUI().getWindows();
			for(Window w:list){
				reportProcurement.getUI().removeWindow(w);
			}
			break;		
		case RECEIPT:
			list=reportReceipt.getUI().getWindows();
			for(Window w:list){
				reportReceipt.getUI().removeWindow(w);
			}
			break;		
		case REQUIREMENT: 
			list=reportRequirement.getUI().getWindows();
			for(Window w:list){
				reportRequirement.getUI().removeWindow(w);
			}
			break;		
		case STOCK :
			list=reportStock.getUI().getWindows();
			for(Window w:list){
				reportStock.getUI().removeWindow(w);
			}
			break;	
		default:
			break;
		}
		

	}
	@Override
	public void printClick(ReportType report, ReportData data) {
		Map<String, String>  param=parameter.generateParameter(report, data);
		Button button;
		
		switch (report) {
		case CONSUMPTION:button=reportConsumption.getPrintButton();
						 showPrintWindow(param, button);
						break;
		case DAILY_CONSUMPTION : button=reportDailyConsumption.getPrintButton();
		 						showPrintWindow(param, button);
		 						break;
		case EXPIRED_GOODS : button=reportExpiredGoods.getPrintButton();
		 					 showPrintWindow(param, button);
		 					 break;
		case PROCUREMENT : button=reportProcurement.getPrintButton();
		 				   showPrintWindow(param, button);
		break;
		case RECEIPT :button=reportReceipt.getPrintButton();
					  showPrintWindow(param, button);
					  break;
		case REQUIREMENT : button=reportRequirement.getPrintButton();
						   showPrintWindow(param, button);
		 				   break;
		case STOCK : button=reportStock.getPrintButton();
					 showPrintWindow(param, button);
					 break;
		default:
			break;
		}
	}
	/*
	 * Data yang dibutuhin
	 * Untuk jenis obat
	 *   nama parmeter: type;
	 *   value : obat, bmhp/alkes
	 * Untuk Jenis laporan
	 *   nama parameter : reporttype
	 *   value : yang ada di enum ReportType
	 * Tipe Periode 
	 *   nama : timeperiode
	 *   value, sesuai yang di enum  PerideType
	 * Rentang Tanggal 
	 *  nama parameter : daterange 
	 *  value : dd-MM-yyyy.dd-MM-yyyy
	 * Tanggal 
	 * nama parameter : date
	 * value : dd-MM-yyyy
	 * 
	 * Peride Per bulan
	 * nama parameter yearmonth
	 * value : bulan-tahun
	 * 
	 */
	public void showPrintWindow(Map<String, String> param, Button button){
		// Create an opener extension
		BrowserWindowOpener opener =
		new BrowserWindowOpener(ReportPrint.class);
		opener.setFeatures("height=200,width=400,resizable");
		// A button to open the printer-friendly page.
		for(Map.Entry<String, String> entry:param.entrySet()){
			String key=entry.getKey();
			String value=entry.getValue();
			
			opener.setParameter(key, value);
		}
		opener.extend(button);
		button.click();

	}
	
}
