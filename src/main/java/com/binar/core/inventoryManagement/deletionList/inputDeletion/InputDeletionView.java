package com.binar.core.inventoryManagement.deletionList.inputDeletion;

import java.util.Date;
import java.util.Map;

import com.binar.entity.DeletedGoods;
import com.vaadin.ui.Window;

public interface InputDeletionView {
	
	interface InputDeletionListener {
		public void buttonUpdate();
		public void buttonSave();
		public void quantityChange();
		public void goodsSelectChange();
		public void buttonCancel();
		public void buttonReset();
	}
	
	public enum ErrorLabel{
		QUANTITY,GENERAL, PRICE
	}

	public void init();
	public void resetForm();
	public void showError(ErrorLabel label, String content);
	public void hideError(ErrorLabel label);
	public void hideAllError();
	public void setInputEditView(boolean input);
	public void setUnit(String text);
	public void setSelectGoodsData(Map<String, String> data);
	public FormDeletion getFormData();
	public void setListener(InputDeletionListener listener);
	public void setFormData(DeletedGoods deletion);
}
