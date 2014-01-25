package com.binar.core.requirementPlanning;

import com.binar.core.requirementPlanning.approval.ApprovalModel;
import com.binar.core.requirementPlanning.approval.ApprovalPresenter;
import com.binar.core.requirementPlanning.approval.ApprovalViewImpl;
import com.binar.core.requirementPlanning.forecast.ForecastModel;
import com.binar.core.requirementPlanning.forecast.ForecastPresenter;
import com.binar.core.requirementPlanning.forecast.ForecastViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;

public class Forecast extends CssLayout {
	private VerticalLayout layout=new VerticalLayout();
	
	private GeneralFunction generalFunction;
	
	private ForecastModel model;
	private ForecastPresenter presenter;
	private ForecastViewImpl view;
	
	public Forecast(GeneralFunction function) {
		generalFunction=function;
		
		model = new ForecastModel(generalFunction);
		view =new ForecastViewImpl(generalFunction);
		presenter = new  ForecastPresenter(model, view, generalFunction);
		
		this.addComponent(view);
		this.setCaption("Persetujuan");
		this.setSizeFull();
		this.addStyleName("tab-content");
	}
	
}
