package com.binar.core.report.reportInterface.reportContent.reportStock;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.binar.core.report.reportInterface.ReportInterfaceView.ReportInterfaceListener;
import com.binar.core.report.reportInterface.reportContent.ReportContentView;
import com.binar.core.report.reportInterface.reportContent.ReportData;
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
	
	private BrowserWindowOpener opener;
	
	
	private ReportContentListener listener;
	public ReportStockViewImpl(GeneralFunction function) {
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
		selectDate.setValue(new Date());
		List<String> yearList=function.getListFactory().createYearList(8, 0, false);
		List<String> monthList=function.getListFactory().createMonthList();
		
		

		selectGoodsType=new OptionGroup("Tipe Laporan");
		Item itemType1=selectGoodsType.addItem("obat");
		Item itemType2=selectGoodsType.addItem("alkesbmhp");
		selectGoodsType.setImmediate(true);
		selectGoodsType.setValue("obat");
		
		selectGoodsType.setItemCaption("obat", "Laporan Stok Opname Obat");
		selectGoodsType.setItemCaption("alkesbmhp", "Laporan Stok Opname Alkes & BMHP");
		
		selectDate.addValueChangeListener(this);
		selectGoodsType.addValueChangeListener(this);
		updateWindowOpener();
		construct();

	}

	private void updateWindowOpener() {
		listener.printClick(ReportType.STOCK, getReportData());		
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
		this.addComponents(selectGoodsType, selectDate, layout);
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
		updateWindowOpener();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonCancel){
			listener.cancelClick(ReportType.STOCK);
		}if(event.getButton()==buttonPrint){
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
