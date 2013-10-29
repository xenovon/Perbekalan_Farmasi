package com.binar.core.requirementPlanning;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class InputRequirementPlanning extends CustomComponent {

	CssLayout layout=new CssLayout();
	Label label=new Label("Input Rencana Kebutuhan");
	
	public InputRequirementPlanning() {
		layout.setCaption("Input Rencana Kebutuhan");
		layout.addComponent(label);
		this.setCompositionRoot(layout);
	}
}
