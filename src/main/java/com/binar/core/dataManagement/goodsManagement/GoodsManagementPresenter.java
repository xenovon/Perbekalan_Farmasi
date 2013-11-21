package com.binar.core.dataManagement.goodsManagement;

import java.util.List;

import com.binar.core.dataManagement.goodsManagement.GoodsManagementView.GoodsManagementListener;
import com.binar.entity.Goods;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Notification;

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
		view.setListener(this);
		updateTable();
	}

	@Override
	public void buttonClick(String buttonName) {
		if(buttonName.equals("buttonInput")){
			Notification.show("Tampilkan Input Barang");
		}
	}

	@Override
	public void editClick(String idGoods) {
		Notification.show(idGoods);
	}

	@Override
	public void deleteClick(String idGoods) {
		Notification.show(idGoods);
	}

	@Override
	public void showClick(String idGoods) {
		view.showDetailWindow(model.getSingleGoods(idGoods));
	}
	public void updateTable(){
		List<Goods> goods=model.getGoods();
		view.updateTableData(goods);
	}
}
