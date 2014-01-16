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

	public enum PeriodeType{
		BY_MONTH, BY_DAY, BY_RANGE
	}
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
	public Date[] getDateRange() {
		String[] range=dateRange.split("--");
		Date[] date=new Date[2];
		try {
			date[0] = format.parse(range[0]);
			date[1] = format.parse(range[1]);
			
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
		this.selectedGoods = selectedGoods;
	}

	public void setSelectedMonth(String selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public void setSelectedYear(String selectedYear) {
		this.selectedYear = selectedYear;
	}

	public void setSelectedDay(Date selectedDay) {
		this.selectedDay = selectedDay;
	}
	public void setPeriodeType(PeriodeType periodeType) {
		this.periodeType = periodeType;
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

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	public void setAccepted(String accepted) {
		this.accepted = accepted;
	}
	public String getAccepted() {
		return accepted;
	}
	
	
}
