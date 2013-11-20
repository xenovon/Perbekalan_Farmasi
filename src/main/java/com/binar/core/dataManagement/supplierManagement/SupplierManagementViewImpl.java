package com.binar.core.dataManagement.supplierManagement;

import com.binar.core.dataManagement.supplierManagement.SupplierManagementView.SupplierManagementListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class SupplierManagementViewImpl extends VerticalLayout implements 
				SupplierManagementView, SupplierManagementListener{

	GeneralFunction function;
	Label title;
	
	
	public SupplierManagementViewImpl(GeneralFunction function){
		this.function=function;
		
	}
	@Override
	public void init() {
		title=new Label("<h2>Manajemen Supplier</h2>", ContentMode.HTML);
	}


	@Override
	public void construct() {
		this.addComponent(title);
			
	}


	SupplierManagementListener listener;
	public void addListener(SupplierManagementListener listener) {
		this.listener=listener;
	}
	
}
