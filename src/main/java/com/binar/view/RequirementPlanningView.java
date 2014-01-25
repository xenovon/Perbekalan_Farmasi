package com.binar.view;

import com.binar.core.requirementPlanning.Approval;
import com.binar.core.requirementPlanning.Forecast;
import com.binar.core.requirementPlanning.InputRequirementPlanning;
import com.binar.core.requirementPlanning.ReqPlanningList;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.ListFactory;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
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
	
	@Override
	public void enter(ViewChangeEvent event) {
		//Inisiasi General function		
		generalFunction =new GeneralFunction();
		
		if(approval==null){
			approval=new Approval(generalFunction);
		}
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
		tabSheet.addTab(approval).setCaption("Persetujuan");
		tabSheet.addTab(forecast).setCaption("Peramalan");

		tabSheet.addSelectedTabChangeListener(this);
		tabSheet.setSizeFull();
		this.setCompositionRoot(tabSheet);
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

}
