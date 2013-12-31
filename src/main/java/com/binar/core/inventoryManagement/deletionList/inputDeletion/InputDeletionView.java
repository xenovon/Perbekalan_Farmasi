package com.binar.core.inventoryManagement.deletionList.inputDeletion;

import java.util.Date;
import java.util.Map;

import com.vaadin.ui.Window;

public interface InputDeletionView {
	
	interface InputDeletionListener {
		void buttonClick(String source);
		void realTimeValidator(String inputField);
		public void setDeletionDate(Date deletionDate);
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
	public void setInputEditView(boolean input);
	public void setUnit(String text);
	public void setSelectGoodsData(Map<String, String> data);
	void addListener(InputDeletionPresenter listener);

}
