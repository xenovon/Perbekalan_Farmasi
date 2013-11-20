package com.binar.core.dataManagement.supplierManagement;

import com.binar.core.dataManagement.supplierManagement.SupplierManagementView.SupplierManagementListener;
import com.binar.generalFunction.GeneralFunction;

public class SupplierManagementPresenter implements SupplierManagementListener{

	SupplierManagementModel  model;
	SupplierManagementViewImpl view;
	GeneralFunction function;
	
	public SupplierManagementPresenter(SupplierManagementModel model, 
			SupplierManagementViewImpl view, GeneralFunction function) {
		this.model=model;
		this.view=view;
		this.function=function;
		this.view.init();
	}
	
	
}
