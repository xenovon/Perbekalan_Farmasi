package com.binar.core.report.reportInterface.reportContent.reportStock;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.GoodsReception;
import com.binar.entity.ReqPlanning;
import com.binar.entity.SupplierGoods;
import com.binar.entity.User;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class ReportStockModel extends Label {
 
	private GeneralFunction function;
	private String periode; //
	private String tableCode; //
	private String city; //
	private String reportDate; // 
	private String userName; //
	private String userNum; //
	private String goodsType;
	
	private ReportData data;
	private EbeanServer server;
	private User user; //untuk penanggung jawab yang tanda tangan
	private GetSetting setting;
	private DateManipulator date;
	private TextManipulator text;
	
	//Variabel untuk ditampilkan di surat pesanan
		
	private String html="<html><head><title>Laporan Stok Opname {{GoodsType}} </title> <style type='text/css'>body{width:750px;font-family:arial}h1.title{display:block;margin:0 auto;font-size:24px;text-align:center}h2.address{display:block;margin:0 auto;font-size:16px;font-weight:normal;text-align:center}.center{padding-bottom:20px;margin-bottom:30px}.kepada{width:400px;margin-top:30px;line-height:1.5em}.PONumber{float:right;top:40px}table{width:100%;border:1px solid black;border-collapse:collapse}table tr td,table tr th{border:1px solid black;padding:2px;margin:0}.footer{float:right;margin-top:60px}.tapak-asma{text-align:center}.kepala{margin-bottom:100px}.total{float:right}.container{width:100%;margin-top:10px;}</style> </head> <body> <div class='center'> <h1 class='title'>Laporan Stok Opname {{GoodsType}}</h1> <h2 class='address'> {{Periode}} </h2> </div> <table> <tr> <th>No</th> <th>Nama</th> <th>Satuan</th> <th>Stok</th> <th>Jumlah</th> </tr> {{TableCode}} </table> <div class='container'><div class='total'>Jumlah : {{StockTotal}} &nbsp;&nbsp;&nbsp;{{TotalPrice}}</div></div> </br> <div class='footer'> {{City}} , {{ReportDate}} <div class='tapak-asma'> <div class='kepala'>Petugas Gudang Farmasi </br>RSUD Ajibarang</div> <div>{{UserName}}</div> <div>NIP: {{UserNum}}</div> </div> </div> </body> </html>";
	
	public ReportStockModel(GeneralFunction function, ReportData data) {
		this.function=function;
		this.setContentMode(ContentMode.HTML);
		this.data=data;
		this.server=function.getServer();
		this.date=function.getDate();
		this.setting=function.getSetting();
		this.text=function.getTextManipulator();
		setContent();
		replaceText();
	}
	Map<Goods, Double[]> goodsData=null;
	//untuk mengeset konten-konten yang ada di laporan
	private void setContent(){
		
		DateTime periodeDate=new DateTime(data.getDate());
		periode="Periode "+date.dateToText(periodeDate.toDate());
		
		reportDate = date.dateToText(new Date(),true);
		
		city=setting.getSetting("rs_city").getSettingValue();
		if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_OBAT.toString())){
			goodsType="Obat";
			goodsData=getGoods(data.getDate(), true);
			tableCode=generateTableCode(goodsData);
			
		}else if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_ALKES.toString())){
			goodsData=getGoods(data.getDate(), false);
			tableCode=generateTableCode(goodsData);
			goodsType="Alkes & BMHP";
		}
		this.user=function.getLogin().getUserLogin();	
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
		html=html.replace("{{GoodsType}}", goodsType);	
		html=html.replace("{{StockTotal}}", text.intToAngka(getTotalQuantity(goodsData)));
		html=html.replace("{{TotalPrice}}", text.doubleToRupiah(getTotalPrice(goodsData)));
		
		this.setValue(html);
	}
	private String generateTableCode(Map<Goods, Double[]> data){
		String returnValue="";
		
		int i=0;
		if(data.size()==0){
			return "<tr><td style='text-align:center;font-style:italic;' colspan='4'>Data kosong</td></tr>";
		}
		for(Map.Entry<Goods, Double[]> datum:data.entrySet()){
			i++;
			returnValue=returnValue+"<tr>";
				returnValue=returnValue+"<td>"+i+"</td>";
				returnValue=returnValue+"<td>"+datum.getKey().getName()+"</td>";
				returnValue=returnValue+"<td>"+datum.getKey().getUnit()+"</td>";
				returnValue=returnValue+"<td>"+text.intToAngka(datum.getValue()[0].intValue())+"</td>";
				returnValue=returnValue+"<td>"+text.doubleToRupiah(datum.getValue()[1])+"</td>";
			returnValue=returnValue+"</tr>";
			
		}
		return returnValue;
	}
	//mendapatkan jumlah total quantity
	public int getTotalQuantity(Map<Goods, Double[]> data){
		int returnValue=0;
		for(Map.Entry<Goods, Double[]> entry:data.entrySet()){
			returnValue=returnValue+entry.getValue()[0].intValue();
		}
		return returnValue;
	}
	
	//mendapatkan jumlah harga
	
	public Double getTotalPrice(Map<Goods, Double[]> data){
		double returnValue = 0;
		for(Map.Entry<Goods, Double[]> entry:data.entrySet()){
			returnValue=returnValue+entry.getValue()[1];
		}
		return returnValue;
		
	}
	//untuk mendapatkan data barang yang dihapus
	//menggunakan kelas Map, Goods untuk menampung barang, dan array Integer untuk menampung jumlah stok dan jumlah harga
	//Array 1 untuk stok, array 2 untuk harga
	private Map<Goods, Double[]> getGoods(Date periodeDate, boolean isObat){
		System.out.println("Mendapatkan periode");
		
		DateTime periode=new DateTime(periodeDate);
		DateTime startDate=periode.withHourOfDay(periode.hourOfDay().getMinimumValue());
		DateTime endDate=periode.withHourOfDay(periode.hourOfDay().getMaximumValue());
		
		List<String> goodsTypeList=new ArrayList<String>();
		if(isObat){
			goodsTypeList.add(EnumGoodsType.NARKOTIKA.toString());
			goodsTypeList.add(EnumGoodsType.PSIKOTROPIKA.toString());
			goodsTypeList.add(EnumGoodsType.OBAT.toString());
		}else{
			goodsTypeList.add(EnumGoodsType.ALAT_KESEHATAN.toString());
			goodsTypeList.add(EnumGoodsType.BMHP.toString());
		}
		//mendapatkan data reception dan deletion
		
		List<GoodsConsumption> goodsConsumption=server.find(GoodsConsumption.class).where().in("goods.type", goodsTypeList).
				between("consumptionDate", startDate.toDate(), endDate.toDate()).findList();
		
		List<GoodsReception> goodsReception=server.find(GoodsReception.class).where().
				in("invoiceItem.purchaseOrderItem.supplierGoods.goods.type", goodsTypeList).
				between("date", startDate.toDate(), endDate.toDate()).order().desc("date").findList();

		
		List<Goods> goodsList=server.find(Goods.class).where().in("type", goodsTypeList).findList();
		
		Map<Goods, Double[]> returnValue=new HashMap<Goods, Double[]>();
		
		for(Goods goods:goodsList){
			GoodsReception receptionBuffer=null;
			GoodsConsumption consumptionBuffer=null;
			
			for(GoodsReception reception:goodsReception){
				if(reception.getInvoiceItem().getPurchaseOrderItem().
						getSupplierGoods().getGoods().getIdGoods().equals(goods.getIdGoods())){
					receptionBuffer=reception;
				}
			}
			for(GoodsConsumption consumption:goodsConsumption){
				if(consumption.getGoods().getIdGoods().equals(goods.getIdGoods())){
					consumptionBuffer=consumption;
				}
			}
			
			ReturnData  data=extractData(goods, receptionBuffer, consumptionBuffer);
			
			Double[] doubleArray=new Double[2];
			doubleArray[0]=(double)data.getStock();
			doubleArray[1]=data.getPrice();
			
			returnValue.put(goods, doubleArray);
		}
		
		return returnValue;
	}
	//untuk membungkus data keluaran
	private class ReturnData{
		private Goods good;
		private double price;
		private int stock;
		
		public Goods getGood() {
			return good;
		}
		public void setGood(Goods good) {
			this.good = good;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public void setStock(int stock) {
			this.stock = stock;
		}
		public int getStock() {
			return stock;
		}
		
	}
	//menentukan data mana yang harus di 
	private  ReturnData extractData(Goods goods, GoodsReception reception, GoodsConsumption consumption){
		ReturnData data=new ReturnData();
		if(reception==null && consumption==null){
			int stock=goods.getCurrentStock();
			data.setGood(goods);
			data.setPrice(getTotalPrice(goods, stock));
			data.setStock(stock);
		}else if(reception!=null && consumption==null){
			int stock=reception.getStockQuantity();
			data.setGood(goods);
			data.setPrice(getTotalPrice(goods, stock));
			data.setStock(stock);			
		}else if(reception==null && consumption!=null){
			int stock=consumption.getStockQuantity();
			data.setGood(goods);
			data.setPrice(getTotalPrice(goods, stock));
			data.setStock(stock);
		}else if(reception!=null && consumption!=null){
			int stock;
			if(reception.getTimestamp().before(consumption.getTimestamp())){
				stock=consumption.getStockQuantity();
			}else{
				stock=consumption.getStockQuantity();
			}
			data.setGood(goods);
			data.setPrice(getTotalPrice(goods, stock));
			data.setStock(stock);
		}
		return data;
	}
	//menghitung total harga
	private double getTotalPrice(Goods goods, int stock){
		try {
			SupplierGoods supplierGoods=server.find(SupplierGoods.class).where().eq("goods", goods).order().desc("lastUpdate").findList().get(0);
			//get last price sudah termasuk PPN, jika mau non PPN, maka dikurangi 10%
			System.out.println(supplierGoods.getLastPrice()*stock);
			System.out.println((supplierGoods.getLastPrice()*100)/(100+setting.getPPN())*stock);
			System.out.println("Dengan PPN? "+data.isWithPPN());
			if(data.isWithPPN()){
				return supplierGoods.getLastPrice()*stock;
			}else{
				//dikurangi PPN
				return (supplierGoods.getLastPrice()*100)/(100+setting.getPPN())*stock;
			}
		} catch (Exception e) {
			return 0;
		}
	}
}
