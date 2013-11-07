package com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.Manufacturer;
import com.binar.entity.Supplier;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.FormLayout;

public class InputFormModel {

	GeneralFunction function;
	EbeanServer server;
	
	public InputFormModel(GeneralFunction function){
		this.function=function;
		this.server=function.getServer();
	}
	
	public List<String> validate(FormLayout layout){
		//inisiasi variabel
		/*
		 * input
		 * input good
		 * input quantitiy
		 * input supplier
		 * input producer
		 * input price
		 * input information
		 */
		
		
		return null;
	}
	//Mendapatkan data daftar barang untuk combo box
	public Map<String,String> getGoodsData(){
		List<Goods> goodsList=server.find(Goods.class).findList();
		Map<String, String> data=new TreeMap<String, String>();
		for(Goods goods:goodsList){
			data.put(goods.getIdGoods(), goods.getName());
		}
		return data;
	}
	
	public Map<String, String> getSupplierData(){
		List<Supplier> supplierList=server.find(Supplier.class).findList();
		Map<String, String> data=new TreeMap<String, String>();
		for(Supplier supplier:supplierList){
			data.put(Integer.toString(supplier.getIdSupplier()), supplier.getSupplierName());
		}
		return data;
		
	}
	public Map<String, String> getManufacturer(){
		List<Manufacturer> manufacturerList=server.find(Manufacturer.class).findList();
		Map<String, String> data=new TreeMap<String, String>();
		for(Manufacturer manufacturer:manufacturerList){
			data.put(Integer.toString(manufacturer.getIdManufacturer()),
					manufacturer.getManufacturerName());
		}
		return data;
	}
	public boolean insertData(FormLayout layout){
		return true;
	}
	//Untuk mengubah  format January-2013 menjadi Date
	private DateTime parseDate(String date){
		
		String monthString=date.split("-")[0];
		String yearString=date.split("-")[1];
		
		int month=0;
		int year=Integer.parseInt(yearString);
		
		if(monthString.equals("Januari")){
			month=1;
		}else if(monthString.equals("Februari")){
			month=2;			
		}else if(monthString.equals("Maret")){
			month=3;
		}else if(monthString.equals("April")){
			month=4;
		}else if(monthString.equals("Mei")){
			month=5;
		}else if(monthString.equals("Juni")){
			month=6;
		}else if(monthString.equals("Juli")){
			month=7;
		}else if(monthString.equals("Agustus")){
			month=8;
		}else if(monthString.equals("September")){
			month=9;
		}else if(monthString.equals("Oktober")){
			month=10;
		}else if(monthString.equals("November")){
			month=11;
		}else if(monthString.equals("Desember")){
			month=12;
		}
		return new DateTime(year, month, 1, 0, 0);
		
	}
	
	
	
}
