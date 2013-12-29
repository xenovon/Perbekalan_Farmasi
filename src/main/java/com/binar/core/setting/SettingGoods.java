package com.binar.core.setting;

import com.binar.core.setting.settingGoods.SettingGoodsModel;
import com.binar.core.setting.settingGoods.SettingGoodsPresenter;
import com.binar.core.setting.settingGoods.SettingGoodsViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;

public class SettingGoods extends  CssLayout {


	private GeneralFunction generalFunction;
	
	private SettingGoodsModel model;
	private SettingGoodsPresenter presenter;
	private SettingGoodsViewImpl view;
	
	public SettingGoods(GeneralFunction function) {
		generalFunction=function;
		
		model = new SettingGoodsModel(generalFunction);
		view =new SettingGoodsViewImpl(generalFunction);
		presenter = new  SettingGoodsPresenter(generalFunction, model, view);
		
		this.addComponent(view);
		this.setCaption("Pengaturan Barang");
		this.setSizeFull();
		this.addStyleName("tab-content");
	}
}
