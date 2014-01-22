package com.binar.core.requirementPlanning.forecast;

interface  ForecastView {
	public interface ForecastListener{
		public void buttonStartClick();
	}
	
	public void init();
	public void construct();
	public void showWindow();
	
}
