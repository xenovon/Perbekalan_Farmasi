package com.binar.core.inventoryManagement.receptionList.inputReception;

import java.util.Date;
import java.util.Map;

import com.binar.core.inventoryManagement.receptionList.inputReception.InputReceptionPresenter;
import com.binar.core.inventoryManagement.receptionList.inputReception.InputReceptionView.ErrorLabel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public interface InputReceptionView {
	
	interface InputReceptionListener{
		void buttonClick(String source);
		void realTimeValidator(String inputField);
	}
	
	public enum ErrorLabel{
		QUANTITY,GENERAL, ERROR_DATE
	}

	public void init();
	public void resetForm();
	public void showError(ErrorLabel label, String content);
	public void hideAllError();
	public void setInputEditView(boolean isEdit);
	public void setUnit(String text);
	public void setSelectInvoiceItemsData(Map<Integer, String> data);
	public void setSelectGoodsData(Map<Integer, String> data);
	public FormReception getFormData();
	void addListener(InputReceptionPresenter listener);
}
