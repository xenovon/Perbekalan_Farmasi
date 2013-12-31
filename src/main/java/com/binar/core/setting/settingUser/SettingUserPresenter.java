package com.binar.core.setting.settingUser;

import java.util.List;

import com.binar.core.setting.settingUser.SettingUserView.FormData;
import com.binar.core.setting.settingUser.SettingUserView.SettingUserListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Notification;

public class SettingUserPresenter implements SettingUserListener {

	SettingUserModel model;
	SettingUserViewImpl view;
	GeneralFunction function;
	
	public SettingUserPresenter(GeneralFunction function, SettingUserModel model, SettingUserViewImpl view) {
		this.model=model;
		this.view=view;
		this.function=function;
	
		view.setListener(this);
		view.init();
		view.setFormData(function.getLogin().getUserLogin());

	}
	
	@Override
	public void updateClick() {
		view.hideError();
		FormData data=view.getFormData();
		List<String> error=model.updateData(data, function.getLogin().getUserLogin().getIdUser(), view.isEditPassword());
		if(error==null){
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
	public void resetClick() {
		view.setFormData(function.getLogin().getUserLogin());
	}

	@Override
	public void valueChange() {
		view.hideError();
	}

	@Override
	public void modeClick() {
		view.changeMode();
	}

}
