package com.binar.core.requirementPlanning.forecast;

import com.binar.core.requirementPlanning.forecast.ForecastView.ForecastListener;
import com.binar.core.requirementPlanning.forecast.forecastStep.ForecastStepModel;
import com.binar.core.requirementPlanning.forecast.forecastStep.ForecastStepPresenter;
import com.binar.core.requirementPlanning.forecast.forecastStep.ForecastStepViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Notification;

public class ForecastPresenter implements ForecastListener{

	ForecastModel model;
	ForecastViewImpl view;
	GeneralFunction function;
	
	ForecastStepModel stepModel;
	ForecastStepViewImpl stepView;
	ForecastStepPresenter stepPresenter;
	
	public ForecastPresenter(ForecastModel model, ForecastViewImpl view, GeneralFunction function) {
		this.model=model;
		this.view=view;
		this.function=function;
		this.view.setListener(this);
		this.view.init();
	}
	@Override
	public void buttonStartClick() {
		if(stepPresenter==null){
			stepModel= new ForecastStepModel(function);
			stepView=new ForecastStepViewImpl(function);
			stepPresenter=new ForecastStepPresenter(stepModel, stepView, function);
		}
		
		view.displayForm(stepView, "Peramalan");
	}

}
