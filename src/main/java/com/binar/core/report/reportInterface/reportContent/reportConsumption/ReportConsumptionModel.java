package com.binar.core.report.reportInterface.reportContent.reportConsumption;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.core.report.reportInterface.reportContent.ReportData.PeriodeType;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.User;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class ReportConsumptionModel extends Label{

	private GeneralFunction function;
	private ReportData data;
	private EbeanServer server;
	private User user; //untuk penanggung jawab yang tanda tangan
	private GetSetting setting;
	private DateManipulator date;
	
	//Variabel untuk ditampilkan di surat pesanan
	
	private String goodsType; //ok
	private String periode; //ok
	private String tableCode; //ok
	private String city; //ok
	private String reportDate; //ok
	private String userName; //ok
	private String userNum; //ok
	private String timecycle; //ok
	
	private String html="";
	
	public ReportConsumptionModel(GeneralFunction function, ReportData data) {
		this.function=function;
		this.setContentMode(ContentMode.HTML);
		this.data=data;
		this.server=function.getServer();
		this.date=function.getDate();
		this.setting=function.getSetting();
		this.user=function.getLogin().getUserLogin();
		
		setContent();
		replaceText();
	}
	//untuk mengeset konten-konten yang ada di laporan
	private void setContent(){
		if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_OBAT.toString())){
			goodsType="Obat";
			if(data.getPeriodeType()==PeriodeType.BY_MONTH){
				tableCode=generateTableCode(getConsumptionDataMonthly(data.getDateMonth(), true));
			}else{
				tableCode=generateTableCode(getConsumptionDataDaily(data.getDateMonth(), true));				
			}
			
		}else if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_ALKES.toString())){
			goodsType="Alkes & BMHP";
			if(data.getPeriodeType()==PeriodeType.BY_MONTH){
				tableCode=generateTableCode(getConsumptionDataMonthly(data.getDateMonth(), false));
			}else{
				tableCode=generateTableCode(getConsumptionDataDaily(data.getDateMonth(), false));				
			}
		}
		if(data.getPeriodeType()==PeriodeType.BY_MONTH){
			periode="Periode "+date.dateToText(data.getDateMonth());
			timecycle="Bulanan";
		}else if(data.getPeriodeType()==PeriodeType.BY_DAY){
			timecycle="Harian";
			periode="Per "+date.dateToText(data.getDate(), true);
		}
		reportDate = date.dateToText(new Date(),true);
		city=setting.getSetting("rs_city").getSettingValue();
		user=function.getLogin().getUserLogin();
		
		userName=user.getName();
		userNum=user.getEmployeeNum();
		
	}
	private void replaceText(){
		html=html.replace("{{GoodsType}}", goodsType);
		html=html.replace("{{Periode}}", periode);
		html=html.replace("{{Timecycle}}", timecycle);
		html=html.replace("{{TableCode}}", tableCode);
		html=html.replace("{{Timecycle}}", timecycle);
		html=html.replace("{{City}}", city);
		html=html.replace("{{ReportDate}}", reportDate);
		html=html.replace("{{UserName}}", userName);
		html=html.replace("{{UserNum}}", userNum);		
		
		this.setValue(html);
	}
	private String generateTableCode(Map<Goods, Integer> data){
		String returnValue="";
		
		int i=0;
		for(Map.Entry<Goods, Integer> entry:data.entrySet()){
			i++;
			returnValue=returnValue+"<tr>";
				returnValue=returnValue+"<td>"+i+"</td>";
				returnValue=returnValue+"<td>"+entry.getKey().getName()+"</td>";
				returnValue=returnValue+"<td>"+entry.getKey().getUnit()+"</td>";
				returnValue=returnValue+"<td>"+entry.getValue()+"</td>";
			returnValue=returnValue+"</tr>";
			
		}
		
		
		return returnValue;
	}
	//untuk mendapatkan konsumsi barang dalam hari tertentu
	private Map<Goods, Integer> getConsumptionDataDaily(Date periode, boolean isObat){
		System.out.println("Mendapatkan periode");
		
		DateTime startDate=new DateTime(periode);
			startDate=startDate.withHourOfDay(startDate.hourOfDay().getMinimumValue());
		DateTime endDate=startDate.withHourOfDay(startDate.hourOfDay().getMaximumValue());

		List<String> goodsTypeList=new ArrayList<String>();
		if(isObat){
			goodsTypeList.add(EnumGoodsType.NARKOTIKA.toString());
			goodsTypeList.add(EnumGoodsType.PSIKOTROPIKA.toString());
			goodsTypeList.add(EnumGoodsType.OBAT.toString());
		}else{
			goodsTypeList.add(EnumGoodsType.ALAT_KESEHATAN.toString());
			goodsTypeList.add(EnumGoodsType.BMHP.toString());
		}
		
		List<GoodsConsumption> consumptionOfMonth=server.find(GoodsConsumption.class).where().in("goods.type", goodsTypeList).
				between("consumptionDate", startDate.toDate(), endDate.toDate()).findList();
		System.out.println("Start Date : "+startDate);
		System.out.println("End Date : "+endDate);
		
		Map<Goods, Integer> returnValue=new HashMap<Goods, Integer>();		
		for (GoodsConsumption consumption : consumptionOfMonth){
			if(returnValue.containsKey(consumption.getGoods())){
				int quantity=returnValue.get(consumption.getGoods());
				int quantityNow=quantity+consumption.getQuantity();
				
				returnValue.remove(consumption.getGoods());
				returnValue.put(consumption.getGoods(), quantityNow);
			}else{
				returnValue.put(consumption.getGoods(), consumption.getQuantity());
			}
		}
		return returnValue; 

	}
	
	//mendapatkan konsumsi barang pada tanggal tertentu
	private Map<Goods, Integer> getConsumptionDataMonthly(Date periode, boolean isObat){
		System.out.println("Mendapatkan periode");
		
		DateTime startDate=new DateTime(periode);
			startDate=startDate.withDayOfMonth(startDate.dayOfMonth().getMinimumValue());
		DateTime endDate=startDate.withDayOfMonth(startDate.dayOfMonth().getMaximumValue());
		
		List<String> goodsTypeList=new ArrayList<String>();
		if(isObat){
			goodsTypeList.add(EnumGoodsType.NARKOTIKA.toString());
			goodsTypeList.add(EnumGoodsType.PSIKOTROPIKA.toString());
			goodsTypeList.add(EnumGoodsType.OBAT.toString());
		}else{
			goodsTypeList.add(EnumGoodsType.ALAT_KESEHATAN.toString());
			goodsTypeList.add(EnumGoodsType.BMHP.toString());
		}
		
		
		List<GoodsConsumption> consumptionOfMonth=server.find(GoodsConsumption.class).where().in("goods.type", goodsTypeList).
				between("consumptionDate", startDate.toDate(), endDate.toDate()).findList();
		System.out.println("Start Date : "+startDate);
		System.out.println("End Date : "+endDate);
		
		Map<Goods, Integer> returnValue=new HashMap<Goods, Integer>();		
		for (GoodsConsumption consumption : consumptionOfMonth){
			if(returnValue.containsKey(consumption.getGoods())){
				int quantity=returnValue.get(consumption.getGoods());
				int quantityNow=quantity+consumption.getQuantity();
				
				returnValue.remove(consumption.getGoods());
				returnValue.put(consumption.getGoods(), quantityNow);
			}else{
				returnValue.put(consumption.getGoods(), consumption.getQuantity());
			}
		}
		return returnValue; 
	}
	
	
}
