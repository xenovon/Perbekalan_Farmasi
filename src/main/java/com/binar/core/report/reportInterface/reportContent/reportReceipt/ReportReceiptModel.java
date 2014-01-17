package com.binar.core.report.reportInterface.reportContent.reportReceipt;

import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportType;
import com.binar.core.report.reportInterface.reportContent.ReportData.PeriodeType;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class ReportReceiptModel extends Label {

	private GeneralFunction function;
	public ReportReceiptModel(GeneralFunction function, ReportData data) {
		this.function=function;
		this.setContentMode(ContentMode.HTML);
	/*
	 * 		PeriodeType periodeType;
		if(isViewMonthMode()){
			periodeType=PeriodeType.BY_MONTH;
		}else{
			periodeType=PeriodeType.BY_DAY;
		}
		data.setPeriodeType(periodeType);
		data.setSelectedDay(selectDate.getValue());
		data.setSelectedMonth((String) selectMonth.getValue());
		data.setSelectedYear((String) selectYear.getValue());
		data.setSelectedGoods((String)selectGoodsType.getValue());
		data.setType(ReportType.RECEIPT);

	 */
		this.setValue(data.getPeriodeType().toString()+" "+data.getDate()!=null?data.getDate().toString():"a"+" "+data.getDateMonth()!=null?data.getDateMonth().toString():"a"+" "+
				data.getSelectedGoods()+" "+data.getType().toString());
	}
	
	
	
}

