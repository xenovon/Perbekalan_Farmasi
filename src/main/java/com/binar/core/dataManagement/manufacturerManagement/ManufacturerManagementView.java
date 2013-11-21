package com.binar.core.dataManagement.producerManagement;

public interface ProducerManagementView {

	interface ProducerManagementListener{
		
	}
	
	public void init();
	public void construct();
	public void addListener(ProducerManagementListener listener);
}
