package com.binar.core.setting.settingInsurance.inputEditInsurance;

import java.util.Collection;
import java.util.List;

import com.binar.core.setting.settingInsurance.inputEditInsurance.InputInsuranceView.InputInsuranceListener;
import com.binar.entity.Goods;
import com.binar.entity.Insurance;
import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class InputInsurancePresenter implements InputInsuranceListener{

	private InputInsuranceModel model;
	private InputInsuranceViewImpl view;
	private GeneralFunction function;
	private boolean editMode;
	
	public InputInsurancePresenter(InputInsuranceModel model,
			InputInsuranceViewImpl view, GeneralFunction function,  boolean editMode) {
		this.model=model;
		this.view=view;
		this.function=function;
	
		view.setListener(this);
		view.init();
		view.setEditMode(editMode);
		this.editMode=editMode;
	}

	@Override
	public void buttonClick(String button) {
		if(button.equals("buttonSubmit")){
			saveData();
		}else if(button.equals("buttonCancel")){
			buttonCancel();
		}else if(button.equals("buttonSaveEdit")){
			editData();
		}else if(button.equals("buttonReset")){
			view.resetForm();
			view.hideError();
		}
	}

	private void saveData(){
		view.hideError();
		FormData data=view.getFormData();
		String result=model.saveData(data);
		if(result==null){
			closeWindow();
			Notification.show("Penyimpanan data asuransi berhasil");
		}else{
			view.showError(result);
		}
	}
	
	private void editData(){
		view.hideError();
		FormData data=view.getFormData();
		String result=model.saveEditData(data);
		if(result==null){
			closeWindow();
			Notification.show("Penyimpanan data asuransi berhasil");
		}else{
			view.showError(result);
		}
			
	}
	private void buttonCancel(){
		function.showDialog("Batalkan", 
				editMode?"Anda yakin akan membatalkan perubahan data?":"Anda yakin Akan Membatalkan Memasukan Data?",
				new ClickListener() {
					public void buttonClick(ClickEvent event) {
						closeWindow();
					}
				}, view.getUI());
		
	}
	public void closeWindow(){
		Collection<Window> list=view.getUI().getWindows();
		for(Window w:list){
			view.getUI().removeWindow(w);
			view.resetForm();
		}
		
	}
	//set data untuk edit
	public void setFormData(int idInsurance){
		Insurance insurance=model.getSingleInsurance(idInsurance);
		view.setFormData(insurance);
	}
}
