package com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.generalFunction.GeneralFunction;

public class FormData {
	/*
	 * input
	 * input good
	 * input quantitiy
	 * input supplier
	 * input producer
	 * input price
	 * input information
	 */

	private String goodsId;
	private String quantity;
	private String supplierId;
	private String manufacturId;
	private String price;
	private String information;
	
	
	private GeneralFunction function;
	private EbeanServer server;
	public FormData(GeneralFunction function){
		this.function=function;
		this.server=function.getServer();
	}
	//Validasi sebelum data disimpan kedalam aplikasi
	public List<String> validate(){
		List<String> error=new ArrayList<String>();
		if(goodsId.equals("")||goodsId.equals(null)){
			error.add("Data obat harus di isi");
		}
		if(supplierId.equals("")||supplierId.equals(null)){
			error.add("Data distributor harus di isi");
		}
		if(manufacturId.equals("")||manufacturId.equals(null)){
			error.add("Data produsen harus di isi");
		}
		String errorQuantity=validateQuantity();
		String errorSupplier=validatePrice();
		
		if(errorQuantity.equals("")){
			error.add(errorQuantity);
		}
		if(errorSupplier.equals("")){
			error.add(errorSupplier);
		}
		return null;
	}
	//validasi untuk quantity, menentukan apakah data inputan valid apa ngga
	public String validateQuantity(){
		String message="";
		try {
			int quantity=Integer.parseInt(this.quantity);
			if(quantity<0){
				message="Kuantitas harus lebih dari 0";
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			message="Data harus berupa angka";
		}
		return message;
	}
	//Validasi untuk harga
	public String validatePrice(){
		String message="";
		try {
			int price=Integer.parseInt(this.price);
			if(price<0){
				message="Harga harus lebih dari 0";
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			message="Harga harus berupa angka";
		}
		return message;
	}
		
}
