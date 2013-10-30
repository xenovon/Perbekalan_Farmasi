package com.binar.core.dataManagement;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class ProducerManagement extends CssLayout{

	Label label=new Label("Manajemen Produsen");
	public ProducerManagement() {
		this.setCaption("Manajemen Produsen");
		this.addComponent(label);
	}
}
