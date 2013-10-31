package com.binar.core.inventoryManagement;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class Stock extends CssLayout {

	Label label=new Label("Stok Barang");
	public Stock() {
		this.setCaption("Stok Barang");
		this.addComponent(label);
		this.addStyleName("tab-content");

	}
}
