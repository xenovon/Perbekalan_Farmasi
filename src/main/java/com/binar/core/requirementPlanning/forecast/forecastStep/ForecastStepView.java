package com.binar.core.requirementPlanning.forecast.forecastStep;

import java.util.Map;

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

}
