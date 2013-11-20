package com.binar.core.dataManagement;

import com.binar.core.dataManagement.supplierManagement.SupplierManagementModel;
import com.binar.core.dataManagement.supplierManagement.SupplierManagementPresenter;
import com.binar.core.dataManagement.supplierManagement.SupplierManagementViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class SupplierManagement extends CssLayout {

	private SupplierManagementModel model;
	private SupplierManagementViewImpl view;
	private SupplierManagementPresenter presenter;
	private GeneralFunction function;
	
	public SupplierManagement(GeneralFunction function) {
		this.function=function;
		
		model=new SupplierManagementModel(function);
		view = new SupplierManagementViewImpl(function);
		presenter=new SupplierManagementPresenter(model, view, function);
		
		this.setCaption("Manajemen Supplier");
		this.addComponent(view);
		this.addStyleName("tab-content");

	}	
	public SupplierManagementViewImpl getView() {
		return view;
	}
	public SupplierManagementPresenter getPresenter() {
		return presenter;
	}
}
