package com.binar.core.requirementPlanning.forecast.forecastStep;

import java.util.Map;

import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
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
	private ForecastStepListener listener;
	public ForecastStepViewImpl(GeneralFunction function) {
		this.function=function;
		
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
				addItem("1");
				addItem("3");
				addItem("6");
				addItem("12");
				addItem("24");
				addItem("36");
				
				setItemCaption("1","Satu Bulan Terakhir");
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
		
		layoutResultForecast =new VerticalLayout(){{
			setMargin(true);
			setSpacing(true);
			addComponent(labelForecastTitle);
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
}
