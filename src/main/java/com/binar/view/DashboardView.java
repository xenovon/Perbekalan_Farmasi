package com.binar.view;

import com.binar.core.dashboard.Dashboard;
import com.binar.core.dataManagement.GoodsManagement;
import com.binar.core.dataManagement.ManufacturerManagement;
import com.binar.core.dataManagement.SupplierManagement;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class DashboardView extends CustomComponent implements View{

	Label label=new Label("Dashboard");
	GeneralFunction function;
	Dashboard dashboard;

	@Override
	public void enter(ViewChangeEvent event) {
		GeneralFunction function=new GeneralFunction();
		if(dashboard==null){
			dashboard=new Dashboard(function);
		}
		dashboard.generateFarmationView();
		System.out.println("Set composition root");
		setCompositionRoot(dashboard);
		
	}

}
