package com.binar.core.requirementPlanning;

import com.binar.core.requirementPlanning.reqPlanningList.ReqPlanningListPresenter;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ReqPlanningList extends CssLayout {

	Label label=new Label("Daftar Rencana Kebutuhan");
	GeneralFunction generalFunction;
	
	public ReqPlanningList(GeneralFunction function) {
		this.generalFunction = function;
		ReqPlanningListPresenter presenter=new ReqPlanningListPresenter(generalFunction);

		this.setCaption("Daftar Rencana Kebutuhan");
		this.addStyleName("tab-content");
		
		this.addComponent(presenter.getViewComponent());
		

	}
}
