package com.binar.core.requirementPlanning.inputRequierementPlanning;

import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public interface InputRequirementPlanningView {
	
	interface InputRequirementListener{
		void buttonClick(String source);
		void buttonClick(String source, Object data);
	}
	
	public void displayForm(Component content);
	public Window getWindow();
	public void addListener(InputRequirementListener listener);
}
