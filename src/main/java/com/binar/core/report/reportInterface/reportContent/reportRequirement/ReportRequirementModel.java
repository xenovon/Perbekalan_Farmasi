package com.binar.core.report.reportInterface.reportContent.reportRequirement;

import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class ReportRequirementModel extends Label{

	private GeneralFunction function;
	public ReportRequirementModel(GeneralFunction function, ReportData data) {
		this.function=function;
		this.setContentMode(ContentMode.HTML);

		this.setValue(data.getDateMonth()+" "+data.getSelectedGoods()+" "+data.getType().toString()+" ");

	}
}
