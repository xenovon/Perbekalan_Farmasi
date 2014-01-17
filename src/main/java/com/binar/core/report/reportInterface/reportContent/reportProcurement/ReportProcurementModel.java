package com.binar.core.report.reportInterface.reportContent.reportProcurement;

import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportType;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class ReportProcurementModel extends Label{
 
	private GeneralFunction function;
	public ReportProcurementModel(GeneralFunction function, ReportData data) {
		this.function=function;
		this.setContentMode(ContentMode.HTML);
		/*
		 * 		ReportData data=new ReportData(function);
		data.setSelectedMonth((String)selectMonth.getValue());
		data.setSelectedYear((String)selectYear.getValue());
		data.setSelectedGoods((String)selectGoodsType.getValue());
		data.setType(ReportType.PROCUREMENT);

		 */
		this.setValue(data.getDateMonth().toString()+" "+data.getType().toString()+" "+data.getSelectedGoods());
	}
}
