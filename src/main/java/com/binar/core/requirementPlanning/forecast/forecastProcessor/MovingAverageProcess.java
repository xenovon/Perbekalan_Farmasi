package com.binar.core.requirementPlanning.forecast.forecastProcessor;

import java.util.List;

import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.Observation;
import net.sourceforge.openforecast.models.MovingAverageModel;
import net.sourceforge.openforecast.models.NaiveForecastingModel;

public class MovingAverageProcess implements ForecastProcessInterface
{

	private MovingAverageModel model;
	private DataSet dataSet;
	private Observation nextMonth;
	public void init(DataSet dataSet) {
		this.dataSet=dataSet;
		model=new MovingAverageModel(getDataSet());
		model.init(dataSet);
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
	private int getDataSet(){
		return dataSet.size()-1;
	}
}
