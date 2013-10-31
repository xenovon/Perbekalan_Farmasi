package com.binar.core.requirementPlanning;

import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;

public class Approval extends  CssLayout {

	
	VerticalLayout layout=new VerticalLayout();
	Label label=new Label("Persetujuan");
	
	public Approval() {
		this.setCaption("Persetujuan");
		this.addComponent(label);
		this.setSizeFull();
		this.addStyleName("tab-content");
	}
}
