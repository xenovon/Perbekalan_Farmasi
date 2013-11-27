package com.binar.core.inventoryManagement;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class Deletion extends CssLayout {
	Label label=new Label("Penghapusan Barang");
	public Deletion() {
		this.setCaption("Penghapusan Barang");
		this.addComponent(label);
		this.addStyleName("tab-content");

	}

}
