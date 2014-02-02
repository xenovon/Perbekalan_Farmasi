package com.binar.view;

import com.binar.core.dashboard.Dashboard;
import com.binar.core.dataManagement.GoodsManagement;
import com.binar.core.dataManagement.ManufacturerManagement;
import com.binar.core.dataManagement.SupplierManagement;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

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
		
		Navigator navigator=UI.getCurrent().getNavigator();
		String parameter=event.getParameters();
		if(parameter.equals("a")){
			dashboard.generateFarmationView();
		}else if(parameter.equals("b")){
			dashboard.generateIfrsView();
		}else if(parameter.equals("c")){
			dashboard.generatePPKView();
		}else if(parameter.equals("d")){
			dashboard.generateProcurementView();
		}else if(parameter.equals("e")){
			dashboard.generateSupportView();
		}

	}

}
