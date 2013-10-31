package com.binar.core.procurement;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class Invoice extends CssLayout {

	Label label=new Label("Faktur");
	public Invoice() {
		this.setCaption("Faktur");
		this.addComponent(label);
		this.addStyleName("tab-content");
		
	}
}
