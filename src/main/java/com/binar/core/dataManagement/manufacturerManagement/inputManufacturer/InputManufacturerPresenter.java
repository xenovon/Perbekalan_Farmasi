package com.binar.core.dataManagement.manufacturerManagement.inputManufacturer;

import java.util.Collection;
import java.util.List;

import com.binar.core.dataManagement.manufacturerManagement.inputManufacturer.InputManufacturerView.ErrorLabel;
import com.binar.core.dataManagement.manufacturerManagement.inputManufacturer.InputManufacturerView.InputManufacturerListener;
import com.binar.entity.Goods;
import com.binar.entity.Manufacturer;
import com.binar.entity.Supplier;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class InputManufacturerPresenter implements InputManufacturerListener{

	private InputManufacturerModel model;
	private InputManufacturerViewImpl view;
	private GeneralFunction function;
	private int idManufacturer; //untuk mode edit, dibutuhkan informasi id manufacturer yang sedang dirubah
	private boolean editMode;
	
	public InputManufacturerPresenter(InputManufacturerModel model,
			InputManufacturerViewImpl view, GeneralFunction function,  boolean editMode) {
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
			view.hideAllError();
		}
	}

	@Override
	public void realTimeValidator(String inputFields) {
		FormData data=view.getFormData();
		if(inputFields.equals("inputEmail")){
			validateEmail(data);
		}

	}
	private void validateEmail(FormData data){
		view.hideError(ErrorLabel.EMAIL);
		String valid=data.validateEmail();
		if(valid!=null){
			view.showError(ErrorLabel.EMAIL, valid);
		}
	}	
	private void saveData(){
		view.hideAllError();
		FormData data=view.getFormData();
		List<String> validate=data.validate();
		if(validate==null){
			String result=model.saveData(data);
			if(result==null){
				closeWindow();
				Notification.show("Penyimpanan produsen berhasil");
			}else{
				view.showError(ErrorLabel.GENERAL, result);
			}
			
		}else{
			String error="<b>Tidak bisa menyimpan data, perbaiki kesalahan berikut :</b><br> <br>";
			for(String err:validate){
				error=error+err+"<br>";
			}
			view.showError(ErrorLabel.GENERAL, error);
		}
	}
	
	private void editData(){
		view.hideAllError();
		FormData data=view.getFormData();
		List<String> validate=data.validate();
		if(validate==null){
			String result=model.saveEditData(data, idManufacturer);
			if(result==null){
				closeWindow();
				Notification.show("Penyimpanan produsen berhasil");
			}else{
				view.showError(ErrorLabel.GENERAL, result);
			}
			
		}else{
			String error="<b>Tidak bisa menyimpan data, perbaiki kesalahan berikut :</b><br> <br>";
			for(String err:validate){
				error=error+validate+"<br>";
			}
			view.showError(ErrorLabel.GENERAL, error);
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
	public void setFormData(int idManufacturer){
		this.idManufacturer=idManufacturer;
		Manufacturer manufacturer=model.getSingleManufacturer(idManufacturer);
		view.setFormData(manufacturer);
	}

}
