package com.binar.core.report.reportInterface.reportContent;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportType;
import com.binar.core.report.reportInterface.reportContent.ReportData.PeriodeType;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.data.Item;
import com.vaadin.server.VaadinRequest;

//untuk menampung dan menggenrate parameter untuk report
public class ReportParameter {
	/*
	 * Data yang dibutuhin
	 * Untuk jenis obat
	 *   nama parmeter: type;
	 *   value : obat, bmhpalkes
	 * Untuk Jenis laporan
	 *   nama parameter : reporttype
	 *   value : yang ada di enum ReportType
	 * Tipe Periode 
	 *   nama : timeperiode
	 *   value, sesuai yang di enum  PerideType
	 * Rentang Tanggal 
	 *  nama parameter : daterange 
	 *  value : dd-MM-yyyy--dd-MM-yyyy
	 * Tanggal 
	 * nama parameter : date
	 * value : dd-MM-yyyy
	 * 
	 * Peride Per bulan
	 * nama parameter yearmonth
	 * value : bulan-tahun
	 * 
	 * accepted
	 * nama parameter : accepted
	 * value : 	diterima, belumditerima	
	 * 
	 */

	//daftar parameter
	public final  String GOODS_TYPE="goodstype";  //obat, bmhpalkes
	public final  String REPORT_TYPE="type";  // 
	public final  String PERIODE_TYPE="periode"; 
	public final  String DATE_RANGE="daterange";
	public final  String DATE="date";
	public final  String MONTH_PERIODE="yearmonth";
	public final  String ACCEPTANCE="acceptance";
	
	public Map<String, String> generateParameter(ReportType reportType, ReportData reportData){
		Map<String, String> parameter;
		
		
		switch (reportType) {
			case CONSUMPTION: return processConsumption(reportType, reportData);
			case DAILY_CONSUMPTION: return processDailyConsumption(reportType, reportData);
			case EXPIRED_GOODS: return processExpiredGoods(reportType, reportData) ;
			case PROCUREMENT: return processProcurement(reportType, reportData);
			case RECEIPT: return processReceipt(reportType, reportData);
			case REQUIREMENT:return processRequirement(reportType, reportData);
			case STOCK : return processStock(reportType, reportData);
		default:
			break;
		}
		return null;
		
	}
	
	public ReportData generateParameter(VaadinRequest request){
		String reportType=request.getParameter(REPORT_TYPE);
		if(reportType.equals(ReportType.CONSUMPTION.toString())){
			return processConsumption(request);
		}else if(reportType.equals(ReportType.DAILY_CONSUMPTION.toString())){
			return processDailyConsumption(request);
		}else if(reportType.equals(ReportType.EXPIRED_GOODS.toString())){
			return processExpiredGoods(request);
		}else if(reportType.equals(ReportType.PROCUREMENT.toString())){
			return processProcurement(request);
		}else if(reportType.equals(ReportType.RECEIPT.toString())){
			return processReceipt(request);
		}else if(reportType.equals(ReportType.REQUIREMENT.toString())){
			return processRequirement(request);
		}else if(reportType.equals(ReportType.STOCK.toString())){
			return processStock(request);
		}else{
			return null;
		}
	}
	
