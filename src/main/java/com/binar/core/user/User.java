package com.binar.core.user;

import com.binar.core.user.userList.UserListModel;
import com.binar.core.user.userList.UserListPresenter;
import com.binar.core.user.userList.UserListViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class User extends CssLayout{
	UserListModel model;
	UserListPresenter presenter;
	UserListViewImpl view;
	Label label=new Label("Faktur");
	
	GeneralFunction function;
	
	public User(GeneralFunction function) {
		model=new UserListModel(function);
		view=new UserListViewImpl(function);
		presenter=new UserListPresenter(model, view, function);
		this.setCaption("Surat Pesanan");
		this.addComponent(view);
		this.addStyleName("tab-content");
	
	}

}
