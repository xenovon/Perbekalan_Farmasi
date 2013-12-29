package com.binar.core.setting;

import com.binar.core.setting.settingPurchaseOrder.SettingPurchaseOrderModel;
import com.binar.core.setting.settingPurchaseOrder.SettingPurchaseOrderPresenter;
import com.binar.core.setting.settingPurchaseOrder.SettingPurchaseOrderViewImpl;
import com.binar.core.setting.settingUser.SettingUserModel;
import com.binar.core.setting.settingUser.SettingUserPresenter;
import com.binar.core.setting.settingUser.SettingUserViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;

public class UserSetting extends CssLayout{
	private GeneralFunction generalFunction;
	
	private SettingUserModel model;
	private SettingUserPresenter presenter;
	private SettingUserViewImpl view;
	
	public UserSetting(GeneralFunction function) {
		generalFunction=function;
		
		model = new SettingUserModel(generalFunction);
		view =new SettingUserViewImpl(function);
		presenter = new  SettingUserPresenter(generalFunction, model, view);
		
		this.addComponent(view);
		this.setCaption("Pengaturan Akun");
		this.setSizeFull();
		this.addStyleName("tab-content");
	}

}
