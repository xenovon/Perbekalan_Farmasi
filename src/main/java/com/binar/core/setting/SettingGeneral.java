package com.binar.core.setting;

import com.binar.core.setting.settingGeneral.SettingGeneralModel;
import com.binar.core.setting.settingGeneral.SettingGeneralPresenter;
import com.binar.core.setting.settingGeneral.SettingGeneralViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;

public class SettingGeneral extends  CssLayout {


	private GeneralFunction generalFunction;
	
	private SettingGeneralModel model;
	private SettingGeneralPresenter presenter;
	private SettingGeneralViewImpl view;
	
	public SettingGeneral(GeneralFunction function) {
		generalFunction=function;
		
		model = new SettingGeneralModel(generalFunction);
		view =new SettingGeneralViewImpl(generalFunction);
		presenter = new  SettingGeneralPresenter(generalFunction, model, view);
		
		this.addComponent(view);
		this.setCaption("Pengaturan Umum");
		this.setSizeFull();
		this.addStyleName("tab-content");
	}
}
