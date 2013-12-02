package com.binar.view;

import com.binar.core.procurement.Invoice;
import com.binar.core.procurement.PurchaseOrder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;

public class ProcurementView extends CustomComponent implements View {

	Label label=new Label("Procurement Management ");
	TabSheet tabSheet=new TabSheet();
	Invoice invoice;
	PurchaseOrder purchaseOrder;
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		invoice=new Invoice();
//		purchaseOrder = new PurchaseOrder();
		tabSheet.addTab(purchaseOrder).setCaption("Surat Pesanan");
		tabSheet.addTab(invoice).setCaption("Faktur");
		tabSheet.setSizeFull();
		this.setCompositionRoot(tabSheet);
	}

}
