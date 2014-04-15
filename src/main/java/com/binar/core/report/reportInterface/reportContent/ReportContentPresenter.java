package com.binar.core.report.reportInterface.reportContent;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.binar.core.procurement.purchaseOrder.printPurchaseOrder.GeneralPrint;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportContentListener;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportType;
import com.binar.core.report.reportInterface.reportContent.reportConsumption.ReportConsumptionResultView;
import com.binar.core.report.reportInterface.reportContent.reportConsumption.ReportConsumptionViewImpl;
import com.binar.core.report.reportInterface.reportContent.reportDailyConsumption.ReportDailyConsumptionViewImpl;
import com.binar.core.report.reportInterface.reportContent.reportExpiredGoods.ReportExpiredGoodsResultView;
import com.binar.core.report.reportInterface.reportContent.reportExpiredGoods.ReportExpiredGoodsViewImpl;
import com.binar.core.report.reportInterface.reportContent.reportProcurement.ReportProcurementViewImpl;
import com.binar.core.report.reportInterface.reportContent.reportReceipt.ReportReceiptResultView;
import com.binar.core.report.reportInterface.reportContent.reportReceipt.ReportReceiptViewImpl;
import com.binar.core.report.reportInterface.reportContent.reportRequirement.ReportRequirementResultView;
import com.binar.core.report.reportInterface.reportContent.reportRequirement.ReportRequirementViewImpl;
import com.binar.core.report.reportInterface.reportContent.reportStock.ReportStockResultView;
import com.binar.core.report.reportInterface.reportContent.reportStock.ReportStockViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class ReportContentPresenter  implements ReportContentListener{

	ReportConsumptionViewImpl reportConsumption;
	ReportDailyConsumptionViewImpl reportDailyConsumption;
	ReportExpiredGoodsViewImpl reportExpiredGoods;
	ReportProcurementViewImpl reportProcurement;
	ReportReceiptViewImpl reportReceipt;
	ReportRequirementViewImpl reportRequirement;
	ReportStockViewImpl reportStock;
	
	public final String WINDOW_WIDTH="750px";
	public final String WINDOW_WIDTH_COMPACT="500px";
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
			reportConsumption.setListener(this);
			reportConsumption.init();
		}
		return reportConsumption;
	}
	public Component getViewDailyConsumption(){
		if(reportDailyConsumption==null){
			reportDailyConsumption=new ReportDailyConsumptionViewImpl(function);
			reportDailyConsumption.setListener(this);
			reportDailyConsumption.init();
		}
		return reportDailyConsumption;

	}
	public Component getExpiredGoods(){
		if(reportExpiredGoods==null){
			reportExpiredGoods=new ReportExpiredGoodsViewImpl(function);
			reportExpiredGoods.setListener(this);
			reportExpiredGoods.init();
		}
		return reportExpiredGoods;
	}
	
	public Component getProcurement(){
		if(reportProcurement==null){
			reportProcurement=new ReportProcurementViewImpl(function);
			reportProcurement.setListener(this);
			reportProcurement.init();
		}
		return reportProcurement;
	}
	public Component getReceipt(){
		if(reportReceipt==null){
			reportReceipt=new ReportReceiptViewImpl(function);
			reportReceipt.setListener(this);
			reportReceipt.init();
		}
		return reportReceipt;
	}
	public Component getRequirement(){
		if(reportRequirement==null){
			reportRequirement=new ReportRequirementViewImpl(function);
			reportRequirement.setListener(this);
			reportRequirement.init();
		}
		return reportRequirement;
	}
	public Component getStock(){
		if(reportStock==null){
			reportStock=new ReportStockViewImpl(function);
			reportStock.setListener(this);
			reportStock.init();
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
			list=reportDailyConsumption.getUI().getWindows();
			for(Window w:list){
				reportDailyConsumption.getUI().removeWindow(w);
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
		BrowserWindowOpener opener;
		switch (report) {
		case CONSUMPTION:opener=reportConsumption.getOpener();
						 setOpenerParameter(opener, param);
						break;
		case DAILY_CONSUMPTION : opener=reportDailyConsumption.getOpener();
								setOpenerParameter(opener, param);
		 						break;
		case EXPIRED_GOODS : opener=reportExpiredGoods.getOpener();
							 setOpenerParameter(opener, param);
							 					 break;
		case PROCUREMENT : opener=reportProcurement.getOpener();
							 setOpenerParameter(opener, param);
							 break;
		case RECEIPT :opener=reportReceipt.getOpener();
							 setOpenerParameter(opener, param);	
							 break;
		case REQUIREMENT : opener=reportRequirement.getOpener();
						 setOpenerParameter(opener, param);
						 break;
		case STOCK : opener=reportStock.getOpener();
						 setOpenerParameter(opener, param);
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
	
	//untuk menambahkan parameter untuk pembuka window, parameter ditambahkan setiap data berubah
	public void setOpenerParameter(BrowserWindowOpener opener, Map<String, String> param){
		
		for(Map.Entry<String, String> entry:param.entrySet()){
			String key=entry.getKey();
			String value=entry.getValue();
			opener.setParameter(key, value);
		}		
		
	}
	Window window;	
	public void showClick(ReportType report, ReportData data){
		/* Dapatkan window */
		Collection<Window> windows=UI.getCurrent().getWindows();
		if(windows.size()>=1){
			window=windows.iterator().next();
		}else{
			return;
		}
		
		if(windows.size()>1){
			while(windows.iterator().hasNext()){
				UI.getCurrent().removeWindow(windows.iterator().next());				
			}
			
			UI.getCurrent().addWindow(window);
		}
		
		//End dapatkan window
		
		if(report==ReportType.CONSUMPTION){
			window.setContent(getComponentConsumption(data));
			window.setWidth(WINDOW_WIDTH);
		}else if(report==ReportType.DAILY_CONSUMPTION){
			
		}else if(report==ReportType.EXPIRED_GOODS){
			window.setContent(getComponentExpiredGoods(data));
			window.setWidth(WINDOW_WIDTH);
		}else if(report==ReportType.PROCUREMENT){

		}else if(report==ReportType.RECEIPT){
			window.setContent(getComponentReceipt(data));
			window.setWidth(WINDOW_WIDTH);

		}else if(report==ReportType.REQUIREMENT){
			window.setContent(getComponentRequirement(data));
			window.setWidth(WINDOW_WIDTH);
		
		}else if(report==ReportType.STOCK){
			window.setContent(getComponentStock(data));
			window.setWidth(WINDOW_WIDTH);
		}
		
	}
	@Override
	public void backClick(ReportType report) {
		if(window!=null){
			if(report==ReportType.CONSUMPTION){
				window.setContent(getViewConsumption());
				window.setWidth(WINDOW_WIDTH_COMPACT);
			}else if(report==ReportType.DAILY_CONSUMPTION){
				
			}else if(report==ReportType.EXPIRED_GOODS){
				window.setContent(getExpiredGoods());
				window.setWidth(WINDOW_WIDTH_COMPACT);

			}else if(report==ReportType.PROCUREMENT){

			}else if(report==ReportType.RECEIPT){
				window.setContent(getReceipt());
				window.setWidth(WINDOW_WIDTH_COMPACT);

			}else if(report==ReportType.REQUIREMENT){
				window.setContent(getRequirement());
				window.setWidth(WINDOW_WIDTH_COMPACT);

			}else if(report==ReportType.STOCK){
				window.setContent(getStock());
				window.setWidth(WINDOW_WIDTH_COMPACT);
			}
		}
	}
	private Component getComponentConsumption(ReportData data){
		ReportConsumptionResultView resultView=new ReportConsumptionResultView(function);
		resultView.init(data, this);
		return resultView;
	}
	private Component getComponentStock(ReportData data){
		ReportStockResultView resultView=new ReportStockResultView(function);
		resultView.init(data, this);
		return resultView;
	}
	private Component getComponentReceipt(ReportData data){
		ReportReceiptResultView resultView=new ReportReceiptResultView(function);
		resultView.init(data, this);
		return resultView;
	}
	private Component getComponentRequirement(ReportData data){
		ReportRequirementResultView resultView=new ReportRequirementResultView(function);
		resultView.init(data, this);
		return resultView;
	}
	private Component getComponentExpiredGoods(ReportData data){
		ReportExpiredGoodsResultView resultView=new ReportExpiredGoodsResultView(function);
		resultView.init(data, this);
		return resultView;
	}	
	
}
