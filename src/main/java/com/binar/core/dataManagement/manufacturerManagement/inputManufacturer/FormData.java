package com.binar.core.dataManagement.manufacturerManagement.inputManufacturer;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.Supplier;
import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.data.validator.EmailValidator;

public class FormData {

	private String name="";
	private String description="";
	private String address="";
	private String phoneNumber="";
	private String email="";
	private String fax="";
	
	private GeneralFunction function;
	private EbeanServer server;
	private boolean editMode;
	public FormData(GeneralFunction function, boolean editMode) {
		this.function=function;
		this.editMode=editMode;
		server=function.getServer();
	}
	
	public List<String> validate(){
		List<String> errorData=new ArrayList<String>();
		

		//validasi form kosong
		if(name.equals("")){
			errorData.add("Nama produsen tidak boleh kosong");
		}
		
		String errorEmail=validateEmail();
		if(errorEmail != null){
			errorData.add(errorEmail);
		}
		
		return errorData.size()==0?null:errorData;
	}
	public String validateEmail(){
		if(!email.equals("")){
			if(!isValidEmailAddress(email)){
				return "Alamat email tidak valid";
			}
		}
		return null;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	//validasi alamat email
	public boolean isValidEmailAddress(String email) {
		return function.getEmailValidator("").isValid(email);
	}

}
