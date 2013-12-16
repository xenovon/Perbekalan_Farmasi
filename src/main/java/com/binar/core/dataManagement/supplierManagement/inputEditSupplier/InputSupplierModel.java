package com.binar.core.dataManagement.supplierManagement.inputEditSupplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.OptimisticLockException;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.GoodsReceipt;
import com.binar.entity.Insurance;
import com.binar.entity.InvoiceItem;
import com.binar.entity.PurchaseOrderItem;
import com.binar.entity.Supplier;
import com.binar.entity.SupplierGoods;
import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;

public class InputSupplierModel {

	private GeneralFunction function;
	private EbeanServer server;
	private GetSetting setting;
	public InputSupplierModel(GeneralFunction function) {
		this.function=function;
		this.setting=function.getSetting();
		this.server=function.getServer();
	}
	//mendapatkan satu barang;
	public Supplier getSingleSupplier(int idSupplier){
		return server.find(Supplier.class,idSupplier);
		
	}
	//menyimpan barang baru
	public String saveData(FormData data){
		try {
			Supplier supplier=new Supplier();
			supplier.setDescription(data.getDescription());
			supplier.setEmail(data.getEmail());
			supplier.setFax(data.getFax());
			supplier.setPhoneNumber(data.getPhoneNumber());
			supplier.setSupplierAbbr(data.getAbbr());
			supplier.setSupplierAddress(data.getAddress());
			supplier.setSupplierName(data.getName());
			supplier.setCity(data.getCity());
			server.save(supplier);
		} catch (Exception e) {
			e.printStackTrace();
			return "Kesalahan dalam penyimpanan data";
		}
		return null;
	}
	//menyimpan hasil perubahan barang
	public String saveEditData(FormData data, int IdSupplier){
		try {
			Supplier supplier=server.find(Supplier.class, IdSupplier);
			supplier.setDescription(data.getDescription());
			supplier.setEmail(data.getEmail());
			supplier.setFax(data.getFax());
			supplier.setPhoneNumber(data.getPhoneNumber());
			supplier.setSupplierAbbr(data.getAbbr());
			supplier.setSupplierAddress(data.getAddress());
			supplier.setSupplierName(data.getName());
			supplier.setCity(data.getCity());
			server.update(supplier);
		} catch (Exception e) {
			e.printStackTrace();
			return "Kesalahan dalam penyimpanan data";
		}
		return null;
	}
	
	
	
	
}
