package com.binar.core.report.reportInterface.reportContent.reportReceipt;

import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;

import com.binar.core.report.reportInterface.ReportInterfaceView.ReportInterfaceListener;
import com.binar.core.report.reportInterface.reportContent.ReportContentView;
import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.core.report.reportInterface.reportContent.ReportPrint;
import com.binar.core.report.reportInterface.reportContent.ReportData.PeriodeType;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;

public class ReportReceiptViewImpl extends FormLayout implements ClickListener, ValueChangeListener, ReportContentView {

	private Button buttonCancel;
	private Button buttonPrint;
	
	private OptionGroup selectGoodsType;
	private OptionGroup selectPeriode;
	private DateField selectDate;
	private ComboBox   selectYear;
	private ComboBox selectMonth;
	
	private GeneralFunction function;
	
	BrowserWindowOpener opener;
	
	private ReportContentListener listener;
	public ReportReceiptViewImpl(GeneralFunction function) {
		this.function=function;
	}
	
	@Override
	public void init() {
		buttonCancel=new Button("Batalkan");
		buttonCancel.addClickListener(this);
		buttonPrint=new Button("Cetak");
		buttonPrint.addClickListener(this);
		buttonPrint.setIcon(new ThemeResource("icons/image/icon-print.png"));

		opener=new BrowserWindowOpener(ReportPrint.class);
		opener.setFeatures("height=200,width=400,resizable");
		// A button to open the printer-friendly page.
		opener.extend(buttonPrint);

		selectDate=new DateField("Pilih Tanggal Laporan");
		selectDate.setImmediate(true);
		
		List<String> yearList=function.getListFactory().createYearList(8, 0, false);
		List<String> monthList=function.getListFactory().createMonthList();
		selectYear = new ComboBox("Pilih Tahun", yearList);
		selectMonth =new ComboBox("Pilih Bulan", monthList);

		selectYear.setImmediate(true);
		selectMonth.setImmediate(true);

		
		selectYear.setNullSelectionAllowed(false);
		selectMonth.setNullSelectionAllowed(false);
		selectYear.setValue(new DateTime().getYear()+"");
		selectMonth.setValue(monthList.get(Calendar.getInstance().get(Calendar.MONTH)));
		for(String month:monthList){
			System.out.println("Bulan "+month);
		}
		System.out.println("Bulan ini" + Calendar.getInstance().get(Calendar.MONTH));
		selectMonth.setTextInputAllowed(false);
		selectYear.setTextInputAllowed(false);
		selectMonth.setWidth(function.FORM_WIDTH);
		selectYear.setWidth(function.FORM_WIDTH);
		
		
	
		selectGoodsType=new OptionGroup("Tipe Laporan");
		Item itemType1=selectGoodsType.addItem("obat");
		Item itemType2=selectGoodsType.addItem("alkesbmhp");
		selectGoodsType.setImmediate(true);
		selectGoodsType.setValue("obat");
		selectGoodsType.setItemCaption("obat", "Laporan Penerimaan Obat");
		selectGoodsType.setItemCaption("alkesbmhp", "Laporan Penerimaan Alkes & BMHP");
		
		selectPeriode=new OptionGroup("Periode Laporan");
		Item itemPeriode1=selectPeriode.addItem("bulanan");
		Item itemPeriode2=selectPeriode.addItem("harian");
		selectPeriode.setImmediate(true);
		selectPeriode.addValueChangeListener(this);
		
		selectPeriode.setItemCaption("bulanan", "Laporan Bulanan");
		selectPeriode.setItemCaption("harian", "Laporan Harian");
		selectPeriode.setValue("bulanan");
		changeViewMode(true);

		 selectGoodsType.addValueChangeListener(this);
		 selectPeriode.addValueChangeListener(this);
		 selectDate.addValueChangeListener(this);
		 selectYear.addValueChangeListener(this);
		 selectMonth.addValueChangeListener(this);
		 
		 updateWindowOpener();
		construct();

	}
	public BrowserWindowOpener getOpener() {
		return opener;
	}
	@Override
	public void construct() {
		this.setSpacing(true);
		this.setMargin(true);
		GridLayout layout=new GridLayout(2, 1){
			{
				addComponent(buttonPrint, 0,0);
				addComponent(buttonCancel, 1, 0);
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
		updateWindowOpener();

	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonCancel){
			listener.cancelClick(ReportType.RECEIPT);
		}if(event.getButton()==buttonPrint){
		}
	}
	public void updateWindowOpener(){
		listener.printClick(ReportType.RECEIPT, getReportData());
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
		data.setType(ReportType.RECEIPT);
		return data;
	}
}
