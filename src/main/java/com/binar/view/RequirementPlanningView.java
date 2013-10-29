package com.binar.view;

import com.binar.core.requirementPlanning.Approval;
import com.binar.core.requirementPlanning.InputRequirementPlanning;
import com.binar.core.requirementPlanning.ReqPlanningList;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;

public class RequirementPlanningView extends CustomComponent implements View{

	TabSheet tabSheet;
	
	@Override
	public void enter(ViewChangeEvent event) {
		Approval approval=new Approval();
		InputRequirementPlanning inputReqPl=new InputRequirementPlanning();
		ReqPlanningList reqPlanning=new ReqPlanningList();
		
		
		tabSheet=new TabSheet(reqPlanning, inputReqPl, approval);
		tabSheet.addStyleName("borderless");
		tabSheet.setSizeFull();
		this.setCompositionRoot(tabSheet);
		
		
	}

}
