package com.binar.core.dataManagement.manufacturerManagement;

import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ManufacturerManagementViewImpl extends VerticalLayout implements ManufacturerManagementView{

	GeneralFunction function;
	Label title;
	
	public ManufacturerManagementViewImpl(GeneralFunction function) {
		this.function=function;
	}
	@Override
	public void init() {
		title=new Label("<h2>Manajemen Produsen</h2>", ContentMode.HTML);

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
