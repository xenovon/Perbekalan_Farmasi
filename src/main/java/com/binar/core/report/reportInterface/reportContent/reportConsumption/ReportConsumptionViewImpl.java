package com.binar.core.report.reportInterface.reportContent.reportConsumption;

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

public class ReportConsumptionViewImpl extends VerticalLayout implements ClickListener, ValueChangeListener, ReportContentView  {

	/*
	 * (non-Javadoc)
	 * @see com.binar.core.report.reportInterface.reportContent.ReportContentView#init()
	 	Bisa perbulan dan per hari
	 	Obat dan alkes bmhp
	 */
	
	private Button buttonCancel;
	private Button buttonPrint;
	
	private OptionGroup selectGoodsType;
	private OptionGroup selectPeriode;
	private DateField selectDate;
	private ComboBox   selectYear;
	private ComboBox selectMonth;
	
	private GeneralFunction function;
	
	
	private ReportContentListener listener;
	public ReportConsumptionViewImpl(GeneralFunction function) {
		this.function=function;
	}
	
	@Override
	public void init() {
		selectDate=new DateField("Pilih Tanggal Laporan");
		selectDate.setImmediate(true);
		
		List<String> yearList=function.getListFactory().createYearList(6, 2, false);
		List<String> monthList=function.getListFactory().createMonthList();
		selectYear = new ComboBox("Pilih Tahun", yearList);
		selectMonth =new ComboBox("Pilih Bulan", monthList);

		selectYear.setImmediate(true);
		selectMonth.setImmediate(true);

		
		selectYear.setNullSelectionAllowed(false);
		selectMonth.setNullSelectionAllowed(false);
		selectYear.setValue(yearList.get(2));
		selectMonth.setValue(monthList.get(Calendar.getInstance().get(Calendar.MONTH)));
		for(String month:monthList){
			System.out.println("Bulan "+month);
		}
		System.out.println("Bulan ini" + Calendar.getInstance().get(Calendar.MONTH));
		selectMonth.setTextInputAllowed(false);
		selectYear.setTextInputAllowed(false);
		selectMonth.setWidth(function.FORM_WIDTH);
		selectYear.setWidth(function.FORM_WIDTH);
		
		
		buttonCancel=new Button("Batalkan");
		buttonCancel.addClickListener(this);
		buttonPrint=new Button("Cetak");
		buttonPrint.addClickListener(this);
		
		selectGoodsType=new OptionGroup();
		Item itemType1=selectGoodsType.addItem("obat");
		Item itemType2=selectGoodsType.addItem("alkesbmhp");
		selectGoodsType.setImmediate(true);
		
		selectGoodsType.setItemCaption(itemType1, "Laporan Penerimaan Obat");
		selectGoodsType.setItemCaption(itemType2, "Laporan Penerimaan Alkes & BMHP");
		
		selectPeriode=new OptionGroup();
		Item itemPeriode1=selectPeriode.addItem("bulanan");
		Item itemPeriode2=selectPeriode.addItem("harian");
		selectPeriode.setImmediate(true);
		
		selectPeriode.setItemCaption(itemPeriode1, "Laporan Bulanan");
		selectPeriode.setItemCaption(itemPeriode2, "Laporan Harian");
		construct();

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
		this.addComponents(selectGoodsType, selectPeriode, selectDate, selectYear, selectMonth, layout);
	}
	@Override
	public void setListener(ReportContentListener listener) {
		this.listener=listener;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==selectPeriode){
			String value=(String)selectPeriode.getValue();
			if(isViewMonthMode()){
				changeViewMode(true);
			}else{
				changeViewMode(false);
			}
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonCancel){
			listener.cancelClick(ReportType.CONSUMPTION);
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
		if(selectPeriode.getValue().equals("bulanan")){
			return true;
		}else{
			return false;
		}

	}
	public void changeViewMode(boolean viewByMonth) {
		if(viewByMonth){
			selectMonth.setVisible(true);
			selectYear.setVisible(true);
			selectDate.setVisible(false);
		}else{
			selectMonth.setVisible(false);
			selectYear.setVisible(false);
			selectDate.setVisible(true);
		}
	}
	@Override
	public ReportData getReportData() {
		ReportData data=new ReportData(function);
		PeriodeType periodeType;
		if(isViewMonthMode()){
			periodeType=PeriodeType.BY_MONTH;
		}else{
			periodeType=PeriodeType.BY_DAY;
		}
		data.setPeriodeType(periodeType);
		data.setSelectedDay(selectDate.getValue());
		data.setSelectedMonth((String) selectMonth.getValue());
		data.setSelectedYear((String) selectYear.getValue());
		data.setSelectedGoods((String)selectGoodsType.getValue());
		data.setType(ReportType.CONSUMPTION);
		return data;
	}
}
