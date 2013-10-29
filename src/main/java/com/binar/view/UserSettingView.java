package com.binar.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class UserSettingView extends CustomComponent implements View {

	Label label=new Label("User Menu");

	@Override
	public void enter(ViewChangeEvent event) {
		this.setCompositionRoot(label);

	}

}
