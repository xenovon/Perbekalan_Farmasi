package com.binar.core.requirementPlanning;

import com.binar.core.requirementPlanning.reqPlanningList.ReqPlanningListModel;
import com.binar.core.requirementPlanning.reqPlanningList.ReqPlanningListPresenter;
import com.binar.core.requirementPlanning.reqPlanningList.ReqPlanningListViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ReqPlanningList extends CssLayout {

	Label label=new Label("Daftar Rencana Kebutuhan");
	GeneralFunction generalFunction;
	ReqPlanningListPresenter presenter;
	ReqPlanningListModel model;
	ReqPlanningListViewImpl view;
	
	public ReqPlanningList(GeneralFunction function) {
		this.generalFunction = function;
		model=new ReqPlanningListModel(generalFunction);
		view=new ReqPlanningListViewImpl(generalFunction);
		presenter=new ReqPlanningListPresenter(generalFunction, model, view);

		this.setCaption("Daftar Rencana Kebutuhan");
		this.addStyleName("tab-content");
		
		this.addComponent(view);
		

	}
}
