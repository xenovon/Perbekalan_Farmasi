package com.binar.core.setting.settingInsurance.inputEditInsurance;

import java.util.List;
import java.util.Map;

import com.binar.entity.Goods;
import com.binar.entity.Insurance;
import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;
import com.google.web.bindery.autobean.shared.AutoBeanFactory.Category;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public interface InputInsuranceView {

	interface InputInsuranceListener{
		public void buttonClick(String button);
		
	}
	public void init(); 
	public void construct();
	public void setListener(InputInsuranceListener listener);
	public void setFormData(Insurance data); //untuk mengeset data form
	public FormData getFormData(); //untuk mendapatkan data form
	public void setEditMode(boolean editMode); //mengubah mode edit
 	public void resetForm(); //mereset isi form
	public void showError(String content); //menampilkan error di tempat tertentu dengan konten tertentu
	public void hideError(); //menyembunyikan error tertentu
	
	
	
	//sub kelas untuk menampun data combobox
	
}
