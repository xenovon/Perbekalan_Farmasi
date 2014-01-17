package com.binar.core.report.reportInterface.reportContent.reportExpiredGoods;

import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class ReportExpiredGoodsModel extends Label{

	private GeneralFunction function;
	private String goodsType; //ok
	private String periode; //ok
	private String tableCode; //ok
	private String city; //ok
	private String reportDate; //ok
	private String userName; //ok
	private String userNum; //ok
	private String timecycle; //ok
	
	//	 	Per rentang waktu, dengan defaultnya satu tahun

	public ReportExpiredGoodsModel(GeneralFunction function, ReportData data) {
		this.function=function;
		this.setContentMode(ContentMode.HTML);
		this.setValue(data.getType().toString()+" "+data.getAccepted()+" "+data.getDateEnd().toString()+" "+data.getDateStart());
		// TODO Auto-generated constructor stub
	}
}
