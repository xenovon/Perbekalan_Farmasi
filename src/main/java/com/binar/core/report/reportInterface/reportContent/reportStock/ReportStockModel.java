package com.binar.core.report.reportInterface.reportContent.reportStock;

import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class ReportStockModel extends Label {
 
	private GeneralFunction function;
	public ReportStockModel(GeneralFunction function, ReportData data) {
		this.function=function;
		this.setContentMode(ContentMode.HTML);

		this.setValue(data.getDate()+" "+data.getSelectedGoods()+" "+data.getType().toString()+" ");

	}
}
