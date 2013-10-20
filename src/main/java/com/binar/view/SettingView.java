package com.binar.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class SettingView extends CustomComponent  implements View {

	private Label label=new Label("Setting View");
	@Override
	public void enter(ViewChangeEvent event) {
		this.setCompositionRoot(label);
		
	}

}
