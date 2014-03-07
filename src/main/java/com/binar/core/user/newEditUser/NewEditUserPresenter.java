package com.binar.core.user.newEditUser;

import java.util.Collection;
import java.util.List;

import com.binar.core.user.newEditUser.NewEditUserView.FormData;
import com.binar.core.user.newEditUser.NewEditUserView.NewEditUserListener;
import com.binar.entity.User;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.client.ui.VNotification.HideEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Window;

public class NewEditUserPresenter implements NewEditUserListener {

	NewEditUserModel model;
	NewEditUserViewImpl view;
	GeneralFunction function;
	boolean editMode;
	
	User user;
	
	public NewEditUserPresenter(NewEditUserModel model, NewEditUserViewImpl view, GeneralFunction function, boolean editMode) {
		this.model=model;
		this.view=view;
		this.function=function;
	
		view.setListener(this);
		view.init();
		view.setComboBoxData(model.getComboData());
		this.editMode=editMode;
		view.setEditMode(editMode);
	}	
	
	public void setFormData(int idUser){
		this.user=model.getUser(idUser);
		view.setFormData(this.user);
	}
	@Override
	public void updateClick() {
		view.hideError();
		FormData data=view.getFormData();
		List<String> error=model.updateData(data, user);
		if(error==null){
			closeWindow();
			Notification.show("Data telah diperbaharui");
		}else{
			String errors="<b>Tidak bisa menyimpan data, perbaiki kesalahan berikut :</b><br> <br>";
			for(String err:error){
				errors=errors+err+"<br>";
			}
			view.showError(errors);
			
		}
	}
	@Override
	public void activationClick() {
		String error=model.activateUser(user.getIdUser());
		if(error==null){
			User users=model.getUser(user.getIdUser());
			view.changeButtonActivation(users.isActive());
		}else{
			Notification.show("Gagal mengaktivasi");			
		}
	}

	@Override
	public void resetPasswordClick() {
		String error=model.resetPassword(user.getIdUser());
		if(error.length()==8){
			User users=model.getUser(user.getIdUser());
			view.showError("Sukses memperbaharui password,</br> Catat Password berikut :  </br><b>"+error+"</b></br>");
			Notification.show("Sukses memperbaharui password,Catat Password berikut : "+error, Type.ERROR_MESSAGE);
		}else{
			Notification.show("Gagal mereset password : "+error);			
		}
		
	}

	@Override
	public void resetClick() {
		view.resetForm();
	}

	@Override
	public void cancelClick() {
		function.showDialog("Batalkan", 
				editMode?"Anda yakin akan membatalkan perubahan data?":"Anda yakin Akan Membatalkan Memasukan Data?",
				new ClickListener() {
					public void buttonClick(ClickEvent event) {
						closeWindow();
					}
				}, view.getUI());
		
	}
	@Override
	public void saveClick() {
		System.out.println("Save click");
		view.hideError();
		FormData data=view.getFormData();
		List<String> error=model.saveData(data);
		if(error==null){
			closeWindow();
			Notification.show("Data telah disimpan");
		}else{
			String errors="<b>Tidak bisa menyimpan data, perbaiki kesalahan berikut :</b><br> <br>";
			for(String err:error){
				errors=errors+err+"<br>";
			}
			view.showError(errors);
			
		}
		
	}
	@Override
	public void valueChange() {
		view.hideError();
	}
	public void closeWindow(){
		Collection<Window> list=view.getUI().getWindows();
		for(Window w:list){
			view.getUI().removeWindow(w);
			view.resetForm();
		}
		
	}

}
