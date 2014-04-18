package com.binar.core.requirementPlanning.forecast.forecastStep;

import java.util.Date;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.metadata.LegendPlacements;
import org.dussan.vaadin.dcharts.metadata.TooltipAxes;
import org.dussan.vaadin.dcharts.metadata.XYaxes;
import org.dussan.vaadin.dcharts.metadata.locations.LegendLocations;
import org.dussan.vaadin.dcharts.metadata.locations.TooltipLocations;
import org.dussan.vaadin.dcharts.metadata.renderers.LabelRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.LegendRenderers;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.AxesDefaults;
import org.dussan.vaadin.dcharts.options.Cursor;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Legend;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.renderers.legend.EnhancedLegendRenderer;

import com.binar.core.requirementPlanning.forecast.forecastProcessor.Forecaster;
import com.binar.entity.Goods;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class ForecastChart extends CustomComponent {

	private Forecaster forecaster; //berisi data2 hasil forecast
	private Goods goods;
	public ForecastChart(Forecaster forecaster, Goods goods){
		this.forecaster=forecaster;
		this.goods=goods;
	}
	
	public void setForecaster(Forecaster forecaster) {
		this.forecaster = forecaster;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public void generateChart(){
		if(forecaster!=null){
			DCharts chart=new DCharts().setOptions(generateOptions(false)).setDataSeries(generateDataSeries(false));
			chart.setCaption("Peramalan "+goods.getName());
			chart.show();
			this.setCompositionRoot(chart);			
		}else{
			this.setCompositionRoot(new Label("Kesalahan dalam menghasilkan chart >_< "));
		}

	}
	public void generateTripleESChart(){
		if(forecaster!=null){
			DCharts chart=new DCharts().setOptions(generateOptions(true)).setDataSeries(generateDataSeries(true));
			chart.setCaption("Peramalan "+goods.getName());
			chart.show();
			this.setCompositionRoot(chart);
		}else{
			this.setCompositionRoot(new Label("Kesalahan dalam menghasilkan chart >_< "));
		}
	}
	
	private DataSeries generateDataSeries(boolean triple){
		DataSeries series=new DataSeries();
		series.newSeries();
		int i=1;

		if(triple){
			int dataSize=forecaster.getData().size();

			for(Integer integer:forecaster.getData()){
				series.add(i,integer);
				i++;
			}
			series.newSeries();
			series.add(dataSize, forecaster.getData().get(dataSize-1));
			series.add(dataSize+1, forecaster.getProcessTripleES().getNextMonthValue());

		}else{
//			int dataSize=forecaster.getDataFilter().size();
			int dataSize=forecaster.getData().size();

			for(Integer integer:forecaster.getData()){
				series.add(i,integer);
				i++;
			}	
//			for(Integer integer:forecaster.getDataFilter()){
//				series.add(i,integer);
//				i++;
//			}	

			series.newSeries();
//			series.add(dataSize, forecaster.getDataFilter().get(dataSize-1));
			series.add(dataSize, forecaster.getData().get(dataSize-1));
			series.add(dataSize+1, forecaster.getProcessDoubleES().getNextMonthValue());

//			series.newSeries();
//			series.add(dataSize, forecaster.getDataFilter().get(dataSize-1));
//			series.add(dataSize+1, forecaster.getProcessMovingAverage().getNextMonthValue());

			series.newSeries();
			series.add(dataSize, forecaster.getData().get(dataSize-1));
//			series.add(dataSize, forecaster.getDataFilter().get(dataSize-1));
			series.add(dataSize+1, forecaster.getProcessNaive().getNextMonthValue());

			series.newSeries();
			series.add(dataSize, forecaster.getData().get(dataSize-1));
//			series.add(dataSize, forecaster.getDataFilter().get(dataSize-1));
			series.add(dataSize+1, forecaster.getProcessSimpleES().getNextMonthValue());

		}
		return series;		
	}
	
	private Options generateOptions(boolean triple){
		AxesDefaults axesDefaults = new AxesDefaults()
		.setLabelRenderer(LabelRenderers.CANVAS);		
		
		Axes axes = new Axes()
		.addAxis(
			new XYaxis()
				.setLabel("Bulan")
				.setPad(0))
		.addAxis(new XYaxis(XYaxes.Y)
				.setLabel("Jumlah Konsumsi"));
		Highlighter highlighter = new Highlighter()
		.setShow(true)
		.setSizeAdjust(10)
		.setTooltipLocation(TooltipLocations.NORTH)
		.setTooltipAxes(TooltipAxes.Y)
		.setTooltipFormatString("%.2f")
		.setUseAxesFormatters(false);

		Cursor cursor = new Cursor()
		.setShow(true);
		Legend legend = new Legend();
		legend.setShow(true);
		legend.setRenderer(LegendRenderers.ENHANCED);
		legend.setRendererOptions(
			new EnhancedLegendRenderer()
				.setNumberRows(1));
		legend.setPlacement(LegendPlacements.OUTSIDE_GRID);
		
		if(!triple){
			legend.setLabels("Riwayat Data","Double Exponential Smoothing" ,"Naive", "Simple Exponential Smoothing");
		
		}else{
			legend.setLabels("Riwayat Data", "Triple Exponential Smoothing");		
		}
		legend.setLocation(LegendLocations.SOUTH);
		
		Options options = new Options()
		.setAxesDefaults(axesDefaults)
		.setAxes(axes)
		.setHighlighter(highlighter)
		.setCursor(cursor)
		.setAnimate(true)
		.setLegend(legend);
		return options;
	}
		
}
