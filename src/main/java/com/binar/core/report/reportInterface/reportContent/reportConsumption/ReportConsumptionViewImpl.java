package com.binar.core.report.reportInterface.reportContent.reportConsumption;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;

import com.binar.core.report.reportInterface.ReportInterfaceView.ReportInterfaceListener;
import com.binar.core.report.reportInterface.reportContent.ReportContentView;
import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportContentListener;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportType;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ViewMode;
import com.binar.core.report.reportInterface.reportContent.ReportData.PeriodeType;
import com.binar.core.report.reportInterface.reportContent.ReportData.ReportContent;
import com.binar.core.report.reportInterface.reportContent.ReportPrint;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ThemeResource;
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
	private Button buttonShow;
	
	private OptionGroup selectGoodsType;
	private OptionGroup selectPeriode;
	private DateField selectDate;
	private ComboBox   selectYear;
	private ComboBox selectMonth;
	private ComboBox selectWeek;
	private ComboBox selectContent;
	
	private GeneralFunction function;
	
	
	private BrowserWindowOpener opener;
	
	private ReportContentListener listener;
	public ReportConsumptionViewImpl(GeneralFunction function) {
		this.function=function;
	}
	@Override
	public void init() {
		buttonCancel=new Button("Batalkan");
		buttonCancel.addClickListener(this);
		buttonPrint=new Button("Cetak");
		buttonPrint.addClickListener(this);
		buttonPrint.setIcon(new ThemeResource("icons/image/icon-print.png"));

		buttonShow=new Button("Tampilkan Laporan");
		buttonShow.addClickListener(this);

		
		opener=new BrowserWindowOpener(ReportPrint.class){
			public void beforeClientResponse(boolean initial) {
				super.beforeClientResponse(initial);
				getState().target = "win" + UUID.randomUUID().toString();
				
			}
		};
		
		
		opener.setFeatures("height=200,width=400,resizable");
		// A button to open the printer-friendly page.
		opener.extend(buttonPrint);

		selectDate=new DateField("Pilih Tanggal Laporan");
		selectDate.setImmediate(true);
		selectDate.setValue(new Date());
		
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
		 selectWeek =new ComboBox("Minggu Ke");
		 selectWeek.setImmediate(true);
		 selectWeek.addItem(1);
		 selectWeek.addItem(2);
		 selectWeek.addItem(3);
		 selectWeek.addItem(4);
		 selectWeek.setItemCaption(1, "Minggu Ke-1");
		 selectWeek.setItemCaption(2, "Minggu Ke-2");
		 selectWeek.setItemCaption(3, "Minggu Ke-3");
		 selectWeek.setItemCaption(4, "Minggu Ke-4");
		 selectWeek.setValue(1);
		 
		 selectContent =new ComboBox("Pilih Tampilan");
		 selectContent.setImmediate(true);
		 selectContent.addItem(ReportContent.CHART);
		 selectContent.addItem(ReportContent.TABLE);
		 selectContent.addItem(ReportContent.TABLE_CHART);
		 selectContent.setItemCaption(ReportContent.CHART, "Tampilkan Chart");
		 selectContent.setItemCaption(ReportContent.TABLE, "Tampilkan Tabel");
		 selectContent.setItemCaption(ReportContent.TABLE_CHART, "Tampilkan Tabel dan Chart");
		 selectContent.setItemCaption(4, "Minggu Ke-4");
		 selectContent.setValue(ReportContent.TABLE);

		selectGoodsType=new OptionGroup("Tipe Laporan");
		selectGoodsType.addItem("obat");
		selectGoodsType.addItem("alkesbmhp");
		selectGoodsType.setImmediate(true);
		selectGoodsType.setValue("obat");
		
		selectGoodsType.setItemCaption("obat", "Laporan Pemakaian Obat");
		selectGoodsType.setItemCaption("alkesbmhp", "Laporan Pemakaian Alkes & BMHP");
		
		selectPeriode=new OptionGroup("Periode Laporan");
		selectPeriode.addItem("bulanan");
		selectPeriode.addItem("mingguan");
		selectPeriode.addItem("harian");
		selectPeriode.setImmediate(true);
		selectPeriode.addValueChangeListener(this);
		selectPeriode.setItemCaption("bulanan", "Laporan Bulanan");
		selectPeriode.setItemCaption("harian", "Laporan Harian");
		selectPeriode.setItemCaption("mingguan", "Laporan Mingguan");
		selectPeriode.setValue("bulanan");
		
		 selectGoodsType.addValueChangeListener(this);
		 selectPeriode.addValueChangeListener(this);
		 selectDate.addValueChangeListener(this);
		 selectYear.addValueChangeListener(this);
		 selectMonth.addValueChangeListener(this);
		 selectWeek.addValueChangeListener(this);
		 selectContent.addValueChangeListener(this);


		updateWindowOpener();
		changeViewMode(ViewMode.MONTHLY);
		construct();

	}

	@Override
	public void construct() {
		this.setSpacing(true);
		this.setMargin(true);
		GridLayout layout=new GridLayout(3, 1){
			{
				addComponent(buttonPrint, 0,0);
				addComponent(buttonShow, 1,0);
				addComponent(buttonCancel, 2, 0);
				setSpacing(true);
				setMargin(true);
			}
		};
		this.addComponents(selectContent, selectGoodsType, selectPeriode, selectDate, selectYear, selectMonth, selectWeek,layout);

	}
	@Override
	public void setListener(ReportContentListener listener) {
		this.listener=listener;
	}
	public BrowserWindowOpener getOpener() {
		return opener;
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		if(event.getProperty()==selectPeriode){
			String value=(String)selectPeriode.getValue();
			changeViewMode(getViewMode());
		}else if(event.getProperty()==selectContent){
			ReportContent value=(ReportContent)selectContent.getValue();
			if(!(value==ReportContent.TABLE)){
				buttonPrint.setEnabled(false);
			}else{
				buttonPrint.setEnabled(true);
			}
		}
		updateWindowOpener();
	}

	
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonCancel){
			listener.cancelClick(ReportType.CONSUMPTION);
		}if(event.getButton()==buttonShow){
			listener.showClick(ReportType.CONSUMPTION, getReportData());
		}
	}
	
	@Override
	public Button getPrintButton() {
		return buttonPrint;
	}
	
	//apakah yang pilih adalah bulanan
	private ViewMode getViewMode(){
		if(selectPeriode.getValue().equals("bulanan")){
			return ViewMode.MONTHLY;
		}else if(selectPeriode.getValue().equals("harian")){
			return ViewMode.DAILY;
		}else{
			return ViewMode.WEEKLY;
		}

	}

	public void changeViewMode(ViewMode viewMode) {
		if(viewMode==ViewMode.MONTHLY){
			selectMonth.setVisible(true);
			selectYear.setVisible(true);
			selectDate.setVisible(false);
			selectWeek.setVisible(false);
		}else if(viewMode==ViewMode.DAILY){
			selectMonth.setVisible(false);
			selectYear.setVisible(false);
			selectDate.setVisible(true);
			selectWeek.setVisible(false);
		}else{  //artinya mingguan
			selectMonth.setVisible(true);
			selectYear.setVisible(true);
			selectDate.setVisible(false);
			selectWeek.setVisible(true);			
		}
	}
	@Override
	public ReportData getReportData() {
		ReportData data=new ReportData(function);
		PeriodeType periodeType;
		if(getViewMode()==ViewMode.MONTHLY){
			periodeType=PeriodeType.BY_MONTH;
		}else if(getViewMode()==ViewMode.MONTHLY){
			periodeType=PeriodeType.BY_DAY;
		}else{
			periodeType=PeriodeType.BY_WEEK;			
		}
		data.setReportContent((ReportContent)selectContent.getValue());
		data.setSelectedWeek((Integer)selectWeek.getValue());
		data.setPeriodeType(periodeType);
		data.setSelectedDay(selectDate.getValue());
		data.setSelectedMonth((String) selectMonth.getValue());
		data.setSelectedYear((String) selectYear.getValue());
		data.setSelectedGoods((String)selectGoodsType.getValue());
		data.setType(ReportType.CONSUMPTION);
		return data;
	}
	public void updateWindowOpener(){
		listener.printClick(ReportType.CONSUMPTION, getReportData());
	}
}
