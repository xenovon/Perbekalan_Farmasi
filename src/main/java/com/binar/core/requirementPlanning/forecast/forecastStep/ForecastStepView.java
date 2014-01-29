package com.binar.core.requirementPlanning.forecast.forecastStep;

import java.util.Map;

import com.binar.core.requirementPlanning.forecast.forecastProcessor.Forecaster;
import com.vaadin.ui.Component;

interface  ForecastStepView {
	public interface ForecastStepListener{
		public void buttonNext();
		public void buttonPrev();
		public void buttonClose();
	}
	
	public enum ViewMode{
		INPUT_DATA, RESULT
	}
	public void init();
	public void construct();
	public void setViewMode(ViewMode mode);
	public void setComboGoodsData(Map<String, String> data);
	//chart dan data forecast
	public void generateForecastView(Component component, Forecaster forecaster);

}
