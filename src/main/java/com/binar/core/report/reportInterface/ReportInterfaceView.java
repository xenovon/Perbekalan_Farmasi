package com.binar.core.report.reportInterface;

import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.vaadin.ui.Button;

public interface ReportInterfaceView {

	interface ReportInterfaceListener{
		 public void buttonRequirementClick();
		 public void buttonProcurementClick();
		 public void buttonDailyConsumptionClick();
		 public void buttonReceiptClick();
		 public void buttonConsumptionClick();
		 public void buttonStockClick();
		 public void buttonExpiredGoodsClick();
	}
	
	public void init();
	public void construct();
	public void setListener(ReportInterfaceListener listener);
	
}
