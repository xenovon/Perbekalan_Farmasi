package com.binar.core.dataManagement.manufacturerManagement.inputManufacturer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.OptimisticLockException;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.GoodsReceipt;
import com.binar.entity.Insurance;
import com.binar.entity.InvoiceItem;
import com.binar.entity.Manufacturer;
import com.binar.entity.PurchaseOrderItem;
import com.binar.entity.Supplier;
import com.binar.entity.SupplierGoods;
import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;

public class InputManufacturerModel {

	private GeneralFunction function;
	private EbeanServer server;
	private GetSetting setting;
	public InputManufacturerModel(GeneralFunction function) {
		this.function=function;
		this.setting=function.getSetting();
		this.server=function.getServer();
	}
	//mendapatkan satu barang;
	public Manufacturer getSingleManufacturer(int idManufacturer){
		return server.find(Manufacturer.class,idManufacturer);
		
	}
	//menyimpan barang baru
	public String saveData(FormData data){
		try {
			Manufacturer manufacturer=new Manufacturer();
			manufacturer.setDescription(data.getDescription());
			manufacturer.setEmail(data.getEmail());
			manufacturer.setFax(data.getFax());
			manufacturer.setPhoneNumber(data.getPhoneNumber());
			manufacturer.setAddress(data.getAddress());
			manufacturer.setManufacturerName(data.getName());
			
			server.save(manufacturer);
		} catch (Exception e) {
			e.printStackTrace();
			return "Kesalahan dalam penyimpanan data";
		}
		return null;
	}
	//menyimpan hasil perubahan barang
	public String saveEditData(FormData data, int IdManufacturer){
		try {
			Manufacturer manufacturer=server.find(Manufacturer.class, IdManufacturer);
			manufacturer.setDescription(data.getDescription());
			manufacturer.setEmail(data.getEmail());
			manufacturer.setFax(data.getFax());
			manufacturer.setPhoneNumber(data.getPhoneNumber());
			manufacturer.setAddress(data.getAddress());
			manufacturer.setManufacturerName(data.getName());
			
			server.update(manufacturer);
		} catch (Exception e) {
			e.printStackTrace();
			return "Kesalahan dalam penyimpanan data";
		}
		return null;
	}
	
	
	
	
}
