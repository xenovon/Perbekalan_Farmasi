package com.binar.core.report.reportInterface.reportContent.reportStock;

import java.util.Calendar;
import java.util.List;

import com.binar.core.report.reportInterface.ReportInterfaceView.ReportInterfaceListener;
import com.binar.core.report.reportInterface.reportContent.ReportContentView;
import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportContentListener;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportType;
import com.binar.core.report.reportInterface.reportContent.ReportData.PeriodeType;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;

public class ReportStockViewImpl extends VerticalLayout implements ClickListener, ValueChangeListener, ReportContentView {

	/*
	 * (non-Javadoc)
	 * @see com.binar.core.report.reportInterface.reportContent.ReportContentView#init()
	 	Cuma per hari
	 	
	 	laporan Obat dan alkes/bmhp
	 */
	private Button buttonCancel;
	private Button buttonPrint;
	
	private OptionGroup selectGoodsType;
	private DateField selectDate;
	
	private GeneralFunction function;
	
	
	private ReportContentListener listener;
	public ReportStockViewImpl(GeneralFunction function) {
		this.function=function;
	}
	
	@Override
	public void init() {
		selectDate=new DateField("Pilih Tanggal Laporan");
		selectDate.setImmediate(true);
		
		List<String> yearList=function.getListFactory().createYearList(6, 2, false);
		List<String> monthList=function.getListFactory().createMonthList();
		
		
		buttonCancel=new Button("Batalkan");
		buttonCancel.addClickListener(this);
		buttonPrint=new Button("Cetak");
		buttonPrint.addClickListener(this);
		
		selectGoodsType=new OptionGroup();
		Item itemType1=selectGoodsType.addItem("obat");
		Item itemType2=selectGoodsType.addItem("alkes/bmhp");
		selectGoodsType.setImmediate(true);
		
		selectGoodsType.setItemCaption(itemType1, "Laporan Penerimaan Obat");
		selectGoodsType.setItemCaption(itemType2, "Laporan Penerimaan Alkes & BMHP");
		
		
	}

	@Override
	public void construct() {
		this.setSpacing(true);
		this.setMargin(true);
		GridLayout layout=new GridLayout(2, 1){
			{
				addComponent(buttonPrint, 0,0);
				addComponent(buttonPrint, 1, 0);
				setSpacing(true);
				setMargin(true);
			}
		};
		this.addComponents(selectGoodsType, selectDate, layout);
	}
	@Override
	public void setListener(ReportContentListener listener) {
		this.listener=listener;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonCancel){
			listener.cancelClick();
		}if(event.getButton()==buttonPrint){
			listener.printClick(ReportType.RECEIPT, getReportData());
		}
	}
	
	@Override
	public Button getPrintButton() {
		return buttonPrint;
	}
	
	//apakah yang pilih adalah bulanan
	private boolean isViewMonthMode(){
		return false;
	}
	public void changeViewMode(boolean viewByMonth) {
	}
	@Override
	public ReportData getReportData() {
		ReportData data=new ReportData(function);
		data.setSelectedDay(selectDate.getValue());
		data.setSelectedGoods((String)selectGoodsType.getValue());
		data.setType(ReportType.STOCK);
		return data;
	}


}
