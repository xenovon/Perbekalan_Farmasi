package com.binar.core.report.reportInterface.reportContent.reportExpiredGoods;

import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class ReportExpiredGoodsModel extends Label{

	private GeneralFunction function;
	public ReportExpiredGoodsModel(GeneralFunction function, ReportData data) {
		this.function=function;
		this.setContentMode(ContentMode.HTML);

		// TODO Auto-generated constructor stub
	}
}
