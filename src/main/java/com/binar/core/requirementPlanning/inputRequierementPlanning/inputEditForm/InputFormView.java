package com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm;

import java.util.Map;

import com.vaadin.ui.FormLayout;

public interface InputFormView {

	interface InputFormListener{
		void buttonClick(String source);
		void realtimeValidator(String inputField);
		public void setPeriode(String periode);
	}
	public enum ErrorLabel{
		QUANTITY,SUPPLIER,GENERAL
	}
	public void init();
	public void resetForm();
	public void setSelectSupplierData(Map<String, String> data);
	public void setSelectManufacturerData(Map<String, String> data);
	public void showError(ErrorLabel label, String content);
	public void hideError(ErrorLabel label);
	public void hideAllError();
	//untuk mengeset label satuan barang
	public void setUnit(String text);
	
	public void addListener(InputFormListener listener);
	public void setSelectGoodsData(Map<String, String> data);
}
