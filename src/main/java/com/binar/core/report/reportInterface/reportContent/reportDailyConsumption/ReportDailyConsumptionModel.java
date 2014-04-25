package com.binar.core.report.reportInterface.reportContent.reportDailyConsumption;

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

public class ReportDailyConsumptionModel extends Label{
	private GeneralFunction function;
	/*
	 * Per Bulan
	 * Obat, alkes/bmhp
	 */
	private String goodsType; //
	private String periode; //
	private String tableCode; //
	private String city; //
	private String reportDate; // 
	private String userName; //
	private String userNum; //
	private String addStyle="";
	
	private ReportData data;
	private EbeanServer server;
	private User user; //untuk penanggung jawab yang tanda tangan
	private GetSetting setting;
	private DateManipulator date;
	
	//Variabel untuk ditampilkan di surat pesanan
		
	private String html="<html> <head> <title>Daftar Pengeluaran {{GoodsType}} Gudang Farmasi </title> <style type='text/css'>body{width:750px;font-family:arial}h1.title{display:block;margin:0 auto;font-size:24px;text-align:center}h2.address{display:block;margin:0 auto;font-size:16px;font-weight:normal;text-align:center}.center{padding-bottom:20px;margin-bottom:30px}.kepada{width:400px;margin-top:30px;line-height:1.5em}.PONumber{float:right;top:40px}table{width:100%;border:1px solid black;border-collapse:collapse}table tr td,table tr th{border:1px solid black;padding:2px;margin:0}.footer{float:right;margin-top:60px}.tapak-asma{text-align:center}.kepala{margin-bottom:100px} {{AddStyle}} </style> </head> <body> <div class='center'> <h1 class='title'>Daftar Pengeluaran {{GoodsType}} Gudang Farmasi</h1> <h2 class='address'> {{Periode}} </h2> </div> <table> <tr> <th rowspan='2'>No</th> <th rowspan='2'>Nama</th> <th rowspan='2'>Sat</th> <th colspan='31' style='text-align:center'>Tanggal</th> </tr> <tr> <th>1</th> <th>2</th> <th>3</th> <th>4</th> <th>5</th> <th>6</th> <th>7</th> <th>8</th> <th>9</th> <th>10</th> <th>11</th> <th>12</th> <th>13</th> <th>14</th> <th>15</th> <th>16</th> <th>17</th> <th>18</th> <th>19</th> <th>20</th> <th>21</th> <th>22</th> <th>23</th> <th>24</th> <th>25</th> <th>26</th> <th>27</th> <th>28</th> <th>29</th> <th>30</th> <th>31</th> </tr> {{TableCode}} </table> <div class='footer'> {{City}} , {{ReportDate}} <div class='tapak-asma'> <div class='kepala'>Petugas Gudang Farmasi </br>RSUD Ajibarang</div> <div>{{UserName}}</div> <div>NIP: {{UserNum}}</div> </div> </div> </body> </html>";

