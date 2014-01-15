package com.binar.core.report.reportInterface.reportContent;

import com.binar.core.procurement.purchaseOrder.printPurchaseOrder.GeneralPrint;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportContentListener;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportType;
import com.vaadin.server.BrowserWindowOpener;

public class ReportContentPresenter  implements ReportContentListener{

	@Override
	public void cancelClick() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void printClick(ReportType report, ReportData data) {
		// TODO Auto-generated method stub
		
	}
	
	public void showPrintWindow(ReportData data){
		// Create an opener extension
		BrowserWindowOpener opener =
		new BrowserWindowOpener(GeneralPrint.class);
		opener.setFeatures("height=200,width=400,resizable");
		// A button to open the printer-friendly page.
//		opener.setParameter("ID", idPurchase+"");
//		opener.extend(view.getButtonPrint());
		

	}
	
}
