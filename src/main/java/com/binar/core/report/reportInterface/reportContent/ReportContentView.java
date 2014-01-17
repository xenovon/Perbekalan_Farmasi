package com.binar.core.report.reportInterface.reportContent;

import com.binar.core.report.reportInterface.ReportInterfaceView.ReportInterfaceListener;
import com.vaadin.ui.Button;

public interface ReportContentView {
	
	public enum ReportType{
		CONSUMPTION, DAILY_CONSUMPTION, EXPIRED_GOODS, PROCUREMENT, RECEIPT,REQUIREMENT, STOCK,
	}
	interface ReportContentListener{
		public void cancelClick(ReportType report);
		public void printClick(ReportType report, ReportData data);
	}
	
	public void init();
	public void construct();
	public void setListener(ReportContentListener listener);
	public Button getPrintButton();
	public ReportData getReportData();

	
}
