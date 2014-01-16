package com.binar.core.report.reportInterface.reportContent.reportConsumption;

import java.util.List;

import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.entity.GoodsConsumption;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class ReportConsumptionModel extends Label{

	private GeneralFunction function;
	private ReportData data;
	public ReportConsumptionModel(GeneralFunction function, ReportData data) {
		this.function=function;
		this.setContentMode(ContentMode.HTML);
		this.data=data;
	}
	public void setContent(){
		
	}

}
