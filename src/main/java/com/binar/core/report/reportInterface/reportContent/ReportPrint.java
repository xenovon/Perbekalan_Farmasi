package com.binar.core.report.reportInterface.reportContent;

import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportType;
import com.binar.core.report.reportInterface.reportContent.reportConsumption.ReportConsumptionModel;
import com.binar.core.report.reportInterface.reportContent.reportDailyConsumption.ReportDailyConsumptionModel;
import com.binar.core.report.reportInterface.reportContent.reportExpiredGoods.ReportExpiredGoodsModel;
import com.binar.core.report.reportInterface.reportContent.reportProcurement.ReportProcurementModel;
import com.binar.core.report.reportInterface.reportContent.reportReceipt.ReportReceiptModel;
import com.binar.core.report.reportInterface.reportContent.reportRequirement.ReportRequirementModel;
import com.binar.core.report.reportInterface.reportContent.reportStock.ReportStockModel;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class ReportPrint extends UI{

	/*
	 * (non-Javadoc)
	 * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
	 * 
	 * File UI untuk mencetak laporan, menggunakan file model yang ada di masing-masing package report
	 * 
	 */
	GeneralFunction function;
	ReportConsumptionModel modelConsumption;
	ReportDailyConsumptionModel modelDailyConsumption;
	ReportExpiredGoodsModel modelExpiredGoods;
	ReportProcurementModel modelProcurement;
	ReportReceiptModel modelReceipt;
	ReportRequirementModel modelRequirement;
	ReportStockModel modelStock;
	ReportParameter parameter;
	
	@Override
	protected void init(VaadinRequest request) {
		function=new GeneralFunction();
		parameter=new ReportParameter(function);
		
		try {
			ReportData data=parameter.generateParameter(request);
			if(data!=null){
				setContent(data);
			}
		} catch (Exception e) {
			setContent(new Label(e.getMessage() +" "+e.getCause().toString()));
		}
		JavaScript.getCurrent().execute(
				"setTimeout(function() {" +
				" print(); self.close();}, 0);");
	}
	
	public void setContent(ReportData data) throws Exception{
		if(data.getType()==ReportType.DAILY_CONSUMPTION){
			modelDailyConsumption=new ReportDailyConsumptionModel(function, data);
			setContent(modelDailyConsumption);
		}else if(data.getType()==ReportType.CONSUMPTION){
			modelConsumption = new ReportConsumptionModel(function, data);
			setContent(modelConsumption);
		}else if(data.getType()==ReportType.EXPIRED_GOODS){
			modelExpiredGoods=new ReportExpiredGoodsModel(function, data);
			setContent(modelExpiredGoods);
		}else if(data.getType()==ReportType.PROCUREMENT){
			modelProcurement=new ReportProcurementModel(function, data);
			setContent(modelProcurement);
		}else if(data.getType()==ReportType.RECEIPT){
			modelReceipt=new ReportReceiptModel(function, data);
			setContent(modelReceipt);
		}else if(data.getType()==ReportType.REQUIREMENT){
			modelRequirement=new ReportRequirementModel(function, data);
			setContent(modelConsumption);
		}else if(data.getType()==ReportType.STOCK){
			modelStock=new ReportStockModel(function, data);
			setContent(modelStock);
		}
		
	}

}