	public ReportDailyConsumptionModel(GeneralFunction function, ReportData data) {
		this.function=function;
		this.setContentMode(ContentMode.HTML);
		this.data=data;
		this.server=function.getServer();
		this.date=function.getDate();
		this.setting=function.getSetting();
		setContent();
		replaceText();
	}
	//untuk mengeset konten-konten yang ada di laporan
	private void setContent(){
		Map<Goods, List<Integer>> dataResult=null;
		if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_OBAT.toString())){
			goodsType="Obat";
			dataResult=getDailyConsumption(data.getDateMonth(), true);
			tableCode=generateTableCode(dataResult);
			
		}else if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_ALKES.toString())){
			goodsType="Alkes & BMHP";
			dataResult=getDailyConsumption(data.getDateMonth(), false);
			tableCode=generateTableCode(dataResult);
		}
		periode="Periode "+date.dateToText(data.getDateMonth());
		reportDate = date.dateToText(new Date(),true);
		city=setting.getSetting("rs_city").getSettingValue();
		user=function.getLogin().getUserLogin();
		
		userName=user.getName();
		userNum=user.getEmployeeNum();
		if(dataResult!=null){
			int columnCount=getDayOfTheMonth(data.getDateMonth());
			if(columnCount<31){
				if(columnCount==30){
					addStyle=addStyle+"table tr:nth-child(2) th:nth-child(31) {display: none;}";
				}else if(columnCount==29){
					addStyle=addStyle+"table tr:nth-child(2) th:nth-child(31) {display: none;}";
					addStyle=addStyle+"table tr:nth-child(2) th:nth-child(30) {display: none;}";					
				}else if(columnCount==28){
					addStyle=addStyle+"table tr:nth-child(2) th:nth-child(31) {display: none;}";
					addStyle=addStyle+"table tr:nth-child(2) th:nth-child(30) {display: none;}";
					addStyle=addStyle+"table tr:nth-child(2) th:nth-child(29) {display: none;}";						
				}
			}else{
				addStyle="";
			}
			
		}
		System.out.println("Add style + "+addStyle);	

	
		
	}
	private int getDayOfTheMonth(Date date){
		return new DateTime(date).dayOfMonth().getMaximumValue();
	}
	private void replaceText(){
		html=html.replace("{{GoodsType}}", goodsType);
		html=html.replace("{{Periode}}", periode);
		html=html.replace("{{TableCode}}", tableCode);
		html=html.replace("{{City}}", city);
		html=html.replace("{{ReportDate}}", reportDate);
		html=html.replace("{{UserName}}", userName);
		html=html.replace("{{UserNum}}", userNum);	
		html=html.replace("{{AddStyle}}", addStyle);
		
		this.setValue(html);
	}
	private String generateTableCode(Map<Goods, List<Integer>> data){
		String returnValue="";
		
		int i=0;
		if(data.size()==0){
			return "<tr><td style='text-align:center;font-style:italic;' colspan='34'>Data kosong</td></tr>";
		}
		for(Map.Entry<Goods, List<Integer>> entry:data.entrySet()){
			
			i++;
			returnValue=returnValue+"<tr>";
				returnValue=returnValue+"<td>"+i+"</td>";
				returnValue=returnValue+"<td>"+entry.getKey().getName()+"</td>";
				returnValue=returnValue+"<td>"+entry.getKey().getUnit()+"</td>";
				for(Integer quantity:entry.getValue()){
					returnValue=returnValue+"<td>"+quantity+"</td>";					
				}
			returnValue=returnValue+"</tr>";
			
		}
		
		
		return returnValue;
	}
	//untuk mendapatkan konsumsi barang dalam satu hari tiap bulannya
	//format data
	//Nama Obat    1 2 3 4 5 6 7 8 9 10  dst
	//Panadol 	   5 6  dst   -> 
	//Goods			List<Integer>
	private Map<Goods, List<Integer>> getDailyConsumption(Date periode, boolean isObat){
		System.out.println("Mendapatkan periode");
		
		//set periode (satu bulan
		DateTime startDate=new DateTime(periode);
			startDate=startDate.withTimeAtStartOfDay();
			startDate=startDate.withDayOfMonth(startDate.dayOfMonth().getMinimumValue());
		DateTime endDate=startDate.withHourOfDay(startDate.hourOfDay().getMaximumValue()).withMinuteOfHour(startDate.minuteOfHour().getMaximumValue());
			endDate=endDate.withDayOfMonth(startDate.dayOfMonth().getMaximumValue());
			
		List<String> goodsTypeList=new ArrayList<String>();
		if(isObat){
			goodsTypeList.add(EnumGoodsType.NARKOTIKA.toString());
			goodsTypeList.add(EnumGoodsType.PSIKOTROPIKA.toString());
			goodsTypeList.add(EnumGoodsType.OBAT.toString());
		}else{
			goodsTypeList.add(EnumGoodsType.ALAT_KESEHATAN.toString());
			goodsTypeList.add(EnumGoodsType.BMHP.toString());
		}
		//mendata daftar barang yang dikonsumsi pada bulan terpilih
		List<GoodsConsumption> consumptionOfMonth=server.find(GoodsConsumption.class).where().in("goods.type", goodsTypeList).
				between("consumptionDate", startDate.toDate(), endDate.toDate()).findList();
		System.out.println("Start Date : "+startDate);
		System.out.println("End Date : "+endDate);
		
		List<Goods> goodsList=new ArrayList<Goods>();
		for (GoodsConsumption consumption : consumptionOfMonth){
			if(!goodsList.contains(consumption.getGoods())){
				goodsList.add(consumption.getGoods());
			}	
		}
		
		//Memasukan data barang terpilih beserta konsumsi hariannya ke MAP
		Map<Goods, List<Integer>> returnValue=new HashMap<Goods, List<Integer>>();
		for(Goods goods:goodsList){
			DateTime currentDate=startDate;
			List<Integer> quantityList=new ArrayList<Integer>();
			while(currentDate.compareTo(endDate) < 0){
				int totalDailyConsumption=0;
				DateTime todayStart=currentDate.withHourOfDay(currentDate.hourOfDay().getMinimumValue());
				DateTime todayEnd=currentDate.withHourOfDay(currentDate.hourOfDay().getMaximumValue());
				List<GoodsConsumption> consumptions=server.find(GoodsConsumption.class).where().eq("goods", goods).
						where().between("consumptionDate", todayStart, todayEnd).findList();
				
				for(GoodsConsumption consumption:consumptions){
					totalDailyConsumption=totalDailyConsumption+consumption.getQuantity();
				}
				quantityList.add(totalDailyConsumption);
				currentDate=currentDate.plusDays(1);
			}
			
			returnValue.put(goods, quantityList);
		}
			
		return returnValue; 

	}
	
	

}
