package com.binar.core.requirementPlanning.forecast.forecastProcessor;

import java.util.List;

import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.Observation;
import net.sourceforge.openforecast.models.DoubleExponentialSmoothingModel;
import net.sourceforge.openforecast.models.MovingAverageModel;
import net.sourceforge.openforecast.models.TripleExponentialSmoothingModel;

public class TripleExponentialSmoothingProcess implements ForecastProcessInterface{

	TripleExponentialSmoothingModel model;
	private DataSet dataSet;
	private Observation nextMonth;
	public void init(DataSet dataSet) {
		model=TripleExponentialSmoothingModel.getBestFitModel(dataSet);
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
		System.out.println("Mulai Testing Alpha Gamma, Beta Triple Exponential Smoothing Model==========================");
		System.out.println("Data set :" +dataSet.toString());
		System.out.println("Rekomendasi dari sistem : ");
		System.out.println("--Alpha : "+model.getAlpha());
		System.out.println("--Gamma : "+model.getGamma());
		System.out.println("--Beta : "+model.getBeta());
		System.out.println("--MSE : "+model.getMSE());
		System.out.println("--Hasil : "+model.forecast(nextMonth));
		System.out.println("Pengetesan Berbagai Variasi Alpha Beta Gamma");
		double alpha=0;
		while(!(alpha>1)){
			double gamma=0;
			while(!(gamma>1)){
				double beta=0;
				while(!(beta>1)){
					System.out.println("Triple Exponential Smoothing Model--------------------------------");
					TripleExponentialSmoothingModel model=new TripleExponentialSmoothingModel(alpha,gamma, beta);
					model.init(dataSet);
					System.out.println("--Alpha : "+model.getAlpha());
					System.out.println("--Gamma : "+model.getGamma());
					System.out.println("--Beta : "+model.getBeta());
					System.out.println("--MSE : "+model.getMSE());
					System.out.println("--Hasil : "+model.forecast(nextMonth));
					System.out.println("--------------------------------------------------");				
					beta=beta+0.1;
				}
			gamma=gamma+0.1;
			}
			alpha=alpha+0.1;
		}
		System.out.println("Akhir Testing Triple  Exp Smoothing==========================================================");
	}
}
