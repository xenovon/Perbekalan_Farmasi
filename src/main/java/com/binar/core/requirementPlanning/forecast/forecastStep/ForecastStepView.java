package com.binar.core.requirementPlanning.forecast.forecastStep;

import java.util.Map;

import org.dussan.vaadin.dcharts.DCharts;

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
	public void generateForecastView(Component chartAll, boolean isTripleMode, final Forecaster forecaster);

}
