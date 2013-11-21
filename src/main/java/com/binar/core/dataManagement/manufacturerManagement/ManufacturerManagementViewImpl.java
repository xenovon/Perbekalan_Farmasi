package com.binar.core.dataManagement.producerManagement;

import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ProducerManagementViewImpl extends VerticalLayout implements ProducerManagementView{

	GeneralFunction function;
	Label title;
	
	public ProducerManagementViewImpl(GeneralFunction function) {
		this.function=function;
	}
	@Override
	public void init() {
		title=new Label("<h2>Manajemen Producer</h2>", ContentMode.HTML);

		construct();
	}
	@Override
	public void construct() {
		this.addComponent(title);
	}
	ProducerManagementListener listener;
	@Override
	public void addListener(ProducerManagementListener listener) {
		this.listener=listener;
	}

	
	
	
}
