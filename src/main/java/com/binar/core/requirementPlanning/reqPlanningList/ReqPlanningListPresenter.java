package com.binar.core.requirementPlanning.reqPlanningList;

import com.binar.core.PresenterInterface;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Component;

public class ReqPlanningListPresenter implements PresenterInterface{

	ReqPlanningListViewImpl reqPlaningListViewImpl;
	
	public ReqPlanningListPresenter(GeneralFunction function){
		this.reqPlaningListViewImpl=new ReqPlanningListViewImpl(function);
		this.reqPlaningListViewImpl.init();
	}
	
	@Override
	public Component getViewComponent() {
		// TODO Auto-generated method stub
		return reqPlaningListViewImpl;
	}

}
