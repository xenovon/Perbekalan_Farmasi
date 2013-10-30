package com.binar.view;

import com.binar.core.procurement.GoodsOrderReport;
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
	GoodsOrderReport goodsOrderReport=new GoodsOrderReport();
	Invoice invoice=new Invoice();
	PurchaseOrder purchaseOrder=new PurchaseOrder();
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		tabSheet.addTab(purchaseOrder).setCaption("Surat Pesanan");
		tabSheet.addTab(invoice).setCaption("Invoice");
		tabSheet.addTab(goodsOrderReport).setCaption("Laporan Pemesanan Barang");
		tabSheet.setSizeFull();
		this.setCompositionRoot(tabSheet);
		

	}

}
