package com.binar.core.dataManagement;

import com.binar.core.dataManagement.manufacturerManagement.ManufacturerManagementModel;
import com.binar.core.dataManagement.manufacturerManagement.ManufacturerManagementPresenter;
import com.binar.core.dataManagement.manufacturerManagement.ManufacturerManagementViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class ManufacturerManagement extends CssLayout{

	private GeneralFunction function;
	private ManufacturerManagementViewImpl view;
	private ManufacturerManagementModel model;
	private ManufacturerManagementPresenter presenter;
	public ManufacturerManagement(GeneralFunction function) {
		this.function=function;
		view=new  ManufacturerManagementViewImpl(function);
		model=new ManufacturerManagementModel(function);
		
		presenter= new ManufacturerManagementPresenter(model, view, function);
		
		this.addComponent(view);
		this.setCaption("Manajemen Produsen");
		this.setStyleName("tab-content");
		this.setSizeFull();
	}
	
	public ManufacturerManagementViewImpl getView() {
		return view;
	}
	
	public ManufacturerManagementPresenter getPresenter() {
		return presenter;
	}
}
