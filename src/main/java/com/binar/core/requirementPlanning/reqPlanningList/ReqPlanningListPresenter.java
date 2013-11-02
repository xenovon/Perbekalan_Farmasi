package com.binar.core.requirementPlanning.reqPlanningList;

import com.binar.core.PresenterInterface;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Component;

public class ReqPlanningListPresenter implements PresenterInterface{

	GeneralFunction function;
	ReqPlanningListModel model;
	ReqPlanningListViewImpl view;
	
	public ReqPlanningListPresenter(GeneralFunction function, 
			ReqPlanningListModel model, ReqPlanningListViewImpl view){
		this.model=model;
		this.view=view;
		
		view.init();
	}


}
