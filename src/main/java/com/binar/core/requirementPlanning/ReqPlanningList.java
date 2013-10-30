package com.binar.core.requirementPlanning;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ReqPlanningList extends CssLayout {

	Label label=new Label("Daftar Rencana Kebutuhan");
	
	public ReqPlanningList() {
		this.setCaption("Daftar Rencana Kebutuhan");
		this.addComponent(label);
		this.addStyleName("tab-content");

	}
}
