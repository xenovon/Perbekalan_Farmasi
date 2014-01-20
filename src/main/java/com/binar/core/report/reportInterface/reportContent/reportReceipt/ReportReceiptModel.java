package com.binar.core.report.reportInterface.reportContent.reportReceipt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.core.report.reportInterface.reportContent.ReportContentView.ReportType;
import com.binar.core.report.reportInterface.reportContent.ReportData.PeriodeType;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.GoodsReception;
import com.binar.entity.Supplier;
import com.binar.entity.User;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class ReportReceiptModel extends Label {

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
	
	private String html="<html> <head> <title> Laporan Penerimaan {{Timecycle}} </title> <style type='text/css'>body{width:750px;font-family:arial}h1.title{display:block;margin:0 auto;font-size:24px;text-align:center}h2.address{display:block;margin:0 auto;font-size:16px;font-weight:normal;text-align:center}.center{padding-bottom:20px;margin-bottom:30px}.kepada{width:400px;margin-top:30px;line-height:1.5em}.PONumber{float:right;top:40px}table{width:100%;border:1px solid black;border-collapse:collapse}table tr td,table tr th{border:1px solid black;padding:2px;margin:0}.footer{float:right;margin-top:60px}.tapak-asma{text-align:center}.kepala{margin-bottom:100px}</style> </head> <body> <div class='center'> <h1 class='title'>Laporan Penerimaan {{GoodsType}}</h1> <h2 class='address'> {{Periode}} </h2> </div> <table> <tr> <th>No</th> <th>Nama</th> <th>Sat</th> <th>PBF</th> <th>Jumlah</th> <th>Faktur</th> <th>Harga</th> <th>Ket</th> </tr> {{TableCode}} </table> <div class='footer'> {{City}} , {{ReportDate}} <div class='tapak-asma'> <div class='kepala'>Petugas Gudang Farmasi </br>RSUD Ajibarang</div> <div>{{UserName}}</div> <div>NIP: {{UserNum}}</div> </div> </div> </body> </html>";
	
	public ReportReceiptModel(GeneralFunction function, ReportData data) {
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
				tableCode=generateTableCode(getReception(data.getDateMonth(), false, true));
			}else{
				tableCode=generateTableCode(getReception(data.getDate(), true, true));
			}
			
		}else if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_ALKES.toString())){
			goodsType="Alkes & BMHP";
			if(data.getPeriodeType()==PeriodeType.BY_MONTH){
				tableCode=generateTableCode(getReception(data.getDateMonth(), false, false));
			}else{
				tableCode=generateTableCode(getReception(data.getDateMonth(), true, false));
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
		html=html.replace("{{TableCode}}", tableCode);
		html=html.replace("{{Timecycle}}", timecycle);
		html=html.replace("{{City}}", city);
		html=html.replace("{{ReportDate}}", reportDate);
		html=html.replace("{{UserName}}", userName);
		html=html.replace("{{UserNum}}", userNum);		
		
		this.setValue(html);
	}
	private String generateTableCode(List<GoodsReception> data){
		String returnValue="";
		
		int i=0;
		if(data.size()==0){
			return "<tr><td style='text-align:center;font-style:italic;' colspan='4'>Data kosong</td></tr>";
		}
		for(GoodsReception reception:data){
			i++;
			Goods goods=reception.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getGoods();
			Supplier supplier=reception.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getSupplier();
			returnValue=returnValue+"<tr>";
				returnValue=returnValue+"<td>"+i+"</td>";
				returnValue=returnValue+"<td>"+goods.getName()+"</td>";
				returnValue=returnValue+"<td>"+goods.getUnit()+"</td>";
				returnValue=returnValue+"<td>"+supplier.getSupplierName()+"</td>";
				returnValue=returnValue+"<td>"+supplier.getSupplierName()+"</td>";
				returnValue=returnValue+"<td>"+reception.getQuantityReceived()+"</td>";
				returnValue=returnValue+"<td>"+reception.getInvoiceItem().getInvoice().getInvoiceNumber()+"</td>";
				returnValue=returnValue+"<td>"+reception.getInvoiceItem().getPricePPN()+"</td>";
				returnValue=returnValue+"<td style='max-size:220px;'>"+reception.getInformation()+"</td>";
				
			returnValue=returnValue+"</tr>";
			
		}
		
		
		return returnValue;
	}
	//untuk mendapatkan konsumsi barang dalam hari tertentu
	private List<GoodsReception> getReception(Date periode, boolean isDaily, boolean isObat){
		System.out.println("Mendapatkan periode");
		
		DateTime startDate;
		DateTime endDate;
		//tentukan rentang tanggal
		if(isDaily){
			startDate=new DateTime(periode);
				startDate=startDate.withHourOfDay(startDate.hourOfDay().getMinimumValue());
			endDate=startDate.withHourOfDay(startDate.hourOfDay().getMaximumValue());
		}else{
			startDate=new DateTime(periode);
				startDate=startDate.withDayOfMonth(startDate.dayOfMonth().getMinimumValue());
			endDate=startDate.withDayOfMonth(startDate.dayOfMonth().getMaximumValue());
		}
		List<String> goodsTypeList=new ArrayList<String>();
		if(isObat){
			goodsTypeList.add(EnumGoodsType.NARKOTIKA.toString());
			goodsTypeList.add(EnumGoodsType.PSIKOTROPIKA.toString());
			goodsTypeList.add(EnumGoodsType.OBAT.toString());
		}else{
			goodsTypeList.add(EnumGoodsType.ALAT_KESEHATAN.toString());
			goodsTypeList.add(EnumGoodsType.BMHP.toString());
		}
		
		List<GoodsReception> consumptionOfMonth=server.find(GoodsReception.class).where().
				in("invoiceItem.purchaseOrderItem.supplierGoods.goods.type", goodsTypeList).
				between("date", startDate.toDate(), endDate.toDate()).order().desc("date").findList();
		
		System.out.println("Start Date : "+startDate);
		System.out.println("End Date : "+endDate);
		
		return consumptionOfMonth; 

	}
	
	
	
	
}

