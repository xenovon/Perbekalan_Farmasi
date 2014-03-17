package com.binar.core.requirementPlanning.forecast.forecastProcessor;

import java.util.List;

import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.Observation;
import net.sourceforge.openforecast.models.MovingAverageModel;
import net.sourceforge.openforecast.models.SimpleExponentialSmoothingModel;

public class SimpleExponentialSmoothingProcess implements ForecastProcessInterface {

	SimpleExponentialSmoothingModel model;
	private DataSet dataSet;
	private Observation nextMonth;
	public void init(DataSet dataSet) {
		model=SimpleExponentialSmoothingModel.getBestFitModel(dataSet);
		this.dataSet=dataSet;
		nextMonth=new Observation(0);
		nextMonth.setIndependentValue(Forecaster.INDEPENDENT_VARIABLE, (dataSet.size()+1));
//		alphaTest();
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
	
	public void alphaTest(){
		System.out.println("Mulai Testing Alpha Simple Exponential Smoothing Model==========================");
		System.out.println("Data set :" +dataSet.toString());
		System.out.println("Rekomendasi dari sistem : ");
		System.out.println("--Alpha : "+model.getAlpha());
		System.out.println("--MSE : "+model.getMSE());
		System.out.println("--Hasil : "+model.forecast(nextMonth));
		System.out.println("Pengetesan Berbagai Variasi Alpha");

		double alpha=0;
		while(!(alpha>1)){
			System.out.println("Simple Exponential Smoothing Model--------------------------------");
			SimpleExponentialSmoothingModel model=new SimpleExponentialSmoothingModel(alpha);
			model.init(dataSet);
			System.out.println("--Alpha : "+model.getAlpha());
			System.out.println("--MSE : "+model.getMSE());
			System.out.println("--Hasil : "+model.forecast(nextMonth));
			System.out.println("--------------------------------------------------");
			alpha=alpha+0.1;
		}
		System.out.println("Akhir Testing Alpha ==========================================================");
	}
}
