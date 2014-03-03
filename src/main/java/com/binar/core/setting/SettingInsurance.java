package com.binar.core.setting;

import com.binar.core.setting.settingGoods.SettingGoodsModel;
import com.binar.core.setting.settingGoods.SettingGoodsPresenter;
import com.binar.core.setting.settingGoods.SettingGoodsViewImpl;
import com.binar.core.setting.settingInsurance.InsuranceManagementModel;
import com.binar.core.setting.settingInsurance.InsuranceManagementPresenter;
import com.binar.core.setting.settingInsurance.InsuranceManagementViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;

public class SettingInsurance extends CssLayout {

	private GeneralFunction generalFunction;
	
	private InsuranceManagementModel model;
	private InsuranceManagementPresenter presenter;
	private InsuranceManagementViewImpl view;
	
	public SettingInsurance(GeneralFunction function) {
		generalFunction=function;
		
		model = new InsuranceManagementModel(generalFunction);
		view =new InsuranceManagementViewImpl(generalFunction);
		presenter = new  InsuranceManagementPresenter( model, view, generalFunction);
		
		this.addComponent(view);
		this.setCaption("Pengaturan Asuransi");
		this.setSizeFull();
		this.addStyleName("tab-content");
	}

}
