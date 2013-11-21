package com.binar.core.dataManagement.producerManagement;

import com.binar.generalFunction.GeneralFunction;

public class ProducerManagementPresenter {

	ProducerManagementViewImpl view;
	ProducerManagementModel model;
	GeneralFunction function;
	
	public ProducerManagementPresenter(ProducerManagementModel model, 
			ProducerManagementViewImpl view, GeneralFunction function){
	this.model=model;
	this.view=view;
	this.function=function;
	
	view.init();
		
	}
}
