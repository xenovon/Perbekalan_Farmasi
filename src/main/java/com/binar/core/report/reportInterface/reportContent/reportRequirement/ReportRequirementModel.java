package com.binar.core.report.reportInterface.reportContent.reportRequirement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.core.report.reportInterface.reportContent.ReportData;
import com.binar.core.report.reportInterface.reportContent.ReportData.PeriodeType;
import com.binar.core.report.reportInterface.reportContent.ReportData.ReportAcceptance;
import com.binar.entity.DeletedGoods;
import com.binar.entity.ReqPlanning;
import com.binar.entity.Role;
import com.binar.entity.User;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.generalFunction.AcceptancePyramid;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.binar.generalFunction.TextManipulator;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class ReportRequirementModel extends Label{

	private GeneralFunction function;
	private String periode; //
	private String tableCode; //
	private String city; //
	private String reportDate; // 
	private String userName; //
	private String userNum; //
	private String goodsType;
	private String acceptText;
	
	private ReportData data;
	private EbeanServer server;
	private User user; //untuk penanggung jawab yang tanda tangan
	private GetSetting setting;
	private DateManipulator date;
	private TextManipulator text;
	
	private AcceptancePyramid accept;
	//Variabel untuk ditampilkan di surat pesanan
		
	private String html="<html> <head> <title> Daftar Kebutuhan {{GoodsType}} </title> <style type='text/css'>body{width:750px;font-family:arial}h1.title{display:block;margin:0 auto;font-size:24px;text-align:center}h2.address{display:block;margin:0 auto;font-size:16px;font-weight:normal;text-align:center}.center{padding-bottom:20px;margin-bottom:30px}.kepada{width:400px;margin-top:30px;line-height:1.5em}.PONumber{float:right;top:40px}table{width:100%;border:1px solid black;border-collapse:collapse}table tr td,table tr th{border:1px solid black;padding:2px;margin:0}.footer{float:right;margin-top:60px}.tapak-asma{text-align:center}.kepala{margin-bottom:100px}</style> </head> <body> <div class='center'> <h1 class='title'>Daftar Kebutuhan {{GoodsType}} {{AcceptText}}</h1> <h2 class='address'> {{Periode}} </h2> </div> <table> <tr> <th>No</th> <th>Nama</th> <th>Satuan</th> <th>Asuransi</th> <th>HNA + PPN %</th> <th>Kebutuhan</th> <th>Perkiraan Jumlah Harga</th> <th>Produsen</th> <th>Distributor</th> <th>Disetujui</th> <th>Keterangan</th> </tr> {{TableCode}} </table> <div class='footer'> {{City}} , {{ReportDate}} </br> <div class='tapak-asma'> <div class='kepala'>Disusun Oleh, </br>Petugas Gudang Farmasi </br>RSUD Ajibarang</div> <div>{{UserName}}</div> <div>NIP: {{UserNum}}</div> </div> </div> </body> </html>";

	public ReportRequirementModel(GeneralFunction function) {
		this.function=function;
		this.accept=function.getAcceptancePyramid();
		this.server=function.getServer();
		this.date=function.getDate();
		this.setting=function.getSetting();
		this.text=function.getTextManipulator();
	}
	public ReportRequirementModel(GeneralFunction function, ReportData data) {
		this.function=function;
		this.setContentMode(ContentMode.HTML);
		this.data=data;
		this.accept=function.getAcceptancePyramid();
		this.server=function.getServer();
		this.date=function.getDate();
		this.setting=function.getSetting();
		this.text=function.getTextManipulator();
		System.out.println("Start requirement");
		setContent();
		replaceText();
	}
	//untuk mengeset konten-konten yang ada di laporan
	private void setContent(){
		
		DateTime periodeDate=new DateTime(data.getDateMonth());
		periode="Periode "+date.dateToText(periodeDate.toDate());
		
		reportDate = date.dateToText(new Date(),true);
		
		city=setting.getSetting("rs_city").getSettingValue();
		if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_OBAT.toString())){
			goodsType="Obat";
			tableCode=generateTableCode(getRequirement(periodeDate, true, data.getAccept()));
			
		}else if(data.getSelectedGoods().equals(ReportData.SELECT_GOODS_ALKES.toString())){
			tableCode=generateTableCode(getRequirement(periodeDate, false, data.getAccept()));
			goodsType="Alkes & BMHP";
		}
		switch (data.getAccept()) {
		case ACCEPTED:acceptText="Disetujui";
			
			break;
		case NON_ACCEPTED:acceptText="Belum Disetujui";break;
		case BOTH:acceptText="";break;
		default:
			acceptText="";
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
		html=html.replace("{{AcceptText}}", acceptText);
		html=html.replace("{{GoodsType}}", goodsType);		
		this.setValue(html);
	}
	private String generateTableCode(List<ReqPlanning> data){
		String returnValue="";
		
		int i=0;
		if(data.size()==0){
			return "<tr><td style='text-align:center;font-style:italic;' colspan='10'>Data kosong</td></tr>";
		}
		for(ReqPlanning datum:data){
			i++;
			returnValue=returnValue+"<tr>";
				returnValue=returnValue+"<td>"+i+"</td>";
				returnValue=returnValue+"<td>"+datum.getSupplierGoods().getGoods().getName()+"</td>";
				returnValue=returnValue+"<td>"+datum.getSupplierGoods().getGoods().getUnit()+"</td>";
				returnValue=returnValue+"<td>"+datum.getSupplierGoods().getGoods().getInsurance().getName()+"</td>";
				returnValue=returnValue+"<td>"+text.doubleToRupiah(datum.getPriceEstimation())+"</td>";   //ERROR
				returnValue=returnValue+"<td>"+text.intToAngka(datum.getQuantity())+"</td>";
				returnValue=returnValue+"<td>"+text.doubleToRupiah(datum.getPriceEstimationPPN()*datum.getQuantity())+"</td>";
				returnValue=returnValue+"<td>"+datum.getSupplierGoods().getManufacturer().getManufacturerName()+"</td>";
				returnValue=returnValue+"<td>"+datum.getSupplierGoods().getSupplier().getSupplierName()+"</td>";
				returnValue=returnValue+"<td>"+accept.acceptedBy(datum.getAcceptance())+"</td>";


				returnValue=returnValue+"<td style='max-width:220px;'>"+datum.getInformation()+"</td>";
			returnValue=returnValue+"</tr>";
			
		}
		
		
		return returnValue;
	}
	//untuk mendapatkan data barang yang dihapus
	List<ReqPlanning> getRequirement(DateTime periode, boolean isObat, ReportAcceptance acceptance){
		System.out.println("Mendapatkan periode");
		
		
		DateTime start=periode.withDayOfMonth(periode.dayOfMonth().getMinimumValue()).withMillisOfDay(periode.millisOfDay().getMinimumValue());
		DateTime end=periode.withDayOfMonth(periode.dayOfMonth().getMaximumValue()).withMillisOfDay(periode.millisOfDay().getMaximumValue());
		
		start=start.minusSeconds(5);
		List<String> goodsTypeList=new ArrayList<String>();
		if(isObat){
			goodsTypeList.add(EnumGoodsType.NARKOTIKA.toString());
			goodsTypeList.add(EnumGoodsType.PSIKOTROPIKA.toString());
			goodsTypeList.add(EnumGoodsType.OBAT.toString());
		}else{
			goodsTypeList.add(EnumGoodsType.ALAT_KESEHATAN.toString());
			goodsTypeList.add(EnumGoodsType.BMHP.toString());
		}
		List<ReqPlanning> reqPlannings;
		if(acceptance==ReportAcceptance.ACCEPTED){
			reqPlannings=server.find(ReqPlanning.class).where().eq("acceptance",accept.getAcceptedByAllCriteria()).
					between("period", start.toDate(), end.toDate()).in("supplierGoods.goods.type", goodsTypeList).order().desc("timestamp").findList();			
		}else if(acceptance==ReportAcceptance.NON_ACCEPTED){
			reqPlannings=server.find(ReqPlanning.class).where().lt("acceptance", accept.getAcceptedByAllCriteria()).
					between("period", start.toDate(), end.toDate()).in("supplierGoods.goods.type", goodsTypeList).order().desc("timestamp").findList();						
		}else{
			reqPlannings=server.find(ReqPlanning.class).where().
					between("period", start.toDate(), end.toDate()).in("supplierGoods.goods.type", goodsTypeList).order().desc("timestamp").findList();			
		}
		
		System.out.println("Start Date : "+start);
		System.out.println("End Date : "+end);
		
		return reqPlannings; 

	}
}
