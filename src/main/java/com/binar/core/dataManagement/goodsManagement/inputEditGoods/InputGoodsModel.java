package com.binar.core.dataManagement.goodsManagement.inputEditGoods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.OptimisticLockException;

import com.avaje.ebean.EbeanServer;
import com.binar.core.dataManagement.goodsManagement.inputEditGoods.InputGoodsView.ComboDataList;
import com.binar.core.procurement.PurchaseOrder;
import com.binar.entity.Goods;
import com.binar.entity.GoodsReceipt;
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
	public InputGoodsView.ComboDataList getComboData(){
		ComboDataList data=new ComboDataList();
		data.setInputCategoryList(getCategoryList());
		data.setInputImportantList(getImportantList());
		data.setInputPackageList(getPackageList());
		data.setInputTypeList(getGoodsTypeList());
		data.setInputUnitList(getUnitList());
		data.setInsuranceList(getInsuranceList());
		return data;
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
	public Map<Boolean, String> getImportantList(){
		Map<Boolean,String> map=new HashMap<Boolean, String>();
		map.put(new Boolean(true),"Ya");
		map.put(new Boolean(false), "Tidak");
		return map;
	}
	//mendapatkan satu barang;
	public Goods getSingleGoods(String idGoods){
		return server.find(Goods.class,idGoods);
		
	}
	//menyimpan barang baru
	public String saveData(FormData data){
		try {
			Goods goods=new Goods();
			goods.setCategory(data.getCategory());
			goods.setCurrentStock(data.getInitialStockInt());
			goods.setDescription(data.getDescription());
			goods.setGoodsPackage(data.getGoodsPackage());
			goods.setHet(data.getHETDouble());
			goods.setIdGoods(data.getId());
			goods.setImportant(data.isImportant());
			goods.setInformation(data.getInformation());
			goods.setInsurance(server.find(Insurance.class,  data.getInsurance()));
			goods.setMinimumStock(data.getMinimalStockInt());
			goods.setName(data.getName());
			goods.setType(data.getType());
			goods.setUnit(data.getUnit());
			
			server.save(goods);
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
			Goods goods=server.find(Goods.class, data.getId());
			goods.setCategory(data.getCategory());
			goods.setCurrentStock(data.getInitialStockInt());
			goods.setDescription(data.getDescription());
			goods.setGoodsPackage(data.getGoodsPackage());
			goods.setHet(data.getHETDouble());
			goods.setIdGoods(data.getId());
			goods.setImportant(data.isImportant());
			goods.setInformation(data.getInformation());
			goods.setInsurance(server.find(Insurance.class,  data.getInsurance()));
			goods.setMinimumStock(data.getMinimalStockInt());
			goods.setName(data.getName());
			goods.setType(data.getType());
			goods.setUnit(data.getUnit());
			
			server.update(goods);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "Format angka salah";
		} catch (OptimisticLockException e) {
			e.printStackTrace();
			return "Kesalahan dalam penyimpanan data";
		}
		return null;
	}
	
	//menentukan apakah pengguna bisa mengubah stok awal
	//Stok awal bisa diubah jika belum terdapat penerimaan barang
	//Return true jika stok bisa diubah, return false jika stok tidak dapat dirubah. 
	public boolean isCanEditInitialStock(String idGoods){
		
		List<SupplierGoods> goods=server.find(SupplierGoods.class).where().
								  eq("goods", server.find(Goods.class, idGoods)).findList();
		if(goods.size()!=0){
			List<PurchaseOrderItem> purchaseOrderItems=server.find(PurchaseOrderItem.class).
													   where().in("supplierGoods", goods).findList();
			if(purchaseOrderItems.size()!=0){
				if(purchaseOrderItems.size()>30){ //jika jumlah purchase order ada banyak, maka kemungkinan besar sudah ada penerimaan barang
					return false;
				}else{
					List<InvoiceItem> invoiceItems=server.find(InvoiceItem.class).
							   where().in("purchaseOrderItem", purchaseOrderItems).findList();
					if(invoiceItems.size()!=0){
						List<GoodsReceipt> receiptList=server.find(GoodsReceipt.class).
													   where().in("invoiceItem", invoiceItems).findList();
						if(receiptList.size()!=0){
							return false;
						}else{
							return true;
						}
					}else{
						return true;
					}
				}
			}else{
				return true;
			}
		}else{
			return true;
		}
				
	}
	
	
	
}
