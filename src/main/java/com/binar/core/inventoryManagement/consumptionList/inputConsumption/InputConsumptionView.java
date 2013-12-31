package com.binar.core.inventoryManagement.consumptionList.inputConsumption;

import java.util.Date;
import java.util.Map;

import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormView.ErrorLabel;
import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormView.InputFormListener;
import com.vaadin.ui.Window;

public interface InputConsumptionView {
	
	interface InputConsumptionListener{
		void buttonClick(String source);
		void realTimeValidator(String inputField);
		public void setConsumptionDate(Date consumptionDate);
		void setPeriode(String periode, Window window);
	}
	
	public enum ErrorLabel{
		QUANTITY,GENERAL
	}

	public void init();
	public void resetForm();
	public void setSelectWardData(Map<String, String> data);
	public void showError(ErrorLabel label, String content);
	public void hideError(ErrorLabel label);
	public void hideAllError();
	public void setInputEditView(boolean input);
	//untuk mengeset label satuan barang
	public void setUnit(String text);
	
	public void setSelectGoodsData(Map<String, String> data);
	void addListener(InputConsumptionPresenter listener);
}
