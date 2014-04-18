package com.binar.core.requirementPlanning.forecast.forecastStep;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.dussan.vaadin.dcharts.DCharts;

import com.binar.core.requirementPlanning.forecast.forecastProcessor.Forecaster;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ForecastStepViewImpl extends VerticalLayout implements ForecastStepView, ClickListener{

	private GeneralFunction function;
	private ComboBox selectGoods;
	private ComboBox selectPeriod;
	private Button buttonNext;
	private Button buttonPrevious;
	private Button buttonClose;
	private Label labelForecastTitle;
	private VerticalLayout layoutResultForecast;
	private CssLayout layoutChart;
	private Label labelHelp;
	private CssLayout layoutButtonSwitch;  //untuk tombol yang mengganti mode Triple Exp Smoo ke mode lainnya
	private HorizontalLayout layoutResult;
	private ForecastStepListener listener;
	
	TextManipulator text;
	public ForecastStepViewImpl(GeneralFunction function) {
		this.function=function;
		this.text=function.getTextManipulator();
	}
	@Override
	public void init() {
		selectGoods=new ComboBox("Pilih Barang");
		selectGoods.setWidth(function.FORM_WIDTH);
		selectGoods.setImmediate(true);
		selectPeriod = new ComboBox("Ramalkan Data Berdasarkan Data"){
			{
				setImmediate(true);
				setWidth(function.FORM_WIDTH);
				addItem("3");
				addItem("6");
				addItem("12");
				addItem("24");
				addItem("36");
				
				setItemCaption("3", "3 Bulan Terakhir");
				setItemCaption("6", "6 Bulan Terakhir");
				setItemCaption("12", "Satu Tahun Terakhir");
				setItemCaption("24", "Dua Tahun Terakhir");
				setItemCaption("36", "Tiga Tahun Terakhir");
				setValue("6");
			}
		};
		labelForecastTitle=new Label();
		buttonClose =new Button("Tutup");
		buttonClose.addClickListener(this);
		
		buttonNext =new Button("Selanjutnya");
		buttonNext.addClickListener(this);

		buttonPrevious =new Button("Kembali");
		buttonPrevious.addClickListener(this);
		
		construct();
	}
	FormLayout formLayout;
	@Override
	public void construct() {
		formLayout=new FormLayout(){
			{
				setMargin(true);
				setSpacing(true);
				addComponent(selectGoods);
				addComponent(selectPeriod);
			}
		};
		
		HorizontalLayout buttonLayout=new HorizontalLayout(){
			{
				setMargin(true);
				setSpacing(true);
				addComponent(buttonNext);
				addComponent(buttonPrevious);
				addComponent(buttonClose);
			}
		};
		labelForecastTitle.setValue("Peramalan Untuk Barang");
		
		layoutResult=new HorizontalLayout();
		layoutResult.setSpacing(true);
		layoutResult.setMargin(true);
		
		layoutChart =new CssLayout();
		labelHelp=new Label("<i>Pilih hasil peramalan dengan angka MSE paling kecil</i>", ContentMode.HTML);
		
		layoutButtonSwitch=new CssLayout();
		
		layoutResultForecast =new VerticalLayout(){{
			setMargin(true);
			setSpacing(true);
			
			addComponent(labelForecastTitle);
			addComponent(layoutChart);
			addComponent(layoutResult);
			addComponent(layoutButtonSwitch);
			addComponent(labelHelp);
		}};
		this.setMargin(true);
		this.setSpacing(true);
		this.addComponents(formLayout, layoutResultForecast, buttonLayout);
	}
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonClose){
			listener.buttonClose();
		}else if(event.getButton()==buttonNext){
			listener.buttonNext();
		}else if(event.getButton()==buttonPrevious){
			listener.buttonPrev();
		}
	}
	public void setListener(ForecastStepListener listener){
		this.listener=listener;
	}
	
	@Override
	public void setViewMode(ViewMode mode) {
		if(mode==ViewMode.INPUT_DATA){
			buttonPrevious.setVisible(false);
			buttonNext.setVisible(true);
			formLayout.setVisible(true);
			layoutResultForecast.setVisible(false);
		}else{
			buttonPrevious.setVisible(true);
			buttonNext.setVisible(false);
			formLayout.setVisible(false);
			layoutResultForecast.setVisible(true);
		}
	}
	public void setComboGoodsData(Map<String,String> data) {
		String selectValue="";
		int i=0;
        for (Map.Entry<String, String> entry : data.entrySet()) {
        	selectGoods.addItem(entry.getKey());
        	if(i==0){
        		selectValue=entry.getKey();
        	}
        	selectGoods.setItemCaption(entry.getKey(), entry.getValue());
        	i++;
        }
       	selectGoods.setValue(selectValue);
	};
	public String getSelectedGoods(){
		return (String)selectGoods.getValue();
	}
	public String getSelectedPeriod(){
		return (String)selectPeriod.getValue();
	}
	public void setForecastTitle(String forecastTitle){
		labelForecastTitle.setValue("<h4>"+forecastTitle+"</h4>");
		labelForecastTitle.setContentMode(ContentMode.HTML);
	}
	//ditujukan ketika kelas ini diakses melalui input requirement (barangnya kepilih dluan)
	public void setSelectedGoods(String idGoods){
		selectGoods.setValue(idGoods);
		selectGoods.setReadOnly(true);
	}
	
	VerticalLayout layoutTriple;
	VerticalLayout layoutSimple;
	VerticalLayout layoutDouble;
	VerticalLayout layoutNaive;

	@Override
	public void generateForecastView(Component chartAll, Component chartTriple,
			Forecaster forecaster) {
		final Forecaster finalForecaster=forecaster;
		final Component finalChartTriple=chartTriple;
		final Component finalChartAll=chartAll;
		
		
		System.out.println("Generate Forecast VIew");
		
		boolean isTripleMode;
		if(chartTriple!=null){
			isTripleMode=true;
		}else{
			isTripleMode=false;
		}
		
		ForecastType minimumMSE=getSmallestMSE(finalForecaster);
		
		
		//langkah 1, tambahkan chart;
		layoutChart.removeAllComponents();
		if(isTripleMode){
			layoutChart.addComponent(chartTriple);
			chartTriple.setVisible(false);
		}
		layoutChart.addComponent(chartAll);
		
		
		
		//Langkah 2, tambahkan komponen angka
		//inisialisasi  variabel
		layoutResult.removeAllComponents();
		if(isTripleMode){
			
			layoutTriple=new VerticalLayout(){{
				addComponent(new Label("<h4>Triple Exponential Smoothing</h4>", ContentMode.HTML));
				addComponent(new Label(text.intToAngka(finalForecaster.getProcessTripleES().getNextMonthValue())+"", ContentMode.HTML));
				addComponent(new Label("MSE : "+text.doubleToAngka2(finalForecaster.getProcessTripleES().getNextMonthMSE())));
			}};
			layoutTriple.setVisible(false);
			layoutResult.addComponent(layoutTriple);
		}
		
		if(minimumMSE==ForecastType.SIMPLE){
			layoutSimple=new VerticalLayout(){{
				addComponent(new Label("<h4 style='color:#00d607'>Simple Exponential Smoothing</h4>", ContentMode.HTML));
				addComponent(new Label("<span style='color:#00d607'>"+text.intToAngka(finalForecaster.getProcessSimpleES().getNextMonthValue())+"</span>", ContentMode.HTML));
				addComponent(new Label("<span style='color:#00d607'> MSE : "+text.doubleToAngka2(finalForecaster.getProcessSimpleES().getNextMonthMSE())+"</span>", ContentMode.HTML));			
			}};			
		}else{
			layoutSimple=new VerticalLayout(){{
				addComponent(new Label("<h4>Simple Exponential Smoothing</h4>", ContentMode.HTML));
				addComponent(new Label(text.intToAngka(finalForecaster.getProcessSimpleES().getNextMonthValue())+"", ContentMode.HTML));
				addComponent(new Label("MSE : "+text.doubleToAngka2(finalForecaster.getProcessSimpleES().getNextMonthMSE())));			
			}};

		}
		layoutResult.addComponent(layoutSimple);
		
		if(minimumMSE==ForecastType.DOUBLE){
			layoutDouble= new VerticalLayout(){{
				addComponent(new Label("<h4 style='color:#00d607'>Double Exponential Smoothing</h4>", ContentMode.HTML));
				addComponent(new Label("<span style='color:#00d607'>"+text.intToAngka(finalForecaster.getProcessDoubleES().getNextMonthValue())+"</span>", ContentMode.HTML));
				addComponent(new Label("<span style='color:#00d607'>MSE : "+text.doubleToAngka2(finalForecaster.getProcessDoubleES().getNextMonthMSE())+"</span>", ContentMode.HTML));
				
			}};			
		}else{
			layoutDouble= new VerticalLayout(){{
				addComponent(new Label("<h4>Double Exponential Smoothing</h4>", ContentMode.HTML));
				addComponent(new Label(text.intToAngka(finalForecaster.getProcessDoubleES().getNextMonthValue())+"", ContentMode.HTML));
				addComponent(new Label("MSE : "+text.doubleToAngka2(finalForecaster.getProcessDoubleES().getNextMonthMSE())));
				
			}};
			
		}
		layoutResult.addComponent(layoutDouble);
		
//		if(minimumMSE==ForecastType.MOVING){
//			layoutMoving =new VerticalLayout(){{
//				addComponent(new Label("<h4 style='color:#00d607'>Moving Average</h4>", ContentMode.HTML));
//				addComponent(new Label("<span style='color:#00d607'>"+text.intToAngka(finalForecaster.getProcessMovingAverage().getNextMonthValue())+"</span>", ContentMode.HTML));
//				addComponent(new Label("<span style='color:#00d607'>MSE : "+text.doubleToAngka2(finalForecaster.getProcessMovingAverage().getNextMonthMSE())+"</span>", ContentMode.HTML));			
//			}};
//			
//		}else{
//			layoutMoving =new VerticalLayout(){{
//				addComponent(new Label("<h4>Moving Average</h4>", ContentMode.HTML));
//				addComponent(new Label(text.intToAngka(finalForecaster.getProcessMovingAverage().getNextMonthValue())+"", ContentMode.HTML));
//				addComponent(new Label("MSE : "+text.doubleToAngka2(finalForecaster.getProcessMovingAverage().getNextMonthMSE())));			
//			}};
//
//		}

			
//		layoutResult.addComponent(layoutMoving);
		
		if(minimumMSE==ForecastType.NAIVE){
			layoutNaive =new VerticalLayout(){{
				addComponent(new Label("<h4 style='color:#00d607'>Naive</h4>", ContentMode.HTML));
				addComponent(new Label("<span style='color:#00d607'>"+text.intToAngka(finalForecaster.getProcessNaive().getNextMonthValue())+"</span>", ContentMode.HTML));
				addComponent(new Label("<span style='color:#00d607'>MSE : "+text.doubleToAngka2(finalForecaster.getProcessNaive().getNextMonthMSE())+"</span>", ContentMode.HTML));			
			}};			
		}else{
			
			layoutNaive =new VerticalLayout(){{
				addComponent(new Label("<h4>Naive</h4>", ContentMode.HTML));
				addComponent(new Label(text.intToAngka(finalForecaster.getProcessNaive().getNextMonthValue())+"", ContentMode.HTML));
				addComponent(new Label("MSE : "+text.doubleToAngka2(finalForecaster.getProcessNaive().getNextMonthMSE())));			
			}};

		}
		layoutResult.addComponent(layoutNaive);
		
		//Langkah 3, jika ada triple, tambahkan tombol
		
		layoutButtonSwitch.removeAllComponents();
		if(isTripleMode){
			final Button button=new Button("Tampilkan Triple Exp Smoothing");
			button.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					if(layoutTriple.isVisible()){
						layoutTriple.setVisible(false);
						finalChartTriple.setVisible(false);
						finalChartAll.setVisible(true);
						
						layoutDouble.setVisible(true);
						layoutNaive.setVisible(true);
						layoutSimple.setVisible(true);
						
						button.setCaption("Tampilkan Triple Exp Smoothing");

					}else{
						layoutTriple.setVisible(true);
						finalChartTriple.setVisible(true);
						finalChartAll.setVisible(false);
						
						layoutDouble.setVisible(false);
						layoutNaive.setVisible(false);
						layoutSimple.setVisible(false);

						button.setCaption("Tampilkan Metode Lainnya");

					}
				}
			});
			
			layoutButtonSwitch.addComponent(button);
		}
	}
	
	enum ForecastType{
		NAIVE, SIMPLE, DOUBLE, MOVING, 
	}
	
	private ForecastType getSmallestMSE(Forecaster forecast){
		SortedMap<Double, ForecastType> sort=new TreeMap<Double, ForecastType>();
			
		sort.put(forecast.getProcessDoubleES().getNextMonthMSE(), ForecastType.DOUBLE );
		sort.put( forecast.getProcessSimpleES().getNextMonthMSE(), ForecastType.SIMPLE);
		sort.put( forecast.getProcessNaive().getNextMonthMSE(), ForecastType.NAIVE);
//		sort.put(forecast.getProcessMovingAverage().getNextMonthMSE(), ForecastType.MOVING);
		
		return sort.get(sort.firstKey());
	}
	
	
	/*testing */
	public static void main(String[] args) {
		SortedMap<Double, ForecastType> sort=new TreeMap<Double, ForecastType>();
		
		sort.put(12.0, ForecastType.DOUBLE);
		sort.put(1.1,ForecastType.SIMPLE);
		sort.put(10.0, ForecastType.NAIVE);
		sort.put(0.01, ForecastType.MOVING);
		
		System.out.println(sort.get(sort.firstKey()));
	}
	public void generateForecastEmpty(Component chartTriple,
			Forecaster forecaster){
		final Forecaster finalForecaster=forecaster;
		final Component finalChartTriple=chartTriple;
		//langkah 1, tambahkan chart;
		layoutChart.removeAllComponents();
		layoutResult.removeAllComponents();
		if(chartTriple!=null){
			layoutChart.addComponent(chartTriple);
			layoutResult.removeAllComponents();
			layoutTriple=new VerticalLayout(){{
				addComponent(new Label("<h4>Triple Exponential Smoothing</h4>", ContentMode.HTML));
				addComponent(new Label(text.intToAngka(finalForecaster.getProcessTripleES().getNextMonthValue())+"", ContentMode.HTML));
				addComponent(new Label("MSE : "+text.doubleToAngka2(finalForecaster.getProcessTripleES().getNextMonthMSE())));
			}};
			layoutTriple.setVisible(false);
			layoutResult.addComponent(layoutTriple);
			
		}else{
			layoutChart.addComponent(new Label("Data Tidak Tersedia"));
		}
		


	}
	

	
}
