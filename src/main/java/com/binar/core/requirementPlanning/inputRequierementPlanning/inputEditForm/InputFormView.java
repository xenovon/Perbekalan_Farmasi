package com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm;

import java.util.Map;

import com.vaadin.ui.FormLayout;

public interface InputFormView {

	interface InputFormListener{
		void buttonClick(String source);
	}
	
	public void init();
	public void resetForm();
	public void setSelectSupplierData(Map<String, String> data);
	public void setSelectManufacturerData(Map<String, String> data);
	
	
	public void addListener(InputFormListener listener);
	public void setSelectGoodsData(Map<String, String> data);
}
