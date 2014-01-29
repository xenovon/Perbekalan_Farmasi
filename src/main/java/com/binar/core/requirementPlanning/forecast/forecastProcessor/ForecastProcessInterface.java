package com.binar.core.requirementPlanning.forecast.forecastProcessor;

import java.util.List;

import net.sourceforge.openforecast.DataSet;

public interface ForecastProcessInterface {
	public void init(DataSet dataSet);
	public int getNextMonthValue();
	public double getNextMonthMSE();
}
