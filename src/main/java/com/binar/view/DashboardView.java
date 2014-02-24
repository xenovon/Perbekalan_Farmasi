package com.binar.view;

import com.binar.core.dashboard.Dashboard;
import com.binar.core.dataManagement.GoodsManagement;
import com.binar.core.dataManagement.ManufacturerManagement;
import com.binar.core.dataManagement.SupplierManagement;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.LoginManager;
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
	LoginManager loginManager;

	@Override
	public void enter(ViewChangeEvent event) {
		init(event);
	}
	
	public void init(ViewChangeEvent event){
		GeneralFunction function=new GeneralFunction();
		loginManager=function.getLogin();
		
		if(dashboard==null){
			dashboard=new Dashboard(function);
		}
		dashboard.generateFarmationView();
		System.out.println("Set composition root");
		setCompositionRoot(dashboard);
		
		if (event!=null) {
			String parameter = event.getParameters();
			//Untuk Cheat mengakses dashboard, hapus ketika aplikasi sudah selesai
			if (parameter.equals("a")) {
				dashboard.generateFarmationView();
			} else if (parameter.equals("b")) {
				dashboard.generateIfrsView();
			} else if (parameter.equals("c")) {
				dashboard.generatePPKView();
			} else if (parameter.equals("d")) {
				dashboard.generateProcurementView();
			} else if (parameter.equals("e")) {
				dashboard.generateSupportView();
			}
		}
		/*
		 * Manajemen Role untuk Level VIEW
		 * 
		 */
		if(loginManager.getRoleId().equals(loginManager.FRM)){
			dashboard.generateFarmationView();
		}else if(loginManager.getRoleId().equals(loginManager.IFRS)){
			dashboard.generateIfrsView();
		}else if(loginManager.getRoleId().equals(loginManager.PNJ)){
			dashboard.generateSupportView();
		}else if(loginManager.getRoleId().equals(loginManager.PPK)){
			dashboard.generatePPKView();
		}else if(loginManager.getRoleId().equals(loginManager.TPN)){
			dashboard.generateProcurementView();
		}
	}

}
