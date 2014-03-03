package com.binar.core.setting.settingInsurance;

import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.GoodsReception;
import com.binar.entity.Insurance;
import com.binar.entity.InvoiceItem;
import com.binar.entity.PurchaseOrderItem;
import com.binar.entity.ReqPlanning;
import com.binar.entity.SupplierGoods;
import com.binar.generalFunction.GeneralFunction;

public class InsuranceManagementModel {

	GeneralFunction function;
	EbeanServer server;
	public InsuranceManagementModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	public List<Insurance> getInsurance(){
		return server.find(Insurance.class).findList();
	}
	
	public Insurance getSingleInsurance(int idInsurance){
		return server.find(Insurance.class,idInsurance);
	}	
	public String deleteInsurance(int idInsurance){
		try {
			Insurance insurance=server.find(Insurance.class, idInsurance);
			server.delete(insurance); 
		} catch (PersistenceException e) {
			e.printStackTrace();
			return "Data Gagal Dihapus : Barang sudah terhubung dengan "
					+ "data lainnya. Hapus data yang terhubung terlebih dahulu";
		}catch (Exception e){
			e.printStackTrace();
			return "Kesalahan menghapus data : ";
		}
		return null;
	}
}
