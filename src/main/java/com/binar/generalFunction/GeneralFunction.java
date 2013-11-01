package com.binar.generalFunction;

/* Kelas untuk agregrator fungsi */

public class GeneralFunction {

	ListFactory listFactory;
	
	
	public GeneralFunction() {
		
	}
	
	public void setListFactory(ListFactory listFactory) {
		this.listFactory = listFactory;
	}
	
	public ListFactory getListFactory(){
		return listFactory;
	}
}
