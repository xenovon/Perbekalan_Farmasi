package com.binar.core.setting;

import com.binar.core.setting.settingPurchaseOrder.SettingPurchaseOrderModel;
import com.binar.core.setting.settingPurchaseOrder.SettingPurchaseOrderPresenter;
import com.binar.core.setting.settingPurchaseOrder.SettingPurchaseOrderViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;

public class SettingPurchaseOrder extends  CssLayout {


	private GeneralFunction generalFunction;
	
	private SettingPurchaseOrderModel model;
	private SettingPurchaseOrderPresenter presenter;
	private SettingPurchaseOrderViewImpl view;
	
	public SettingPurchaseOrder(GeneralFunction function) {
		generalFunction=function;
		
		model = new SettingPurchaseOrderModel(generalFunction);
		view =new SettingPurchaseOrderViewImpl(generalFunction);
		presenter = new  SettingPurchaseOrderPresenter(generalFunction, model, view);
		
		this.addComponent(view);
		this.setCaption("Pengaturan Surat Pesanan");
		this.setSizeFull();
		this.addStyleName("tab-content");
	}

}
