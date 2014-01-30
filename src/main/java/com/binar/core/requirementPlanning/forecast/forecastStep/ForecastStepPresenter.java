package com.binar.core.requirementPlanning.forecast.forecastStep;

import java.util.Collection;




import java.util.List;

import com.binar.core.requirementPlanning.forecast.forecastProcessor.Forecaster;
import com.binar.core.requirementPlanning.forecast.forecastStep.ForecastStepView.ForecastStepListener;
import com.binar.core.requirementPlanning.forecast.forecastStep.ForecastStepView.ViewMode;
import com.binar.entity.Goods;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Window;

public class ForecastStepPresenter implements ForecastStepListener{

	ForecastStepModel model;
	ForecastStepViewImpl view;
	GeneralFunction function;
	Forecaster forecaster;
	Window window;
	public ForecastStepPresenter(ForecastStepModel model, ForecastStepViewImpl view, GeneralFunction function, String idGoods) {
		this(model, view, function);
		this.view.setSelectedGoods(idGoods);
	}
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
		if(forecaster==null){
			forecaster=new Forecaster();
		}
		if (selectedGoods!=null) {
			Goods goods=model.getGoods(selectedGoods);
			view.setForecastTitle("Peramalan untuk barang " + goods.getName());
//			List<Integer> data=model.generateConsumptionData(selectedGoods, selectedPeriod);
			List<Integer> data=model.generateDummyConsumptionData(selectedPeriod, true);
			System.err.println("Data : "+ data.toString());
			if(data!=null){ //jika datanya ada
				forecaster.execute(data); //menghitung forecast			
				ForecastChart chart=new ForecastChart(forecaster, goods);
				if(forecaster.isTripleSupport()){
					ForecastChart chartTriple=new ForecastChart(forecaster, goods);
					chart.generateChart();
					chartTriple.generateTripleESChart();
					view.generateForecastView(chart, chartTriple, forecaster);					
				}else{
					chart.generateChart();
					view.generateForecastView(chart, null, forecaster);					
					
				}
			}
			
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
		if(window!=null){
			view.getUI().removeWindow(window);
		}else{
			Collection<Window> list=view.getUI().getWindows();
			for(Window w:list){
				view.getUI().removeWindow(w);
				view.setViewMode(ViewMode.INPUT_DATA);
			}			
		}
		
	}
	public void setWindow(Window window) {
		this.window = window;
	}
}
