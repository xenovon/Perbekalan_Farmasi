package com.binar.core.inventoryManagement;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class StockList extends CssLayout {

	Label label=new Label("Stok Barang");
	public StockList() {
		this.setCaption("Stok Barang");
		this.addComponent(label);
		this.addStyleName("tab-content");

	}
}
