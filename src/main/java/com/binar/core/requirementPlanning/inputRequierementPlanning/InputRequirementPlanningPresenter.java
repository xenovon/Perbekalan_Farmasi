package com.binar.core.requirementPlanning.inputRequierementPlanning;

import com.binar.core.PresenterInterface;
import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormModel;
import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormPresenter;
import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;

import elemental.events.MouseEvent.Button;

public class InputRequirementPlanningPresenter 
		implements PresenterInterface, InputRequirementPlanningView.InputRequirementListener {

	InputRequirementPlanningViewImpl view;
	InputRequirementPlanningModel model;
	
	//form input
	InputFormModel formModel;
	InputFormPresenter formPresenter;
	InputFormViewImpl formView;
	
	GeneralFunction generalFunction;
	
	public InputRequirementPlanningPresenter(InputRequirementPlanningViewImpl view, 
			InputRequirementPlanningModel model, GeneralFunction function ){
		this.view=view;
		this.model=model;
		view.init();
		view.addListener(this);
		this.generalFunction=function;
	}

	@Override
	public void buttonClick(String source) {

	}

	@Override
	public void buttonClick(String source, Object data) {
		if(source.equals("buttonInput")){
			if(formModel==null){
				formModel=new InputFormModel();
				formView=new InputFormViewImpl();
				formPresenter =new InputFormPresenter(formModel, formView,
						generalFunction);
			}
			System.out.println("Data = "+data.toString());
			view.displayForm(formView);
		}
	}

	

	
	
}
