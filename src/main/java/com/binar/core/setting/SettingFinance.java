package com.binar.core.setting;

import com.binar.core.requirementPlanning.approval.ApprovalModel;
import com.binar.core.requirementPlanning.approval.ApprovalPresenter;
import com.binar.core.requirementPlanning.approval.ApprovalViewImpl;
import com.binar.core.setting.settingFinance.SettingFinanceModel;
import com.binar.core.setting.settingFinance.SettingFinancePresenter;
import com.binar.core.setting.settingFinance.SettingFinanceViewImpl;
import com.binar.core.setting.settingGeneral.SettingGeneralView;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;

public class SettingFinance extends CssLayout {

	
	
	private GeneralFunction generalFunction;
	
	private SettingFinanceModel model;
	private SettingFinancePresenter presenter;
	private SettingFinanceViewImpl view;
	
	public SettingFinance(GeneralFunction function) {
		generalFunction=function;
		
		model = new SettingFinanceModel(generalFunction);
		view =new SettingFinanceViewImpl(generalFunction);
		presenter = new  SettingFinancePresenter(generalFunction, model, view);
		
		this.addComponent(view);
		this.setCaption("Pengaturan Keuangan");
		this.setSizeFull();
		this.addStyleName("tab-content");
	}


}
