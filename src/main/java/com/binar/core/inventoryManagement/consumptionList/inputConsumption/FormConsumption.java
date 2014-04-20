package com.binar.core.inventoryManagement.consumptionList.inputConsumption;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.binar.generalFunction.StockFunction;
import com.binar.generalFunction.TextManipulator;

public class FormConsumption {
	
	/*
	 * input
	 * input good
	 * input quantitiy
	 * input supplier
	 * input producer
	 * input price
	 * input information
	 */

	private String goodsId="";
	private String quantity="";
	private String periode="";
	private Date consumptionDate;
	private String ward="";
	private String information="";
	
	private GeneralFunction function;
	private EbeanServer server;
	private GetSetting setting;
	private TextManipulator text;
	private StockFunction stock;
	public FormConsumption(GeneralFunction function){
		this.function=function;
		this.server=function.getServer();
		this.setting=function.getSetting();
		this.text=function.getTextManipulator();
	}
	
	public List<String> validate(){
		List<String> error=new ArrayList<String>();
		String errorDate=validateDate();
		if(!errorDate.equals("")){
			error.add(errorDate);
		}
		if(goodsId!=null ){
			if(goodsId.equals("")){
				error.add("Data obat harus diisi");
			}			
		}else{
			error.add("Data obat harus diisi");			
		}
		String errorQuantity=validateQuantity();
		
		if(!errorQuantity.equals("")){
			error.add(errorQuantity);
		}
		if(consumptionDate!=null ){
			if(consumptionDate.equals("")){
				error.add("Data tanggal harus diisi");
			}			
		}else{
			error.add("Data tanggal harus diisi");	
		}
		if(ward!=null ){
			if(ward.equals("")){
				error.add("Data instalasi harus diisi");
			}			
		}else{
			error.add("Data instalasi harus diisi");	
		}
		System.out.println("Size : "+error.size());
		return (error.size()==0)?null:error;
	}
	public String validateDate(){
		Date date=getConsumptionDate();
		if(stock.isDateValid(date, getGoodsId())){
			return "";
		}else{
			return "Tidak bisa menginput untuk tanggal ini";
		}
	}
	public String validateQuantity(){
		if(this.quantity!=null){	
			if(!this.quantity.equals("")){
				try {
					int quantity=Integer.parseInt(this.quantity);
					if(quantity<0){
						return "Kuantitas harus lebih dari 0";
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
					return "Data harus berupa angka";
				}
			}
		}
		return "";
	}
	
	protected String getGoodsId() {
		return goodsId;
	}
	protected void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	protected String getQuantity() {
		return quantity;
	}
	protected void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	protected void setPeriode(String periode) {
		this.periode = periode;
	}
	public String getPeriode() {
		return periode;
	}
	
	public Date getConsumptionDate() {
		return consumptionDate;
	}

	public void setConsumptionDate(Date consumptionDate) {
		this.consumptionDate = consumptionDate;
	}
	
	protected String getWard() {
		return ward;
	}
	protected void setWard(String ward) {
		this.ward = ward;
	}
	protected String getInformation() {
		return information;
	}
	
	protected void setInformation(String information) {
		this.information = information;
	}
}
