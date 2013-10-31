package com.binar.core.inventoryManagement;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class Receipt extends CssLayout {

	Label label=new Label("Penerimaan");
	public Receipt() {
		this.setCaption("Penerimaan");
		this.addComponent(label);
		this.addStyleName("tab-content");

	}
}
