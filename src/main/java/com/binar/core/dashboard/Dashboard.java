package com.binar.core.dashboard;

import com.binar.core.dashboard.dashboardItem.farmationMinimumStockFastMoving.FarmationMinimumStockFastMovingModel;
import com.binar.core.dashboard.dashboardItem.farmationMinimumStockFastMoving.FarmationMinimumStockFastMovingPresenter;
import com.binar.core.dashboard.dashboardItem.farmationMinimumStockFastMoving.FarmationMinimumStockFastMovingViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class Dashboard extends GridLayout {
	 
	GeneralFunction function;
	public Dashboard(GeneralFunction function) {
		this.function=function;
		this.setSpacing(true);
		this.setColumns(2);
		this.setRows(5);
		this.setMargin(true);
		this.setWidth("100%");
		this.setHeight("450px");
		this.addComponent(new Label("<h2>Dashboard</h2>", ContentMode.HTML), 0,0,1,0);
		generateFarmationView();
	}
	
	FarmationMinimumStockFastMovingModel model;
	FarmationMinimumStockFastMovingPresenter presenter;
	FarmationMinimumStockFastMovingViewImpl view;
	
	public void generateFarmationView(){
		if(model == null){
			model=new FarmationMinimumStockFastMovingModel(function);
			view=new FarmationMinimumStockFastMovingViewImpl(function);
			presenter= new FarmationMinimumStockFastMovingPresenter(function ,view, model);
		}
		this.addComponent(view,0,1);
	}
}
