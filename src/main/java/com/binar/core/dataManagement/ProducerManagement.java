package com.binar.core.dataManagement;

import com.binar.core.dataManagement.producerManagement.ProducerManagementModel;
import com.binar.core.dataManagement.producerManagement.ProducerManagementPresenter;
import com.binar.core.dataManagement.producerManagement.ProducerManagementViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class ProducerManagement extends CssLayout{

	private GeneralFunction function;
	private ProducerManagementViewImpl view;
	private ProducerManagementModel model;
	private ProducerManagementPresenter presenter;
	public ProducerManagement(GeneralFunction function) {
		this.function=function;
		view=new  ProducerManagementViewImpl(function);
		model=new ProducerManagementModel(function);
		
		presenter= new ProducerManagementPresenter(model, view, function);
		
		this.addComponent(view);
		
		this.setCaption("Manajemen Produsen");
		this.setStyleName("tab-content");
		this.setSizeFull();
	}
	
	public ProducerManagementViewImpl getView() {
		return view;
	}
	
	public ProducerManagementPresenter getPresenter() {
		return presenter;
	}
}
