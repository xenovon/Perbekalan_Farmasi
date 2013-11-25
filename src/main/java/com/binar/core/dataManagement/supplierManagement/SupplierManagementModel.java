package com.binar.core.dataManagement.supplierManagement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.Goods;
import com.binar.entity.Supplier;
import com.binar.entity.SupplierGoods;
import com.binar.generalFunction.GeneralFunction;

public class SupplierManagementModel {

	GeneralFunction function;
	EbeanServer server;
	public SupplierManagementModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	
	public List<Supplier>  getSuppliers(){
		if(server==null){
			System.out.println("Server null");
		}
		List<Supplier> suppliers=server.find(Supplier.class).findList();
		return suppliers;
	}
	public Supplier getSingleSupplier(int idSupplier){
		return server.find(Supplier.class, idSupplier);
	}
	
	public String deleteSupplier(String idSupplier){
		try {
			Supplier supplier=server.find(Supplier.class, idSupplier);
			List<SupplierGoods> supplierGoods=server.find(SupplierGoods.class)
					.where().eq("supplier", supplier).select("idSupplierGoods").findList();
			server.delete(supplierGoods);
			server.delete(supplier); 
		} catch (PersistenceException e) {
			e.printStackTrace();
			return "Data Gagal Dihapus : Data supplier sudah terhubung dengan "
					+ "data lainnya. Hapus data yang terhubung terlebih dahulu";
		}catch (Exception e){
			e.printStackTrace();
			return "Kesalahan menghapus data : "+ e.getMessage();
		}
		return null;
	}

	public String getGoodsList(int idSupplier){
		Supplier supplier=getSingleSupplier(idSupplier);
		Set<String> goods=new HashSet<String>();
		List<SupplierGoods> manySupplierGoods=server.find(SupplierGoods.class).where().eq("supplier",supplier).findList();
		System.out.println("Many supplier goods"+manySupplierGoods.size());
		for(SupplierGoods supplierGoods:manySupplierGoods){
			goods.add(supplierGoods.getGoods().getName());
		}
		if(goods.size()==0){
			return "<i>Belum ada barang terdaftar untuk distributor ini</i> ";
		}
		String rValue="Daftar Barang : </br><li>";
		for(String g:goods){
			rValue=rValue+"<ul style='padding-left:10px'>"+g+"</ul>";
		}
		rValue=rValue+"</li>";
		
		return rValue;
	}
}