	GeneralFunction function;
	SimpleDateFormat format;
	public ReportParameter(GeneralFunction function) {
		this.function=function;
		format=new SimpleDateFormat("dd-MM-yyyy");
	}
	private Map<String, String> processConsumption(ReportType reportType, ReportData reportData){
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put(REPORT_TYPE, reportType.toString());
		parameter.put(PERIODE_TYPE, reportData.getPeriodeType().toString());
		parameter.put(GOODS_TYPE, reportData.getSelectedGoods());
		parameter.put(MONTH_PERIODE, reportData.getSelectedMonth()+"-"+reportData.getSelectedYear());
		parameter.put(DATE, format.format(reportData.getSelectedDay()));
		return parameter;
	}
	private ReportData processConsumption(VaadinRequest request){
		try {
			ReportData data=new ReportData(function);
			data.setType(ReportType.valueOf(request.getParameter(REPORT_TYPE)));
			data.setPeriodeType(PeriodeType.valueOf(request.getParameter(PERIODE_TYPE)));
			data.setSelectedGoods(request.getParameter(GOODS_TYPE));
			data.setDate(request.getParameter(DATE));
			data.setDateMonth(request.getParameter(MONTH_PERIODE));
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private Map<String, String> processDailyConsumption(ReportType reportType, ReportData reportData){
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put(REPORT_TYPE, reportType.toString());
		parameter.put(GOODS_TYPE, reportData.getSelectedGoods());
		parameter.put(MONTH_PERIODE, reportData.getSelectedMonth()+"-"+reportData.getSelectedYear());
		
		return parameter;
	}
	private ReportData processDailyConsumption(VaadinRequest request){
		ReportData data=new ReportData(function);
		
		try {
			data.setType(ReportType.valueOf(request.getParameter(REPORT_TYPE)));
			data.setSelectedGoods(request.getParameter(GOODS_TYPE));
			data.setDateMonth(request.getParameter(MONTH_PERIODE));
			
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
			
	}
	 
	private Map<String, String> processExpiredGoods(ReportType reportType, ReportData reportData){
		
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put(REPORT_TYPE, reportType.toString());
		parameter.put(ACCEPTANCE, reportData.getAccepted());
		System.out.println("reportData.getDateStart() "+reportData.getDateStart().toString());
		System.out.println("reportData.getDateEnd() "+reportData.getDateEnd().toString());
		parameter.put(DATE_RANGE, format.format(reportData.getDateStart())+"--"+format.format(reportData.getDateEnd()));
		return parameter;
	}
	private ReportData processExpiredGoods(VaadinRequest request){
		ReportData data=new ReportData(function);
		try {
			data.setType(ReportType.valueOf(request.getParameter(REPORT_TYPE)));
			data.setAccepted(request.getParameter(ACCEPTANCE));
			data.setDateRange(request.getParameter(DATE_RANGE));
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	private Map<String, String> processProcurement(ReportType reportType, ReportData reportData){
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put(REPORT_TYPE, reportType.toString());
		parameter.put(GOODS_TYPE, reportData.getSelectedGoods());
		parameter.put(MONTH_PERIODE, reportData.getSelectedMonth()+"-"+reportData.getSelectedYear());
		return parameter;

	}
	private ReportData processProcurement(VaadinRequest request){
		ReportData data=new ReportData(function);
		try {
			data.setType(ReportType.valueOf(request.getParameter(REPORT_TYPE)));
			data.setSelectedGoods(request.getParameter(GOODS_TYPE));
			data.setDateMonth(request.getParameter(MONTH_PERIODE));
			
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Map<String, String> processReceipt(ReportType reportType, ReportData reportData){
		return processConsumption(reportType, reportData);
	}
	private ReportData processReceipt(VaadinRequest request){
		return processConsumption(request);
	}

	private Map<String, String> processRequirement(ReportType reportType, ReportData reportData){
		return processProcurement(reportType, reportData);
	}
	private ReportData processRequirement(VaadinRequest request){
		return processProcurement(request);
	}

	private Map<String, String> processStock(ReportType reportType, ReportData reportData){
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put(REPORT_TYPE, reportType.toString());
		parameter.put(GOODS_TYPE, reportData.getSelectedGoods());
		parameter.put(DATE, format.format(reportData.getSelectedDay()));
		return parameter;
	}
	private ReportData processStock(VaadinRequest request){
		ReportData data=new ReportData(function);
		try {
			data.setType(ReportType.valueOf(request.getParameter(REPORT_TYPE)));
			data.setSelectedGoods(request.getParameter(GOODS_TYPE));
			data.setDate(request.getParameter(DATE));
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	
	/*
	 * Data yang dibutuhin
	 * Untuk jenis obat
	 *   nama parmeter: type;
	 *   value : obat, bmhp/alkes
	 * Untuk Jenis laporan
	 *   nama parameter : reporttype
	 *   value : yang ada di enum ReportType
	 * Tipe Periode 
	 *   nama : timeperiode
	 *   value, sesuai yang di enum  PerideType
	 * Rentang Tanggal 
	 *  nama parameter : daterange 
	 *  value : dd-MM-yyyy.dd-MM-yyyy
	 * Tanggal 
	 * nama parameter : date
	 * value : dd-MM-yyyy
	 * 
	 * Peride Per bulan
	 * nama parameter yearmonth
	 * value : bulan-tahun
	 * 
	 */

}
