package com.binar.core.requirementPlanning.inputRequierementPlanning;

import com.binar.core.PresenterInterface;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Component;

public class InputRequirementPlanningPresenter implements PresenterInterface {

	InputRequirementPlanningViewImpl inputReq;
	GeneralFunction generalFunction;
	public InputRequirementPlanningPresenter(GeneralFunction function){
		this.generalFunction=function;
		inputReq=new InputRequirementPlanningViewImpl(function);
		inputReq.init();
	}
	
	public Component getViewComponent() {

		return inputReq;
	}

	
	
}
