package com.binar.core.report.reportInterface.reportContent.reportDailyConsumption;

import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class ReportDailyConsumptionModel extends Label{
	private GeneralFunction function;
	public ReportDailyConsumptionModel(GeneralFunction function, ReportData data) {
		this.function=function;
		this.setContentMode(ContentMode.HTML);

	}
}
