package com.binar.view;

import com.binar.core.user.User;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class UserManagementView extends CustomComponent implements View {

	Label label=new Label("User Management ");

	User user;
	@Override
	public void enter(ViewChangeEvent event) {
		user=new User(new GeneralFunction());
		this.setCompositionRoot(user);
	}

}
