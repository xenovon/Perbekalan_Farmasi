package com.binar.core.procurement;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class PurchaseOrder extends CssLayout {

	Label label=new Label("Surat Pesanan");
	public PurchaseOrder() {
		this.setCaption("Surat Pesanan");
		this.addComponent(label);
	}
}
