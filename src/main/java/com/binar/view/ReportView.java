package com.binar.view;

import com.binar.core.procurement.Invoice;
import com.binar.core.procurement.PurchaseOrder;
import com.binar.core.report.Report;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;

public class ReportView extends CustomComponent implements View {

	Label label=new Label("Procurement Management ");
	Report report;
	GeneralFunction function;
	
	@Override
	public void enter(ViewChangeEvent event) {
		function=new GeneralFunction();
		report=new Report(function);
		this.setCompositionRoot(report);
	}

}
