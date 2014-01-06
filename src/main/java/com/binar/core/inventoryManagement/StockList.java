package com.binar.core.inventoryManagement;

import com.binar.core.inventoryManagement.stockList.StockListModel;
import com.binar.core.inventoryManagement.stockList.StockListPresenter;
import com.binar.core.inventoryManagement.stockList.StockListViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class StockList extends CssLayout {
	GeneralFunction generalFunction;
	
	StockListModel model;
	StockListPresenter presenter;
	StockListViewImpl view;
	
	Label label=new Label("Stok Barang");
	
	public StockList(GeneralFunction function) {
		this.generalFunction=function;
		model=new StockListModel(generalFunction);
		view = new StockListViewImpl(generalFunction);
		presenter = new StockListPresenter(model, view, generalFunction);
		
		this.setCaption("Stok Barang");
		this.addStyleName("tab-content");
		this.addComponent(view);
	}
}
