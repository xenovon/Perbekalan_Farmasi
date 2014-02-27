package com.binar;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class Header extends CssLayout{
	Label label=new Label("Aplikasi Perbekalan Farmasi");
	public Header(){
		this.setStyleName("header-style");
		this.addComponent(label);
	}
}
