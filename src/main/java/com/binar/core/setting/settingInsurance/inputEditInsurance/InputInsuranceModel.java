package com.binar.core.setting.settingInsurance.inputEditInsurance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.OptimisticLockException;

import com.avaje.ebean.EbeanServer;
import com.binar.core.dataManagement.goodsManagement.inputEditGoods.InputGoodsView.ComboDataList;
import com.binar.core.procurement.PurchaseOrder;
import com.binar.entity.Goods;
import com.binar.entity.GoodsReception;
import com.binar.entity.Insurance;
import com.binar.entity.InvoiceItem;
import com.binar.entity.PurchaseOrderItem;
import com.binar.entity.SupplierGoods;
import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.google.web.bindery.autobean.shared.AutoBeanFactory.Category;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;

public class InputInsuranceModel {
	private GeneralFunction function;
	private EbeanServer server;
	private GetSetting setting;
	public InputInsuranceModel(GeneralFunction function) {
		this.function=function;
		this.setting=function.getSetting();
		this.server=function.getServer();
	}
	//mendapatkan satu asuransi;
	public Insurance getSingleInsurance(int idInsurance){
		return server.find(Insurance.class, idInsurance);
		
	}
	//menyimpan barang baru
	public String saveData(FormData data){
		try {
			Insurance insurance=new Insurance();
			insurance.setDescription(data.getDescription());
			insurance.setShowInDropdown(data.isShow());
			insurance.setName(data.getName());
			server.save(insurance);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "Format angka salah";
		} catch (OptimisticLockException e) {
			e.printStackTrace();
			return "Kesalahan dalam penyimpanan data";
		}
		return null;
	}
	//menyimpan hasil perubahan barang
	public String saveEditData(FormData data){
		try {
			Insurance insurance=server.find(Insurance.class, data.getId());
			insurance.setDescription(data.getDescription());
			insurance.setShowInDropdown(data.isShow());
			insurance.setName(data.getName());
			
			server.update(insurance);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "Format angka salah";
		} catch (OptimisticLockException e) {
			e.printStackTrace();
			return "Kesalahan dalam penyimpanan data";
		}
		return null;
	}
	
	
	
}
