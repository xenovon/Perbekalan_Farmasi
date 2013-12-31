package com.binar.core.inventoryManagement.receptionList.inputReception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.binar.generalFunction.TextManipulator;

public class FormReception {
	
	/*
	 * input
	 * input invoice item?
	 * input quantity
	 * input information
	 * input date
	 * input expired date
	 */
	
	private String invoiceItemId="";
	private String goodsId="";
	private String quantity="";
	private String information="";
	private Date receptionDate;
	private Date expiredDate;
	private String periode="";
	
	private GeneralFunction function;
	private EbeanServer server;
	private GetSetting setting;
	private TextManipulator text;
	
	public FormReception(GeneralFunction function){
		this.function=function;
		this.server=function.getServer();
		this.setting=function.getSetting();
		this.text=function.getTextManipulator();
	}
	
	public List<String> validate(){
		List<String> error=new ArrayList<String>();
		if(expiredDate!=null ){
			if(expiredDate.equals("")){
				error.add("Data tanggal kadaluarsa harus diisi");
			}			
		}else{
			error.add("Data tanggal kadaluarsa harus diisi");			
		}
		String errorQuantity=validateQuantity();		
		if(!errorQuantity.equals("")){
			error.add(errorQuantity);
		}
		if(receptionDate!=null ){
			if(receptionDate.equals("")){
				error.add("Data tanggal penerimaan harus diisi");
			}			
		}else{
			error.add("Data tanggal penerimaan harus diisi");		
		}
		System.out.println("Size : "+error.size());
		return (error.size()==0)?null:error;
	}

	public String validateQuantity() {
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
	
	protected String getInvoiceItemId() {
		return invoiceItemId;
	}
	protected void setInvoiceItemId(String goodsId) {
		this.invoiceItemId = invoiceItemId;
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
	
	public Date getReceptionDate() {
		return receptionDate;
	}

	public void setReceptionDate(Date consumptionDate) {
		this.receptionDate = receptionDate;
	}

	protected String getInformation() {
		return information;
	}
	
	protected void setInformation(String information) {
		this.information = information;
	}
	
	protected String getGoodsId() {
		return goodsId;
	}
	
	protected void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	
}
