package com.binar.core.requirementPlanning;

import javax.swing.text.html.CSS;

import com.binar.core.requirementPlanning.inputRequierementPlanning.InputRequirementPlanningModel;
import com.binar.core.requirementPlanning.inputRequierementPlanning.InputRequirementPlanningPresenter;
import com.binar.core.requirementPlanning.inputRequierementPlanning.InputRequirementPlanningView;
import com.binar.core.requirementPlanning.inputRequierementPlanning.InputRequirementPlanningViewImpl;
import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormPresenter;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class InputRequirementPlanning extends CssLayout {

	GeneralFunction generalFunction;
	VerticalLayout layout=new VerticalLayout();
	Label label=new Label("Input Rencana Kebutuhan");
	InputRequirementPlanningPresenter inputPresenter;
	InputRequirementPlanningModel inputModel;
	InputRequirementPlanningViewImpl inputView;
	
	public InputRequirementPlanning(GeneralFunction function) {
		
		this.generalFunction=function;
		inputModel=new InputRequirementPlanningModel(generalFunction);
		inputView=new InputRequirementPlanningViewImpl(generalFunction);
		inputPresenter=new InputRequirementPlanningPresenter(inputView, inputModel, generalFunction);
				
		this.setCaption("Input Rencana Kebutuhan");
		this.addComponent(inputView);
		this.addStyleName("tab-content");
	}
}
