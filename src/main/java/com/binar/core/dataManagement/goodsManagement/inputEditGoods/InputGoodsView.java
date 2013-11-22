package com.binar.core.dataManagement.goodsManagement.inputEditGoods;

import java.util.List;
import java.util.Map;

import com.binar.entity.Goods;
import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;
import com.google.web.bindery.autobean.shared.AutoBeanFactory.Category;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public interface InputGoodsView {

	public enum ErrorLabel{
		ID, MINIMUM_STOCK, INITIAL_STOCK, HET, GENERAL
	}
	interface InputGoodsListener{
		public void buttonClick(String button);
		public void realTimeValidator(String inputFields);
		
		//untuk menentukan apakah initial stock bisa diubah atau ngga
		//implementasi lebih detail ada di model
		public boolean isCanEditInitialStock();

	}
	public void init(); 
	public void construct();
	public void setFormData(Goods data); //untuk mengeset data form
	public FormData getFormData(); //untuk mendapatkan data form
	public void setEditMode(boolean editMode); //mengubah mode edit
 	public void resetForm(); //mereset isi form
	public void showError(ErrorLabel label, String content); //menampilkan error di tempat tertentu dengan konten tertentu
	public void hideError(ErrorLabel label); //menyembunyikan error tertentu
	public void hideAllError(); //menyembunyikan semua error
	public void setComboBoxData(ComboDataList list);
	
	
	//sub kelas untuk menampun data combobox
	public class ComboDataList{
		private Map<String, String> insuranceList;
		private Map<String, String> inputUnitList;
		private Map<EnumGoodsType, String> inputTypeList;
		private Map<String, String> inputPackageList;
		private Map<EnumGoodsCategory, String> inputCategoryList;
		private Map<Boolean, String> inputImportantList;
		public Map<String, String> getInsuranceList() {
			return insuranceList;
		}
		public void setInsuranceList(Map<String, String> insuranceList) {
			this.insuranceList = insuranceList;
		}
		public Map<String, String> getInputUnitList() {
			return inputUnitList;
		}
		public void setInputUnitList(Map<String, String> inputUnitList) {
			this.inputUnitList = inputUnitList;
		}
		public Map<String, String> getInputPackageList() {
			return inputPackageList;
		}
		public void setInputPackageList(Map<String, String> inputPackageList) {
			this.inputPackageList = inputPackageList;
		}
		public Map<Boolean, String> getInputImportantList() {
			return inputImportantList;
		}
		public void setInputImportantList(Map<Boolean, String> inputImportantList) {
			this.inputImportantList = inputImportantList;
		}
		public Map<EnumGoodsType, String> getInputTypeList() {
			return inputTypeList;
		}
		public void setInputTypeList(Map<EnumGoodsType, String> inputTypeList) {
			this.inputTypeList = inputTypeList;
		}
		public Map<EnumGoodsCategory, String> getInputCategoryList() {
			return inputCategoryList;
		}
		public void setInputCategoryList(
				Map<EnumGoodsCategory, String> inputCategoryList) {
			this.inputCategoryList = inputCategoryList;
		}
		
		
		
	}
	
	 
	
}
