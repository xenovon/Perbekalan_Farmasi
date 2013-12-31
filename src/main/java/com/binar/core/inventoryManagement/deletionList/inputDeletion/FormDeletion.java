package com.binar.core.inventoryManagement.deletionList.inputDeletion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.binar.generalFunction.TextManipulator;

public class FormDeletion {

	/*
	 * input
	 * input good
	 * input quantity
	 * input date
	 * input information
	 */

	private String goodsId="";
	private String suppGoodsId="";
	private String quantity="";
	private String periode="";
	private Date deletionDate;
	private String information="";
	
	private GeneralFunction function;
	private EbeanServer server;
	private GetSetting setting;
	private TextManipulator text;

	public FormDeletion(GeneralFunction function){
		this.function=function;
		this.server=function.getServer();
		this.setting=function.getSetting();
		this.text=function.getTextManipulator();
	}
	
	public List<String> validate(){
		List<String> error=new ArrayList<String>();
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
		if(deletionDate!=null ){
			if(deletionDate.equals("")){
				error.add("Data tanggal harus diisi");
			}			
		}else{
			error.add("Data tanggal harus diisi");	
		}
		System.out.println("Size : "+error.size());
		return (error.size()==0)?null:error;
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
	
	public Date getdeletionDate() {
		return deletionDate;
	}

	public void setdeletionDate(Date deletionDate) {
		this.deletionDate = deletionDate;
	}
	
	protected String getInformation() {
		return information;
	}
	
	protected void setInformation(String information) {
		this.information = information;
	}

	public String getSuppGoodsId() {
		return suppGoodsId;
	}

	public void setSuppGoodsId(String suppGoodsId) {
		this.suppGoodsId = suppGoodsId;
	}

	public Date getDeletionDate() {
		return deletionDate;
	}

	public void setDeletionDate(Date deletionDate) {
		this.deletionDate = deletionDate;
	}
}
