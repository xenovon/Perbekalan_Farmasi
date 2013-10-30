package com.binar.core.requirementPlanning;

import javax.swing.text.html.CSS;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class InputRequirementPlanning extends CssLayout {

	VerticalLayout layout=new VerticalLayout();
	Label label=new Label("Input Rencana Kebutuhan");
	
	public InputRequirementPlanning() {
		this.setCaption("Input Rencana Kebutuhan");
		this.addComponent(label);
		this.addStyleName("tab-content");

	}
}
