package com.binar.core.dataManagement.manufacturerManagement;

import com.binar.generalFunction.GeneralFunction;

public class ManufacturerManagementPresenter  {

	ManufacturerManagementViewImpl view;
	ManufacturerManagementModel model;
	GeneralFunction function;
	
	public ManufacturerManagementPresenter(ManufacturerManagementModel model, 
			ManufacturerManagementViewImpl view, GeneralFunction function){
		this.model=model;
		this.view=view;
		this.function=function;	
		view.init();
	}
}
