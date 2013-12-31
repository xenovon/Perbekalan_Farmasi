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
		public void setReceptionDate(Date receptionDate);
		void setPeriode(String periode, Window window);
	}
	
	public enum ErrorLabel{
		QUANTITY,GENERAL
	}

	public void init();
	public void resetForm();
	public void showError(ErrorLabel label, String content);
	public void hideError(ErrorLabel label);
	public void hideAllError();
	public void setInputEditView(boolean isEdit);
	public void setUnit(String text);
	public void setGoodsName(String text);
	public void setSelectInvoiceItemsData(Map<String, String> data);
//	public void setSelectGoodsData(Map<String, String> data);
	void addListener(InputReceptionPresenter listener);
}
