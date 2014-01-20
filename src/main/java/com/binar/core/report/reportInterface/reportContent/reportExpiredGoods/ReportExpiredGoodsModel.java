package com.binar.core.report.reportInterface.reportContent.reportExpiredGoods;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.core.report.reportInterface.reportContent.ReportData.PeriodeType;
import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.Role;
import com.binar.entity.User;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.vaadin.data.Item;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class ReportExpiredGoodsModel extends Label{

	private GeneralFunction function;
	
	//	 	Per rentang waktu, dengan defaultnya satu tahun
	private String periode; //
	private String tableCode; //
	private String city; //
	private String reportDate; // 
	private String userName; //
	private String userNum; //
	private String quantity; //
	private String accepted;

	private ReportData data;
	private EbeanServer server;
	private User user; //untuk penanggung jawab yang tanda tangan
	private GetSetting setting;
	private DateManipulator date;
	
	//Variabel untuk ditampilkan di surat pesanan
		
	private String html="<html> <head> <title> Daftar Obat dan BMHP Kadaluarsa {{Accepted}} </title> <style type='text/css'>body{width:750px;font-family:arial}h1.title{display:block;margin:0 auto;font-size:24px;text-align:center}h2.address{display:block;margin:0 auto;font-size:16px;font-weight:normal;text-align:center}.center{padding-bottom:20px;margin-bottom:30px}.kepada{width:400px;margin-top:30px;line-height:1.5em}.PONumber{float:right;top:40px}table{width:100%;border:1px solid black;border-collapse:collapse}table tr td,table tr th{border:1px solid black;padding:2px;margin:0}.footer{float:right;margin-top:60px}.tapak-asma{text-align:center}.kepala{margin-bottom:100px}</style> </head> <body> <div class='center'> <h1 class='title'>Daftar Obat dan BMHP Kadaluarsa {{Accepted}}</h1> <h1 class='title'>Di Instalasi Farmasi RSUD Ajibarang</h1> <h2 class='address'> {{Periode}} </h2> </div> <table> <tr> <th>No</th> <th>Nama</th> <th>Satuan</th> <th>Jumlah</th> <th>Harga</th> <th>Total Harga</th> <th>Keterangan</th> </tr> {{TableCode}} </table> <div style='float:right;margin-top:30px'>Jumlah {{Quantity}}</div> <div class='footer'> {{City}} , {{ReportDate}} </br> <div class='tapak-asma'> <div class='kepala'>KA IFRS</br> RSUD Ajibarang <div>{{UserName}}</div> <div>NIP: {{UserNum}}</div> </div> </div> </body> </html>";

	public ReportExpiredGoodsModel(GeneralFunction function, ReportData data) {
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
		
		String year;
		DateTime startDate=new DateTime(data.getDateStart());
		DateTime endDate=new DateTime(data.getDateEnd());
		if(startDate.getYear()==endDate.getYear()){
			year=startDate.getYear()+"";
		}else{
			year=startDate.getYear()+"-"+endDate.getYear();
		}
		periode="Tahun "+year;
		
		reportDate = date.dateToText(new Date(),true);
		city=setting.getSetting("rs_city").getSettingValue();
		List<DeletedGoods> deleted;
		if(data.getAccepted().equals("diterima")){
			deleted=getDailyConsumption(startDate, endDate,true);
			tableCode=generateTableCode(deleted);
			quantity=generateQuantity(deleted);
			accepted="Disetujui";
		}else{
			deleted=getDailyConsumption(startDate, endDate,false);
			tableCode=generateTableCode(deleted);
			quantity=generateQuantity(deleted);
			accepted="Belum Disetujui";
		}
		try {
			Role role=server.find(Role.class, "IFRS");
			this.user=server.find(User.class).where().eq("role",role).eq("active", true).findList().get(0);
		} catch (Exception e) {
			this.user=function.getLogin().getUserLogin();
			e.printStackTrace();
		}
	
		userName=user.getName();
		userNum=user.getEmployeeNum();
		
	}
	private void replaceText(){
		html=html.replace("{{Periode}}", periode);
		html=html.replace("{{TableCode}}", tableCode);
		html=html.replace("{{City}}", city);
		html=html.replace("{{ReportDate}}", reportDate);
		html=html.replace("{{UserName}}", userName);
		html=html.replace("{{UserNum}}", userNum);		
		html=html.replace("{{Accepted}}", accepted);
		html=html.replace("{{Quantity}}", quantity);
		this.setValue(html);
	}
	private String generateQuantity(List<DeletedGoods> data){
		int x=0;
		for(DeletedGoods goods:data){
			x=x+goods.getQuantity();
		}
		return x+"";
	}
	private String generateTableCode(List<DeletedGoods> data){
		String returnValue="";
		
		int i=0;
		if(data.size()==0){
			return "<tr><td style='text-align:center;font-style:italic;' colspan='4'>Data kosong</td></tr>";
		}
		for(DeletedGoods datum:data){
			i++;
			returnValue=returnValue+"<tr>";
				returnValue=returnValue+"<td>"+i+"</td>";
				returnValue=returnValue+"<td>"+datum.getGoods().getName()+"</td>";
				returnValue=returnValue+"<td>"+datum.getGoods().getUnit()+"</td>";
				returnValue=returnValue+"<td>"+datum.getQuantity()+"</td>";
				returnValue=returnValue+"<td>"+datum.getPrice()+"</td>";
				returnValue=returnValue+"<td>"+(datum.getPrice()*datum.getQuantity())+"</td>";
				returnValue=returnValue+"<td style='max-width:220px;'>"+datum.getInformation()+"</td>";
			returnValue=returnValue+"</tr>";
			
		}
		
		
		return returnValue;
	}
	//untuk mendapatkan data barang yang dihapus
	private List<DeletedGoods> getDailyConsumption(DateTime startDate, DateTime endDate, boolean isAccepted){
		System.out.println("Mendapatkan periode");
		
		
		DateTime start=startDate.withHourOfDay(startDate.hourOfDay().getMinimumValue());
		DateTime end=endDate.withHourOfDay(startDate.hourOfDay().getMaximumValue());

		
		List<DeletedGoods> deletedList=server.find(DeletedGoods.class).where().eq("isAccepted",isAccepted).
				between("deletionDate", start.toDate(), end.toDate()).findList();
		System.out.println("Start Date : "+startDate);
		System.out.println("End Date : "+endDate);
		
		return deletedList; 

	}
	
	
}
