package com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm;

import com.binar.core.PresenterInterface;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;

public class InputFormPresenter implements PresenterInterface{

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
