package com.binar.core.requirementPlanning;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class ReqPlanningList extends CustomComponent {

	CssLayout layout=new CssLayout();
	Label label=new Label("Daftar Rencana Kebutuhan");
	
	public ReqPlanningList() {
		layout.setCaption("Daftar Rencana Kebutuhan");
		layout.addComponent(label);
		setCompositionRoot(layout);
	}
}
