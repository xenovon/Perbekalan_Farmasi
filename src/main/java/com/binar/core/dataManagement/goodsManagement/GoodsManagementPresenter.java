package com.binar.core.dataManagement.goodsManagement;

import com.binar.core.dataManagement.goodsManagement.GoodsManagementView.GoodsManagementListener;
import com.binar.generalFunction.GeneralFunction;

public class GoodsManagementPresenter implements GoodsManagementListener{

	GoodsManagementModel model;
	GoodsManagementViewImpl view;
	GeneralFunction function;
	
	public GoodsManagementPresenter(GoodsManagementModel model, 
									GoodsManagementViewImpl view, GeneralFunction function) {
		this.model=model;
		this.view=view;
		this.function=function;
		view.init();
	}
}
