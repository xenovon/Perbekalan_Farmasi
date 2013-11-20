package com.binar.core.dataManagement.supplierManagement;

public interface SupplierManagementView {

	interface SupplierManagementListener{
		
	}
	
	public void init();
	public void construct();
	public void addListener(SupplierManagementListener listener);
}
