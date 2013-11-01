package com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm;

import com.binar.core.PresenterInterface;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;

public class InputFormPresenter implements PresenterInterface, InputFormView.InputFormListener{

	GeneralFunction generalFunction;
	InputFormViewImpl inputForm;
	
	public InputFormPresenter() {
		inputForm=new InputFormViewImpl();
		inputForm.init();
	}

	@Override
	public Component getViewComponent() {
		return inputForm;
	}
}
