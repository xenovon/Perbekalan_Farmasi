package com.binar.core.dataManagement.supplierManagement.inputEditSupplier;

import java.util.Collection;
import java.util.List;

import com.binar.core.dataManagement.supplierManagement.inputEditSupplier.InputSupplierView.ErrorLabel;
import com.binar.core.dataManagement.supplierManagement.inputEditSupplier.InputSupplierView.InputSupplierListener;
import com.binar.entity.Goods;
import com.binar.entity.Supplier;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class InputSupplierPresenter implements InputSupplierListener{

	private InputSupplierModel model;
	private InputSupplierViewImpl view;
	private GeneralFunction function;
	private int idSupplier; //untuk mode edit, dibutuhkan informasi id suplier yang sedang dirubah
	private boolean editMode;
	
	public InputSupplierPresenter(InputSupplierModel model,
			InputSupplierViewImpl view, GeneralFunction function,  boolean editMode) {
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
		}else if(inputFields.equals("inputAbbr")){
			validateAbbr(data);
		}

	}
	private void validateEmail(FormData data){
		view.hideError(ErrorLabel.EMAIL);
		String valid=data.validateEmail();
		if(valid!=null){
			view.showError(ErrorLabel.EMAIL, valid);
		}
	}
	private void validateAbbr(FormData data){
		view.hideError(ErrorLabel.ABBR);
		String valid=data.validateAbbr();
		if(valid!=null){
			view.showError(ErrorLabel.ABBR, valid);
		}	}
	
	private void saveData(){
		view.hideAllError();
		FormData data=view.getFormData();
		List<String> validate=data.validate();
		if(validate==null){
			String result=model.saveData(data);
			if(result==null){
				closeWindow();
				Notification.show("Penyimpanan data distributor berhasil");
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
			String result=model.saveEditData(data, idSupplier);
			if(result==null){
				closeWindow();
				Notification.show("Penyimpanan data distributor berhasil");
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
	public void setFormData(int idSupplier){
		this.idSupplier=idSupplier;
		Supplier supplier=model.getSingleSupplier(idSupplier);
		view.setFormData(supplier);
		validateAbbr(view.getFormData());
	}

}
