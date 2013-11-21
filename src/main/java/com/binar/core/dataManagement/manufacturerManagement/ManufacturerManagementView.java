package com.binar.core.dataManagement.manufacturerManagement;

public interface ManufacturerManagementView {

	interface ProducerManagementListener{
		
	}
	
	public void init();
	public void construct();
	public void addListener(ProducerManagementListener listener);
}
