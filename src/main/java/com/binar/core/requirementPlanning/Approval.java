package com.binar.core.requirementPlanning;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class Approval extends CustomComponent {

	CssLayout layout=new CssLayout();
	Label label=new Label("Persetujuan");
	
	public Approval() {
		layout.setCaption("Persetujuan");
		layout.addComponent(label);
		layout.setSizeFull();
		
		this.setCompositionRoot(layout);
	}
}
