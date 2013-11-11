package com.binar.core.requirementPlanning.inputRequierementPlanning;

import java.util.Collection;

import com.binar.core.PresenterInterface;
import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormModel;
import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormPresenter;
import com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm.InputFormViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

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
		this.updateTable(view.getPeriodeValue());
	}

	@Override
	public void buttonClick(String source) {

	}
	@Override
	public void buttonClick(String source, Object data) {
		if(source.equals("buttonInput")){
			if(formModel==null){
				formModel=new InputFormModel(generalFunction);
				formView=new InputFormViewImpl();
				formPresenter =new InputFormPresenter(formModel, formView,
						generalFunction, (String)data);
			}
			System.out.println("Data = "+data.toString());
			formPresenter.setPeriode((String)data);
			view.displayForm(formView);
			
			//menambahkan listener, agar ketika window diclose, tampilan table akan diupdate
			Collection<Window> windows=view.getUI().getWindows();
			for(Window window:windows){
				window.addCloseListener(new CloseListener() {
					public void windowClose(CloseEvent e) {
						updateTable(view.getPeriodeValue());
					}
				});
			}
			
		}
	}

	@Override
	public void selectChange(Object data) {
		String stringData=(String)data;
		String[] splitted=stringData.split("-");
		for(String x:splitted){
			System.out.println("data "+x);
		}
		updateTable((String)data);
	}
	
	public void updateTable(String data){
		this.view.setTableData(model.getTableData(
				generalFunction.getDate().parseDateMonth((String)data)));
		
	}
	

	

	
	
}
