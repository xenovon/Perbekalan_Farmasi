package com.binar.core.report.reportInterface.reportContent.reportProcurement;

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
import com.binar.entity.PurchaseOrder;
import com.binar.entity.PurchaseOrderItem;
import com.binar.entity.User;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class ReportProcurementModel extends Label{
 
	private GeneralFunction function;
	private String goodsType; //
	private String periode; //
	private String tableCode; //
	private String city; //
	private String reportDate; // 
	private String userName; //
	private String userNum; //

	private ReportData data;
	private EbeanServer server;
	private User user; //untuk penanggung jawab yang tanda tangan
	private GetSetting setting;
	private DateManipulator date;
	
	//Variabel untuk ditampilkan di surat pesanan
	private String html="<html> <head> <title> Laporan Pengadaan {{GoodsType}} </title> <style type='text/css'>body{width:750px;font-family:arial}h1.title{display:block;margin:0 auto;font-size:24px;text-align:center}h2.address{display:block;margin:0 auto;font-size:16px;font-weight:normal;text-align:center}.center{padding-bottom:20px;margin-bottom:30px}.kepada{width:400px;margin-top:30px;line-height:1.5em}.PONumber{float:right;top:40px}table{width:100%;border:1px solid black;border-collapse:collapse}table tr td,table tr th{border:1px solid black;padding:2px;margin:0}.footer{float:right;margin-top:60px}.tapak-asma{text-align:center}.kepala{margin-bottom:100px}</style> </head> <body> <div class='center'> <h1 class='title'>Laporan Pengadaan {{GoodsType}}</h1> <h2 class='address'> {{Periode}} </h2> </div> <table> <tr> <th>No</th> <th>Nomor SP</th> <th>Tanggal</th> <th>Pemasok</th> <th>Produsen</th> <th>Nama Barang</th> <th>Jumlah</th> </tr> {{TableCode}} </table> <div class='footer'> {{City}} , {{ReportDate}} <div class='tapak-asma'> <div class='kepala'>Petugas Gudang Farmasi </br>RSUD Ajibarang</div> <div>{{UserName}}</div> <div>NIP: {{UserNum}}</div> </div> </div> </body> </html>";
	public ReportProcurementModel(GeneralFunction function, ReportData data) {
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
			tableCode=generateTableCode(getPurchaseOrder(data.getDateMonth(), true));
		}else if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_ALKES.toString())){
			tableCode=generateTableCode(getPurchaseOrder(data.getDateMonth(), false));
			goodsType="Alkes & BMHP";
		}
		periode="Periode "+date.dateToText(data.getDateMonth());
		
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
		html=html.replace("{{City}}", city);
		html=html.replace("{{ReportDate}}", reportDate);
		html=html.replace("{{UserName}}", userName);
		html=html.replace("{{UserNum}}", userNum);		
		
		this.setValue(html);
	}
	private String generateTableCode(Map<PurchaseOrder, List<PurchaseOrderItem>> data){
		String returnValue="";		
		int i=0;
		if(data.size()==0){
			return "<tr><td style='text-align:center;font-style:italic;' colspan='7'>Data kosong</td></tr>";
		}
		for(Map.Entry<PurchaseOrder, List<PurchaseOrderItem>> entry:data.entrySet()){
			i++;
			if(entry.getValue().size()!=0){
				returnValue=returnValue+"<tr>";
				returnValue=returnValue+"<td>"+i+"</td>";
				returnValue=returnValue+"<td rowspan='"+entry.getValue().size()+"'>"+entry.getKey().getPurchaseOrderNumber()+"</td>";
				returnValue=returnValue+"<td rowspan='"+entry.getValue().size()+"'>"+date.dateToText(entry.getKey().getDate(), true)+"</td>";
				returnValue=returnValue+"<td rowspan='"+entry.getValue().size()+"'>"+entry.getValue().get(0).getSupplierGoods().getSupplier().getSupplierName()+"</td>";
				returnValue=returnValue+"<td rowspan='"+entry.getValue().size()+"'>"+entry.getValue().get(0).getSupplierGoods().getManufacturer().getManufacturerName()+"</td>";
				int x=0;
				for(PurchaseOrderItem item:entry.getValue()){
					if(x==0){
						returnValue=returnValue+"<td>"+item.getSupplierGoods().getGoods().getName()+"</td>";
						returnValue=returnValue+"<td>"+item.getQuantity()+"</td>";
						returnValue=returnValue+"</tr>";
					}else{
						returnValue=returnValue+"<tr>";
						returnValue=returnValue+"<td>"+item.getSupplierGoods().getGoods().getName()+"</td>";
						returnValue=returnValue+"<td>"+item.getQuantity()+"</td>";
						returnValue=returnValue+"</tr>";
					}
					x++;
				}
			}
			
		}
		return returnValue;
	}
	//untuk mendapatkan surat pesanan dalam tempo 1 bulan
	private Map<PurchaseOrder, List<PurchaseOrderItem>> getPurchaseOrder(Date periode, boolean isObat){
		System.out.println("Mendapatkan periode");
		
		DateTime startDate=new DateTime(periode);
			startDate=startDate.withDayOfMonth(startDate.dayOfMonth().getMinimumValue());
		DateTime endDate=startDate.withDayOfMonth(startDate.dayOfMonth().getMaximumValue());

		List<PurchaseOrder> purchaseOrders=server.find(PurchaseOrder.class).where().
				between("date", startDate.toDate(), endDate.toDate()).findList();
		System.out.println("Start Date : "+startDate);
		System.out.println("End Date : "+endDate);
		
		Map<PurchaseOrder, List<PurchaseOrderItem>> returnValue=new HashMap<PurchaseOrder, List<PurchaseOrderItem>>();		
		for (PurchaseOrder purchaseOrder : purchaseOrders){
			List<PurchaseOrderItem> items=filterList(purchaseOrder.getPurchaseOrderItem(), isObat);
			returnValue.put(purchaseOrder, items);
		}
		return returnValue; 

	}
	//untuk menentukan apakah tipe barang termasuk yang ada didalam list
	private List<PurchaseOrderItem> filterList(List<PurchaseOrderItem> items, boolean isObat){
		List<EnumGoodsType> goodsTypeList=new ArrayList<EnumGoodsType>();
		List<PurchaseOrderItem> itemSelected=new ArrayList<PurchaseOrderItem>();
		for(PurchaseOrderItem item:items){
			Goods goods=item.getSupplierGoods().getGoods();
			if(isObat){
				if(goods.getType()==EnumGoodsType.NARKOTIKA){
					itemSelected.add(item);
				}else if(goods.getType()==EnumGoodsType.PSIKOTROPIKA){
					itemSelected.add(item);
				}else if(goods.getType()==EnumGoodsType.OBAT){
					itemSelected.add(item);					
				}
			}
			else{
				if(goods.getType()==EnumGoodsType.BMHP){
					itemSelected.add(item);
				}else if(goods.getType()==EnumGoodsType.ALAT_KESEHATAN){
					itemSelected.add(item);
				}					
			}
			}
			return itemSelected;
		}

	
}
