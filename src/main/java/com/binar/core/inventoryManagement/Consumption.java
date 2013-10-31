package com.binar.core.inventoryManagement;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class Consumption extends CssLayout {

	Label label=new Label("Pemakaian");
	public Consumption() {
		this.setCaption("Pemakaian");
		this.addComponent(label);
		this.addStyleName("tab-content");

	}
}
