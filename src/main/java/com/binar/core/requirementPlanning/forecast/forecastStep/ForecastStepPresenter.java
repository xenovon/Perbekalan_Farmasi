package com.binar.core.requirementPlanning.forecast.forecastStep;

import java.util.Collection;



import com.binar.core.requirementPlanning.forecast.forecastProcessor.Forecaster;
import com.binar.core.requirementPlanning.forecast.forecastStep.ForecastStepView.ForecastStepListener;
import com.binar.core.requirementPlanning.forecast.forecastStep.ForecastStepView.ViewMode;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Window;

public class ForecastStepPresenter implements ForecastStepListener{

	ForecastStepModel model;
	ForecastStepViewImpl view;
	GeneralFunction function;
	public ForecastStepPresenter(ForecastStepModel model, ForecastStepViewImpl view, GeneralFunction function) {
		this.model=model;
		this.view=view;
		this.function=function;
		this.view.setListener(this);
		this.view.init();
		this.view.setComboGoodsData(model.getGoodsData());
		this.view.setViewMode(ViewMode.INPUT_DATA);
	}
	@Override
	public void buttonNext() {
		view.setViewMode(ViewMode.RESULT);
		String selectedGoods = view.getSelectedGoods();
		String selectedPeriod = view.getSelectedPeriod();
		Forecaster forecaster=new Forecaster();
		
		if (selectedGoods!=null) {
			view.setForecastTitle("Peramalan untuk barang " + selectedGoods
					+ " dan " + selectedPeriod);
			
//			view.addChart(forecaster.forecast(selectedGoods, selectedPeriod));
		}
	}
	@Override
	public void buttonPrev() {
		view.setViewMode(ViewMode.INPUT_DATA);
	}
	@Override
	public void buttonClose() {
		closeWindow();
	}
	public void closeWindow(){
		Collection<Window> list=view.getUI().getWindows();
		for(Window w:list){
			view.getUI().removeWindow(w);
			view.setViewMode(ViewMode.INPUT_DATA);
		}
		
	}

}
