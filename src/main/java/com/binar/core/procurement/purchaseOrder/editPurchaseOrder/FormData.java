package com.binar.core.procurement.purchaseOrder.editPurchaseOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

public class FormData {
	
	
	private String number;
	private Date date;
	private Integer userResponsible=0;
	private String rayon="";
	private int idPurchaseOrder;
	private String name;
	
	public List<String> validate(){
		List<String> error=new ArrayList<String>();
		if(date==null){
			error.add("Tanggal tidak boleh kosong");
		}
		if(userResponsible == null){
			error.add("Penanggung jawab tidak boleh kosong");
		}
		if(name==null){
			error.add("Nama tidak boleh kosong");			
		}
		return error.size()==0?null:error;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdPurchaseOrder() {
		return idPurchaseOrder;
	}
	public void setIdPurchaseOrder(int idPurchaseOrder) {
		this.idPurchaseOrder = idPurchaseOrder;
	}
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getUserResponsible() {
		return userResponsible;
	}
	public void setUserResponsible(Integer userResponsible) {
		this.userResponsible = userResponsible;
	}
	public String getRayon() {
		return rayon;
	}
	public void setRayon(String rayon) {
		this.rayon = rayon;
	}

	
}
