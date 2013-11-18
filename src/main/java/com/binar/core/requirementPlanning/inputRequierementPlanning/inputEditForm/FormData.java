package com.binar.core.requirementPlanning.inputRequierementPlanning.inputEditForm;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.Setting;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.binar.generalFunction.TextManipulator;

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

	private String goodsId="";
	private String quantity="";
	private String supplierId="";
	private String manufacturId="";
	private String price="";
	private String information="";
	private String periode="";
	
	private GeneralFunction function;
	private EbeanServer server;
	private GetSetting setting;
	private TextManipulator text;
	public FormData(GeneralFunction function){
		this.function=function;
		this.server=function.getServer();
		this.setting=function.getSetting();
		this.text=function.getTextManipulator();
	}
	//Validasi sebelum data disimpan kedalam aplikasi
	public List<String> validate(){
		List<String> error=new ArrayList<String>();
		if(goodsId!=null ){
			if(goodsId.equals("")){
				error.add("Data obat harus di isi");
			}			
		}else{
			error.add("Data obat harus di isi");			
		}
		if(supplierId!=null){
			if(supplierId.equals("")){
				error.add("Data distributor harus di isi");
			}			
		}else{
			error.add("Data distributor harus di isi");			
		}
		if(manufacturId!=null){
			if(manufacturId.equals("")){
				error.add("Data produsen harus di isi");				
			}
		}else{
			error.add("Data produsen harus di isi");
		}
		String errorQuantity=validateQuantity();
		String errorPrice=validatePrice();
		
		if(!errorQuantity.equals("")){
			error.add(errorQuantity);
		}
		if(!errorPrice.equals("")){
			error.add(errorPrice);
		}
		System.out.println("Size : "+error.size());
		return (error.size()==0)?null:error;

	}
	//validasi untuk quantity, menentukan apakah data inputan valid apa ngga
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
	//Validasi untuk harga
	public String validatePrice(){
		if(this.price!=null){
			if(!this.price.equals("")){
				try {
					double price=Double.parseDouble(this.price);
					if(price<0){
						return "Harga harus lebih dari 0";
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
					return "Harga harus berupa angka";
				}
			}
		}
		
		return "";
	}
	//validasi untuk mengecek nilai harga agar tidak melebihi HET, 
	//Validasi ini tidak mengikat, hanya menampilkan peringatan
	public String validatePriceHET(){
		//memastikan lolos dari validatePrice()
		if(validatePrice().equals("")){
			double price=Double.parseDouble(this.price);
			
			//hitung harga jual
			double plusPPN=price+(price*setting.getPPN()/100);
			double sellingPrice=plusPPN+(plusPPN*setting.getMargin()/100);
			double het=getHET();
			if(sellingPrice > het){
				return "Harga Jual Lebih tinggi dari HET </br>"+
					   "Harga Jual :  "+text.doubleToRupiah(sellingPrice)+
					   "</br>HET   :  "+text.doubleToRupiah(het);
			}
		}
		return "";
	}
	private double getHET(){
		Goods goods=server.find(Goods.class, goodsId);
		return goods.getHet();
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
	protected String getSupplierId() {
		return supplierId;
	}
	protected void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	protected String getManufacturId() {
		return manufacturId;
	}
	protected void setManufacturId(String manufacturId) {
		this.manufacturId = manufacturId;
	}
	protected String getPrice() {
		return price;
	}
	protected void setPrice(String price) {
		this.price = price;
	}
	protected String getInformation() {
		return information;
	}
	protected void setInformation(String information) {
		this.information = information;
	}
	protected void setPeriode(String periode) {
		this.periode = periode;
	}
	public String getPeriode() {
		return periode;
	}
	public String toString(){
		
		return goodsId+" + "+quantity+" + "+supplierId+" + "
				 + ""+manufacturId+" + "+price+" + "+information+" + "+periode;
	}
	
		
}
