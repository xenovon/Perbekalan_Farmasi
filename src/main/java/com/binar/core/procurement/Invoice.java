package com.binar.core.procurement;

import com.binar.core.procurement.invoice.InvoiceModel;
import com.binar.core.procurement.invoice.InvoicePresenter;
import com.binar.core.procurement.invoice.InvoiceViewImpl;
import com.binar.core.procurement.purchaseOrder.PurchaseOrderModel;
import com.binar.core.procurement.purchaseOrder.PurchaseOrderPresenter;
import com.binar.core.procurement.purchaseOrder.PurchaseOrderViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class Invoice extends CssLayout {

	InvoiceModel model;
	InvoiceViewImpl view;
	InvoicePresenter presenter;
	Label label=new Label("Faktur");
	
	GeneralFunction function;
	
	public Invoice(GeneralFunction function) {
		model=new InvoiceModel(function);
		view=new InvoiceViewImpl(function);
		presenter=new InvoicePresenter(view, model, function);
		this.setCaption("Surat Pesanan");
		this.addComponent(view);
		this.addStyleName("tab-content");
		
	}
}
