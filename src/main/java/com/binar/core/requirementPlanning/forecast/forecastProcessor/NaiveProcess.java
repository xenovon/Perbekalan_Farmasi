package com.binar.core.requirementPlanning.forecast.forecastProcessor;

import java.util.List;

import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.Observation;
import net.sourceforge.openforecast.models.NaiveForecastingModel;

public class NaiveProcess implements ForecastProcessInterface {
	
	NaiveForecastingModel model;
	DataSet dataSet;
	Observation nextMonth;
	@Override
	public void init(DataSet dataSet) {
		model=new NaiveForecastingModel();
		model.init(dataSet);
		this.dataSet=dataSet;
		nextMonth=new Observation(0);
		nextMonth.setIndependentValue(Forecaster.INDEPENDENT_VARIABLE, (dataSet.size()+1));
				
	}

	@Override
	public int getNextMonthValue() {
		if(model!=null){
			return (int)model.forecast(nextMonth);
		}else{
			return 0;
		}
	}

	@Override
	public double getNextMonthMSE() {
		if(model!=null){
			return model.getMSE();			
		}else{
			return 0;
		}
	}

}
