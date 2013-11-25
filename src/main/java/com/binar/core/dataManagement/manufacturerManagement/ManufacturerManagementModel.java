package com.binar.core.dataManagement.manufacturerManagement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.Manufacturer;
import com.binar.entity.Supplier;
import com.binar.entity.SupplierGoods;
import com.binar.generalFunction.GeneralFunction;

public class ManufacturerManagementModel {

	GeneralFunction function;
	EbeanServer server;
	public ManufacturerManagementModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	
	public List<Manufacturer>  getManufacturer(){
		if(server==null){
			System.out.println("Server null");
		}
		List<Manufacturer> manufacturers=server.find(Manufacturer.class).findList();
		return manufacturers;
	}
	public Manufacturer getSingleManufacturer(int idManufacturer){
		return server.find(Manufacturer.class, idManufacturer);
	}
	
	public String deleteManufacturer(String idManufacturer){
		try {
			Manufacturer manufacturer=server.find(Manufacturer.class, idManufacturer);
			List<SupplierGoods> supplierGoods=server.find(SupplierGoods.class)
					.where().eq("manufacturer", manufacturer).select("idSupplierGoods").findList();
			server.delete(supplierGoods);
			server.delete(manufacturer); 
		} catch (PersistenceException e) {
			e.printStackTrace();
			return "Data Gagal Dihapus : Data produsen sudah terhubung dengan "
					+ "data lainnya. Hapus data yang terhubung terlebih dahulu";
		}catch (Exception e){
			e.printStackTrace();
			return "Kesalahan menghapus data : "+ e.getMessage();
		}
		return null;
	}

	public String getGoodsList(int idManufacturer){
		Manufacturer manufacturer=getSingleManufacturer(idManufacturer);
		Set<String> goods=new HashSet<String>();
		List<SupplierGoods> manySupplierGoods=server.find(SupplierGoods.class).where().eq("manufacturer",manufacturer).findList();
		System.out.println("Many supplier goods"+manySupplierGoods.size());
		for(SupplierGoods supplierGoods:manySupplierGoods){
			goods.add(supplierGoods.getGoods().getName());
		}
		if(goods.size()==0){
			return "<i>Belum ada barang terdaftar untuk produsen ini</i> ";
		}
		String rValue="Daftar Barang : </br><li>";
		for(String g:goods){
			rValue=rValue+"<ul style='padding-left:10px'>"+g+"</ul>";
		}
		rValue=rValue+"</li>";
		
		return rValue;
	}
}
