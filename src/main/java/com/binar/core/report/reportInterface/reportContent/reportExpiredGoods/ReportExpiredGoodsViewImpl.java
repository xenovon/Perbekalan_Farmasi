package com.binar.core.report.reportInterface.reportContent.reportExpiredGoods;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

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

public class ReportExpiredGoodsViewImpl extends VerticalLayout implements ClickListener, ValueChangeListener, ReportContentView {

	/*
	 * (non-Javadoc)
	 * @see com.binar.core.report.reportInterface.reportContent.ReportContentView#init()
	 	Per rentang waktu, dengan defaultnya satu tahun
	 	
	 	Milih barang yang belum kadaluarsa, dan yang udah kadaluarsa
	 */
	private Button buttonCancel;
	private Button buttonPrint;
	
	private OptionGroup selectAcceptance; //memilih apakah sudah disetujui atau belum
	private DateField selectStartDate;
	private DateField   selectEndDate;
	
	private GeneralFunction function;
	
	private BrowserWindowOpener opener;
	private ReportContentListener listener;
	public  ReportExpiredGoodsViewImpl(GeneralFunction function) {
		this.function=function;
	} 
	@Override
	public void init() {
		buttonCancel=new Button("Batalkan");
		buttonCancel.addClickListener(this);
		buttonPrint.setIcon(new ThemeResource("icons/image/icon-print.png"));
		
		buttonPrint=new Button("Cetak");
		buttonPrint.addClickListener(this);

		opener=new BrowserWindowOpener(ReportPrint.class);
		opener.setFeatures("height=200,width=400,resizable");
		// A button to open the printer-friendly page.
		opener.extend(buttonPrint);

		DateTime now=DateTime.now();
		Date beginYear=now.withDayOfYear(now.dayOfYear().getMinimumValue()).withHourOfDay(now.hourOfDay().getMinimumValue()).toDate();
		Date endYear=now.withDayOfYear(now.dayOfYear().getMaximumValue()).withHourOfDay(now.hourOfDay().getMaximumValue()).toDate();

		selectStartDate=new DateField("Dari Tanggal");
		selectStartDate.setImmediate(true);
		selectStartDate.setWidth(function.FORM_WIDTH);
		selectStartDate.setValue(beginYear);
		
		selectEndDate=new DateField("Hingga Tanggal");
		selectEndDate.setImmediate(true);
		selectEndDate.setWidth(function.FORM_WIDTH);
		selectEndDate.setValue(endYear);
				
		selectAcceptance=new OptionGroup("Persetujuan");
		Item itemType1=selectAcceptance.addItem("diterima");
		Item itemType2=selectAcceptance.addItem("belumditerima");
		selectAcceptance.setImmediate(true);
		selectAcceptance.setValue("diterima");
		
		selectAcceptance.setItemCaption("diterima", "Barang Kadaluarsa Disetujui");

		selectAcceptance.setItemCaption("belumditerima", "Barang Kadaluarsa Belum Disetujui");
		selectAcceptance.addValueChangeListener(this);
		selectStartDate.addValueChangeListener(this);
		selectEndDate.addValueChangeListener(this);
		
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
		this.addComponents(selectAcceptance, selectStartDate, selectEndDate, layout);
	}
	@Override
	public void setListener(ReportContentListener listener) {
		this.listener=listener;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		updateWindowOpener();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton()==buttonCancel){
			listener.cancelClick(ReportType.EXPIRED_GOODS);
		}if(event.getButton()==buttonPrint){
//			listener.printClick(ReportType.EXPIRED_GOODS, getReportData());
		}
	}
	public void updateWindowOpener(){
		listener.printClick(ReportType.EXPIRED_GOODS, getReportData());

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
		data.setAccepted((String) selectAcceptance.getValue());
		data.setDateEnd(selectEndDate.getValue());
		data.setDateStart(selectStartDate.getValue());
		data.setType(ReportType.EXPIRED_GOODS);
		
		return data;
	}
}