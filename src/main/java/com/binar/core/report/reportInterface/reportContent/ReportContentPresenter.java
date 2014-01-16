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
	
	public ReportContentPresenter(GeneralFunction function, ReportParameter parameter) {
		this.function=function;
		this.parameter=parameter;
		reportConsumption=new ReportConsumptionViewImpl(function);
		reportDailyConsumption=new ReportDailyConsumptionViewImpl(function);
		reportExpiredGoods=new ReportExpiredGoodsViewImpl(function);
		reportProcurement= new ReportProcurementViewImpl(function);
		reportReceipt=new ReportReceiptViewImpl(function);
		reportRequirement= new  ReportRequirementViewImpl(function);
		reportStock=new ReportStockViewImpl (function);
	}
	@Override
	public void cancelClick() {
		Collection<Window> list=reportConsumption.getUI().getWindows();
		for(Window w:list){
			reportConsumption.getUI().removeWindow(w);
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
