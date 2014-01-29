package com.binar.view;

import com.binar.core.requirementPlanning.Approval;
import com.binar.core.requirementPlanning.Forecast;
import com.binar.core.requirementPlanning.InputRequirementPlanning;
import com.binar.core.requirementPlanning.ReqPlanningList;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.ListFactory;
import com.binar.generalFunction.LoginManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.VerticalLayout;

public class RequirementPlanningView extends CustomComponent implements View, SelectedTabChangeListener{

	TabSheet tabSheet;
	GeneralFunction generalFunction;
	InputRequirementPlanning inputReqPl;
	Approval approval;
	ReqPlanningList reqPlanning;
	Forecast forecast;
	LoginManager  loginManager;
	
	@Override
	public void enter(ViewChangeEvent event) {
		generalFunction =new GeneralFunction();
		this.loginManager=generalFunction.getLogin();
		String userLoginRole=loginManager.getRoleId();
		
		/*
		 * Manajemen Role untuk Level VIEW
		 * 
		 */
		if(loginManager.getRoleId().equals(loginManager.FRM)){
			generateFarmationView(event);
		}else if(loginManager.getRoleId().equals(loginManager.IFRS)){
			generateIFRSView(event);
		}else if(loginManager.getRoleId().equals(loginManager.PNJ)){
			generateSupportView(event);
		}else if(loginManager.getRoleId().equals(loginManager.PPK)){
			generatePPKView(event);
		}else if(loginManager.getRoleId().equals(loginManager.TPN)){
			generateProcurementView(event);
		}else if(loginManager.getRoleId().equals(loginManager.ADM)){
			generateAdminView(event);
		}
		
	}

	//untuk meload ulang (refresh) data tabel di tab ketika tab diakses
	@Override
	public void selectedTabChange(SelectedTabChangeEvent event) {
		if(event.getTabSheet().getSelectedTab()==reqPlanning){
			reqPlanning.getPresenter().updateTable(reqPlanning.getView().getSelectedPeriod());
		}
		if(event.getTabSheet().getSelectedTab()==inputReqPl){
			inputReqPl.getPresenter().updateTable(inputReqPl.getView().getPeriodeValue());
		}
		if(event.getTabSheet().getSelectedTab()==approval){
			approval.getPresenter().updateTable(approval.getView().getPeriodeValue());
		}
	}
	private void generateFarmationView(ViewChangeEvent event){
		//Inisiasi General function		
		
		if(inputReqPl==null){
			inputReqPl=new InputRequirementPlanning(generalFunction);
		}
		if(reqPlanning==null){
			reqPlanning=new ReqPlanningList(generalFunction);			
		}
		if(forecast==null){
			forecast=new Forecast(generalFunction);
		}
		
		tabSheet=new TabSheet();
		tabSheet.addTab(reqPlanning).setCaption("Daftar Rencana Kebutuhan");
		tabSheet.addTab(inputReqPl).setCaption("Input Rencana Kebutuhan");;
		tabSheet.addTab(forecast).setCaption("Peramalan");

		tabSheet.addSelectedTabChangeListener(this);
		tabSheet.setSizeFull();
		
		Navigator navigator=UI.getCurrent().getNavigator();
		String parameter=event.getParameters();
		if(parameter.equals(generalFunction.VIEW_REQ_PLANNING_LIST)){
			tabSheet.setSelectedTab(reqPlanning);
		}else if(parameter.equals(generalFunction.VIEW_REQ_PLANNING_INPUT)){
			tabSheet.setSelectedTab(inputReqPl);
		}
		this.setCompositionRoot(tabSheet);
		
	}
	private void generateIFRSView(ViewChangeEvent event){
		//Inisiasi General function		
		
		if(approval==null){
			approval=new Approval(generalFunction);
		}
		if(reqPlanning==null){
			reqPlanning=new ReqPlanningList(generalFunction);			
		}
		
		tabSheet=new TabSheet();
		tabSheet.addTab(reqPlanning).setCaption("Daftar Rencana Kebutuhan");
		tabSheet.addTab(approval).setCaption("Persetujuan");

		tabSheet.addSelectedTabChangeListener(this);
		tabSheet.setSizeFull();
		
		Navigator navigator=UI.getCurrent().getNavigator();
		String parameter=event.getParameters();
		if(parameter.equals(generalFunction.VIEW_REQ_PLANNING_APPROVAL)){
			tabSheet.setSelectedTab(approval);
		}else if(parameter.equals(generalFunction.VIEW_REQ_PLANNING_LIST)){
			tabSheet.setSelectedTab(reqPlanning);
		}
		this.setCompositionRoot(tabSheet);
		
	}
	
	private  void generateSupportView(ViewChangeEvent event){
		generateIFRSView(event);
	}

	private  void generatePPKView(ViewChangeEvent event){
		generateIFRSView(event);
	}

	private void generateProcurementView(ViewChangeEvent event){
		//Inisiasi General function		
		
		if(reqPlanning==null){
			reqPlanning=new ReqPlanningList(generalFunction);			
		}

		
		tabSheet=new TabSheet();
		tabSheet.addTab(reqPlanning).setCaption("Daftar Rencana Kebutuhan");

		tabSheet.addSelectedTabChangeListener(this);
		tabSheet.setSizeFull();
		
		Navigator navigator=UI.getCurrent().getNavigator();
		String parameter=event.getParameters();
		if(parameter.equals(generalFunction.VIEW_REQ_PLANNING_LIST)){
			tabSheet.setSelectedTab(reqPlanning);
		}
		this.setCompositionRoot(tabSheet);
		
	}
	private void generateAdminView(ViewChangeEvent event){
		this.setCompositionRoot(new Label("Error 404: Halaman tidak ditemukan"));
	}
	
}
