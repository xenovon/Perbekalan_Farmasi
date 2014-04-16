package com.binar.core.report.reportInterface.reportContent.reportProcurement;

import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;

import com.binar.core.report.reportInterface.ReportInterfaceView.ReportInterfaceListener;
import com.binar.core.report.reportInterface.reportContent.ReportContentView;
import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.core.report.reportInterface.reportContent.ReportData.ReportContent;
import com.binar.core.report.reportInterface.reportContent.ReportPrint;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportContentListener;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportType;
import com.binar.core.report.reportInterface.reportContent.ReportData.PeriodeType;
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

public class ReportProcurementViewImpl extends VerticalLayout implements ClickListener, ValueChangeListener, ReportContentView {

	/*
	 * (non-Javadoc)
	 * @see com.binar.core.report.reportInterface.reportContent.ReportContentView#init()
	 	Per bulan
	 	
	 	Obat dan alkes/bmhp
	 */
	private Button buttonCancel;
	private Button buttonPrint;
	private Button buttonShow;

	private OptionGroup selectGoodsType;
	private ComboBox  selectYear;
	private ComboBox selectMonth;
	private ComboBox selectContent;

	private GeneralFunction function;
	
	BrowserWindowOpener opener;
	
	private ReportContentListener listener;
	public ReportProcurementViewImpl(GeneralFunction function) {
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

		opener=new BrowserWindowOpener(ReportPrint.class);
		opener.setFeatures("height=200,width=400,resizable");
		// A button to open the printer-friendly page.
		opener.extend(buttonPrint);
		
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
		selectGoodsType.setItemCaption("obat", "Laporan Pengadaan Obat");
		selectGoodsType.setItemCaption("alkesbmhp", "Laporan Pengadaan Alkes & BMHP");
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

		 selectGoodsType.addValueChangeListener(this);
		 selectYear.addValueChangeListener(this);
		 selectMonth.addValueChangeListener(this);
		 selectContent.addValueChangeListener(this);


		 updateWindowOpener();
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
		this.addComponents(selectContent,selectGoodsType, selectYear, selectMonth, layout);
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
		if(event.getProperty()==selectContent){
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
			listener.cancelClick(ReportType.PROCUREMENT);
		}if(event.getButton()==buttonShow){
			listener.showClick(ReportType.PROCUREMENT, getReportData());

		}
	}
	public void updateWindowOpener(){
		listener.printClick(ReportType.PROCUREMENT, getReportData());
	}
	@Override
	public Button getPrintButton() {
		return buttonPrint;
	}
	
	//apakah yang pilih adalah bulanan
	private boolean isViewMonthMode(){
		return true;
	}
	public void changeViewMode(boolean viewByMonth) {
	}
	@Override
	public ReportData getReportData() {
		ReportData data=new ReportData(function);
		data.setReportContent((ReportContent)selectContent.getValue());

		data.setSelectedMonth((String)selectMonth.getValue());
		data.setSelectedYear((String)selectYear.getValue());
		data.setSelectedGoods((String)selectGoodsType.getValue());
		data.setType(ReportType.PROCUREMENT);
		return data;
	}
}
