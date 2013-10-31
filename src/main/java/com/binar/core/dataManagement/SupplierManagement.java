package com.binar.core.dataManagement;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class SupplierManagement extends CssLayout {

	Label label=new Label("Manajemen Supplier");
	public SupplierManagement() {
		this.setCaption("Manajemen Supplier");
		this.addComponent(label);
		this.addStyleName("tab-content");

	}	
}
