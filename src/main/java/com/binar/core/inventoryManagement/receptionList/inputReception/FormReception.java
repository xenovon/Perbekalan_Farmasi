package com.binar.core.inventoryManagement.receptionList.inputReception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.InvoiceItem;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.binar.generalFunction.StockFunction;
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
	private boolean editMode;
	private Integer invoiceItemId;
	private String quantity="";
	private String information="";
	private Date receptionDate;
	private Date expiredDate;
	
	private GeneralFunction function;
	private EbeanServer server;
	private GetSetting setting;
	private TextManipulator text;
	private StockFunction stock;
	
	
	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
	public boolean isEditMode() {
		return editMode;
	}
	public FormReception(GeneralFunction function){
		this.function=function;
		this.server=function.getServer();
		this.setting=function.getSetting();
		this.text=function.getTextManipulator();
		this.stock=function.getStock();
	}
	
	public List<String> validate(){
		List<String> error=new ArrayList<String>();
		String errorDate=validateDate();
		if(!errorDate.equals("")){
			error.add(errorDate);
		}
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
	public String validateDate(){
		try {
			Date date=getReceptionDate();
			String idGoods=server.find(InvoiceItem.class, getInvoiceItemId()).getPurchaseOrderItem().getSupplierGoods().getGoods().getIdGoods();
			if(stock.isDateValid(date,idGoods)){
				return "";
			}else{
				return "Tidak bisa menginput untuk tanggal ini";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Program Bug, Hubungi Developer untuk pencerahan";
		}
	}

	public Integer getInvoiceItemId() {
		return invoiceItemId;
	}
	public void setInvoiceItemId(Integer invoiceItemId) {
		this.invoiceItemId = invoiceItemId;
	}
	public String getQuantity() {
		return quantity;
	}	
	public Integer getQuantityInt() {
		return Integer.parseInt(quantity);
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public Date getReceptionDate() {
		return receptionDate;
	}

	public void setReceptionDate(Date receptionDate) {
		this.receptionDate = receptionDate;
	}

	public String getInformation() {
		return information;
	}
	
	public void setInformation(String information) {
		this.information = information;
	}
	

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	
}
