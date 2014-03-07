package com.binar.core.report.reportInterface.reportContent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportType;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;

public class ReportData {

	/*
	 * Menampung data parameter laporan
	 */
	public enum PeriodeType{
		BY_MONTH, BY_DAY, BY_RANGE
	}
	private boolean withPPN;
	public static final String SELECT_GOODS_OBAT="obat";
	public static final String SELECT_GOODS_ALKES="alkesbmhp";
	
	private ReportType type;
	
	private String selectedGoods;
	private String selectedMonth;
	private String selectedYear;
	private Date selectedDay;
	private Date dateStart;
	private Date dateEnd;
	private String accepted;
	private PeriodeType periodeType;
	private GeneralFunction function;
	
	//untuk dikirim ke UI lain
	private String dateRange; //dd-MM-yyyy--dd-MM-yyyy
	private String dateMonth; //month-year 
	private String date;  //dd-MM-yyyy
	
	SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
	
	public void setDate(String date) {
		this.date = date;
	}
	public void setDateMonth(String dateMonth) {
		this.dateMonth = dateMonth;
	}
	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
		Date[] date=getDateRange();
		setDateStart(date[0]);
		setDateEnd(date[1]);
	}
	public Date getDate() {
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}
	public Date getDateMonth() {
		DateManipulator date=function.getDate();
		DateTime dateTime=date.parseDateMonth(dateMonth);
		if(dateTime!=null){
			return dateTime.toDate();
		}else{
			return new Date();
		}
	}
	private Date[] getDateRange() {
		String[] range=dateRange.split("--");
		Date[] date=new Date[2];
		try {
			date[0] = format.parse(range[0]);
			date[1] = format.parse(range[1]);
			System.out.println("Report Data date range start "+date[0].toString());
			System.out.println("Report Data date range end "+date[1].toString());
			return date;
		} catch (Exception e) {
			return null;
		}
	}
	
	public ReportData(GeneralFunction function) {
		this.function=function;
	}
	
	public ReportType getType() {
		return type;
	}

	public void setType(ReportType type) {
		this.type = type;
	}

	public void setSelectedGoods(String selectedGoods) {
		if(selectedGoods==null){
			this.selectedGoods="";
		}else{
			this.selectedGoods = selectedGoods;			
		}
	}

	public void setSelectedMonth(String selectedMonth) {
		if(selectedMonth==null){
			this.selectedMonth="";
		}else{
			this.selectedMonth = selectedMonth;			
		}
	}

	public void setSelectedYear(String selectedYear) {
		if(selectedYear==null){
			this.selectedYear="";
		}else{
			this.selectedYear = selectedYear;			
		}
		
	}

	public void setSelectedDay(Date selectedDay) {
		if(selectedDay==null){
			this.selectedDay=new Date();
		}else{
			this.selectedDay = selectedDay;			
		}
	}
	public void setPeriodeType(PeriodeType periodeType) {
		if(periodeType==null){
			this.periodeType= PeriodeType.BY_MONTH;
		}else{
			this.periodeType = periodeType;			
		}
	}
	public void setFunction(GeneralFunction function) {
		this.function = function;
	}

	public String getSelectedGoods() {
		return selectedGoods;
	}

	public String getSelectedMonth() {
		return selectedMonth;
	}

	public String getSelectedYear() {
		return selectedYear;
	}

	public Date getSelectedDay() {
		return selectedDay;
	}

	public PeriodeType getPeriodeType() {
		return periodeType;
	}

	public GeneralFunction getFunction() {
		return function;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStartInput) {
		if(dateStartInput==null){
			this.dateStart =  new Date();
		}else{
			this.dateStart = dateStartInput;			
		}
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEndInput) {
		if(dateEndInput==null){
			this.dateEnd=new Date();
		}else{
			this.dateEnd = dateEndInput;			
		}
	}
	public void setAccepted(String accepted) {
		if(accepted==null){
			this.accepted="";
		}else{
			this.accepted = accepted;			
		}
	}
	public String getAccepted() {
		return accepted;
	}
	public void setWithPPN(boolean withPPN) {
		this.withPPN = withPPN;
	}
	public boolean isWithPPN() {
		return withPPN;
	}
	
	
}
