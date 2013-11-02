package com.binar.view;

import com.binar.core.requirementPlanning.Approval;
import com.binar.core.requirementPlanning.InputRequirementPlanning;
import com.binar.core.requirementPlanning.ReqPlanningList;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.ListFactory;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class RequirementPlanningView extends CustomComponent implements View{

	TabSheet tabSheet;
	GeneralFunction generalFunction;
	
	@Override
	public void enter(ViewChangeEvent event) {
		//Inisiasi General function		
		generalFunction =new GeneralFunction();
		generalFunction.setListFactory(new ListFactory());
		
		Approval approval=new Approval();
		InputRequirementPlanning inputReqPl=new InputRequirementPlanning(generalFunction);
		ReqPlanningList reqPlanning=new ReqPlanningList(generalFunction);
		
		
		tabSheet=new TabSheet();
		tabSheet.addTab(reqPlanning).setCaption("Daftar Rencana Kebutuhan");
		tabSheet.addTab(inputReqPl).setCaption("Input Rencana Kebutuhan");;
		tabSheet.addTab(approval).setCaption("Persetujuan");
		
		
		tabSheet.setSizeFull();
		this.setCompositionRoot(tabSheet);
		
		
	}

}
