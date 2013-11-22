package com.binar.core.dataManagement.goodsManagement.inputEditGoods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.Insurance;
import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.generalFunction.GeneralFunction;
import com.binar.generalFunction.GetSetting;
import com.google.web.bindery.autobean.shared.AutoBeanFactory.Category;
import com.vaadin.ui.Label;

public class InputGoodsModel {
	Label labelId;
	Label labelInsurance;
	Label labelName;
	Label labelDescription;
	Label labelType;
	Label labelUnit;
	Label labelCurrentStock;
	Label labelInformation;
	Label labelPackage;
	Label labelCategory;
	Label labelMinimumStock;
	Label labelIsImportant;
	Label labelHet;

	private GeneralFunction function;
	private EbeanServer server;
	private GetSetting setting;
	public InputGoodsModel(GeneralFunction function) {
		this.function=function;
		this.setting=function.getSetting();
		this.server=function.getServer();
	}
	//mendapatkan data combo box dari asuransi
	public Map<String, String> getInsuranceList(){
		List<Insurance> insuranceList=server.find(Insurance.class).findList();
		Map<String, String> map=new HashMap<String, String>();
		//konversi dari List<Insurance> ke Map<String, String>
		for(Insurance insurance:insuranceList){
			map.put(String.valueOf(insurance.getIdInsurance()), insurance.getName());
		}
		return map;
	}
	//mendapatakan data combo box untuk type
	public Map<EnumGoodsType, String> getGoodsTypeList(){
		Map<EnumGoodsType, String> map=new HashMap<EnumGoodsType, String>();
		for(EnumGoodsType type:EnumGoodsType.values()){
			map.put(type, type.toString());
		}
		return map;
	}
	//mendapatkan data combo box untuk unit
	public Map<String, String> getUnitList(){
		return setting.getUnit();
	}
	//mendapatkan data combo  box untuk package
	public Map<String, String> getPackageList(){
		return setting.getPackage();
	}
	//mendapatkan data combo box untuk kategori barang
	public Map<EnumGoodsCategory, String> getCategoryList(){
		Map<EnumGoodsCategory, String> map=new HashMap<EnumGoodsCategory, String>();
		for(EnumGoodsCategory type:EnumGoodsCategory.values()){
			map.put(type, type.toString());
		}
		return map;
	}
	//mendapatkan satu barang;
	public Goods getSingleGoods(String idGoods){
		return server.find(Goods.class,idGoods);
		
	}
	//menyimpan barang baru
	public String saveData(FormData data){
		Goods goods=new Goods();
		goods.setCategory(data.getCategory());
		return null;
	}
	//menyimpan hasil perubahan barang
	public String saveEditData(FormData data){
		return null;
	}
	
	
	
	
}
