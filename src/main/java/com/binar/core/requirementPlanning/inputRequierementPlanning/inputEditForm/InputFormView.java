package com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm;

import com.vaadin.ui.FormLayout;

public interface InputFormView {

	interface InputFormListener{
		void buttonClick(String source);
	}
	
	public void init();
	
	public void addListener(InputFormListener listener);
}
