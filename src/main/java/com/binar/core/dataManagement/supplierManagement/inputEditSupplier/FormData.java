package com.binar.core.dataManagement.supplierManagement.inputEditSupplier;

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

	private String abbr="";
	private String abbrInitValue=""; //nilai awal ABBR dari database sebelum dirubah ke form, digunakan untuk mode edit.
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
		if(abbr.equals("")){
			errorData.add("Singkatan distributor tidak boleh kosong");
		}
		if(name.equals("")){
			errorData.add("Nama distributor tidak boleh kosong");
		}
		//validasi Id
		if(!editMode){
			String errorId=validateAbbr();
			if(errorId!=null){
				errorData.add(errorId);
			}			
		}
		
		String errorEmail=validateEmail();
		if(errorEmail != null){
			errorData.add(errorEmail);
		}
		
		return errorData.size()==0?null:errorData;
	}
	public String validateAbbr(){
		System.out.println(abbr+" vs "+ abbrInitValue);
		if(editMode){
			if(abbr.equals(abbrInitValue)){
				return null;
			}
		}
		
		if(abbr.equals("")){
			return "Singkatan distributor tidak boleh kosong";
		}
		if(abbr.contains(" ")){
			return "Singkatan distributortidak boleh mengandung spasi";
		}
		if(!isUpper(abbr)){
			return "Singkatan harus berisi huruf besar semua";
		}
		Supplier supplier=server.find(Supplier.class).where().eq("supplierAbbr",abbr).findUnique();
		if(supplier==null){
			return null;
		}else{
			return "Singkatan distributor "+abbr+" sudah tercatat sebelumnya</br>Gunakan singkatan yang unik";
		}
	}
	public String validateEmail(){
		if(!email.equals("")){
			if(!isValidEmailAddress(email)){
				return "Alamat email tidak valid";
			}
		}
		return null;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
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
	public String getAbbrInitValue() {
		return abbrInitValue;
	}
	public void setAbbrInitValue(String abbrInitValue) {
		this.abbrInitValue = abbrInitValue;
	}
	//untuk mengecek apakah string berisi huruf besar semua atau tidak
	private  boolean isUpper(String s)
	{
	    for(char c : s.toCharArray())
	    {
	        if(! Character.isUpperCase(c))
	            return false;
	    }

	    return true;
	}
	//validasi alamat email
	public boolean isValidEmailAddress(String email) {
		return function.getEmailValidator("").isValid(email);
	}

}
