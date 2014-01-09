package com.binar.core.dataManagement;

import com.binar.core.dataManagement.goodsManagement.GoodsManagementModel;
import com.binar.core.dataManagement.goodsManagement.GoodsManagementPresenter;
import com.binar.core.dataManagement.goodsManagement.GoodsManagementViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class GoodsManagement extends CssLayout {

	private GeneralFunction function;
	private GoodsManagementModel model;
	private GoodsManagementViewImpl view;
	private GoodsManagementPresenter presenter;
	
	public GoodsManagement(GeneralFunction function) {
		this.function=function;
		model=new GoodsManagementModel(function);
		view=new GoodsManagementViewImpl(function);
		
		presenter=new GoodsManagementPresenter(model, view, function);
		
		this.addComponent(view);
		this.setCaption("Manajemen Barang");
		this.setSizeFull();
		this.setStyleName("tab-content");
	}
	
	
	public GoodsManagementPresenter getPresenter(){
		return presenter;
	}
	public GoodsManagementViewImpl getView() {
		return view;
	}
}
