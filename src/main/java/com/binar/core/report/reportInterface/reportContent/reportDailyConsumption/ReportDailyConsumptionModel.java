package com.binar.core.report.reportInterface.reportContent.reportDailyConsumption;

import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class ReportDailyConsumptionModel extends Label{
	private GeneralFunction function;
	/*
	 * Per Bulan
	 * Obat, alkes/bmhp
	 */
	private String goodsType; //ok
	private String periode; //ok
	private String tableCode; //ok
	private String city; //ok
	private String reportDate; //ok
	private String userName; //ok
	private String userNum; //ok
	private String timecycle; //ok

	public ReportDailyConsumptionModel(GeneralFunction function, ReportData data) {
		this.function=function;
		this.setContentMode(ContentMode.HTML);
	
		this.setValue(data.getDateMonth()+" "+data.getSelectedGoods()+" "+data.getType().toString()+" ");
		
	}
}
