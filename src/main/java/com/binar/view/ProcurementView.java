package com.binar.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class ProcurementView extends CustomComponent implements View {

	Label label=new Label("Procurement Management ");

	@Override
	public void enter(ViewChangeEvent event) {
		this.setCompositionRoot(label);

	}

}
