package com.binar.core.requirementPlanning.forecast.forecastProcessor;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.Observation;

import org.dussan.vaadin.dcharts.ChartImageFormat;
import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.DownloadButtonLocation;
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
import org.joda.time.DateTime;

import com.vaadin.ui.Component;

public class Forecaster {

	
	public  static  final String INDEPENDENT_VARIABLE="bulan";
	public static final int PERIODE_PER_YEAR=12;
	public static final String TIME_VARIABLE="month";
	private DoubleExponentialSmoothingProcess processDoubleES;
	private MovingAverageProcess processMovingAverage;
	private SimpleExponentialSmoothingProcess processSimpleES;
	private TripleExponentialSmoothingProcess processTripleES;
	private NaiveProcess processNaive;
	private List<Integer> data;
	private boolean tripleSupport=false;
	
	public void execute(List<Integer> data){
		this.data=data;
		DataSet dataSet=generateDataSet(data,false);

		if(processDoubleES==null){
			processDoubleES=new DoubleExponentialSmoothingProcess();
		}
		processDoubleES.init(dataSet);
		
		if(processMovingAverage==null){
			processMovingAverage =new MovingAverageProcess();
		}
		processMovingAverage.init(dataSet);
		
		if(processSimpleES==null){
			processSimpleES=new SimpleExponentialSmoothingProcess();
		}
		processSimpleES.init(dataSet);
		if(processNaive==null){
			processNaive=new NaiveProcess();
		}
		processNaive.init(dataSet);
		
		tripleSupport=false;
		
		if(data.size()>=24){
			if(processTripleES==null){
				processTripleES=new TripleExponentialSmoothingProcess();
			}
			tripleSupport=true;
			processTripleES.init(generateDataSet(data, true));
		}
	}
	//Untuk menghilangkan value kosong
	private List<Integer> filterData(List<Integer> input){
		List<Integer> returnValue=new ArrayList<Integer>();
		for (Integer integer : input) {
			if(integer!=0){
				returnValue.add(integer);
			}
		}
		return returnValue;
	}
	
	// jika getTripleModel true maka method akan mengembalikan  
	//dataset yang dikhususkan untuk triple exponential smoothing, yakni tidak difilter
	private DataSet generateDataSet(List<Integer> data, boolean getTripleModel){
		DataSet dataSet=new DataSet();
		if(!getTripleModel){
			data=filterData(data); //difilter
		}
		int i=1;
		for(Integer integer:data){
			Observation obs=new Observation(integer);
			obs.setIndependentValue(INDEPENDENT_VARIABLE, i);
			dataSet.add(obs);
			i++;
		}
		dataSet.setPeriodsPerYear(PERIODE_PER_YEAR);
		dataSet.setTimeVariable(TIME_VARIABLE);
		return dataSet;
	}

	public DoubleExponentialSmoothingProcess getProcessDoubleES() {
		return processDoubleES;
	}

	public MovingAverageProcess getProcessMovingAverage() {
		return processMovingAverage;
	}

	public SimpleExponentialSmoothingProcess getProcessSimpleES() {
		return processSimpleES;
	}

	public TripleExponentialSmoothingProcess getProcessTripleES() {
		return processTripleES;
	}

	public NaiveProcess getProcessNaive() {
		return processNaive;
	}

	public boolean isTripleSupport() {
		return tripleSupport;
	}
	
	public List<Integer> getData() {
		return data;
	}
	
	public List<Integer> getDataFilter() {
		return filterData(data);
	}
}
