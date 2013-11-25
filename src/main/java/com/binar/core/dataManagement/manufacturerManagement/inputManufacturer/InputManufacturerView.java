package com.binar.core.dataManagement.manufacturerManagement.inputManufacturer;

import java.util.List;
import java.util.Map;

import com.binar.entity.Goods;
import com.binar.entity.Manufacturer;
import com.binar.entity.Supplier;
import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public interface InputManufacturerView {

	public enum ErrorLabel{
		EMAIL,  GENERAL
	}
	interface InputManufacturerListener{
		public void buttonClick(String button);
		public void realTimeValidator(String inputFields);
		

	}
	public void init(); 
	public void construct();
	public void setListener(InputManufacturerListener listener);
	public FormData getFormData(); //untuk mendapatkan data form
	public void setEditMode(boolean editMode); //mengubah mode edit
 	public void resetForm(); //mereset isi form
	public void showError(ErrorLabel label, String content); //menampilkan error di tempat tertentu dengan konten tertentu
	public void hideError(ErrorLabel label); //menyembunyikan error tertentu
	public void hideAllError(); //menyembunyikan semua error
	public void setFormData(Manufacturer data);
	
	
	
	 
	
}
