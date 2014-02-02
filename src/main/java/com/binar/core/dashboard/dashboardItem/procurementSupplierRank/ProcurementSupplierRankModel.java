package com.binar.core.dashboard.dashboardItem.procurementSupplierRank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.joda.time.LocalDate;

import com.avaje.ebean.EbeanServer;
import com.binar.core.inventoryManagement.ReceptionList;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.GoodsReception;
import com.binar.entity.Supplier;
import com.binar.entity.enumeration.EnumStockStatus;
import com.binar.generalFunction.GeneralFunction;

public class ProcurementSupplierRankModel {

	GeneralFunction function;
	EbeanServer server;
	public ProcurementSupplierRankModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	//menghasilkan daftar supplier dan jumlah transaksi barang dalam 1 3 bulan
	private Map<Supplier, Integer> getSupplierList(){
		
		try {
			//inisialisasi tanggal  3 bulan
			LocalDate baseDate=new LocalDate();
			LocalDate startDate=baseDate.withDayOfMonth(baseDate.dayOfMonth().getMinimumValue()).minusMonths(3);
			LocalDate endDate=baseDate.withDayOfMonth(baseDate.dayOfMonth().getMaximumValue());
			
			List<GoodsReception> receptionList=server.find(GoodsReception.class).where().between("date", startDate, endDate).findList();
			
			
			Map<Supplier, Integer> returnValue=new HashMap<Supplier, Integer>();		
			for (GoodsReception reception : receptionList){
				Supplier supplier=reception.getInvoiceItem().getPurchaseOrderItem().getSupplierGoods().getSupplier();
				if(returnValue.containsKey(supplier)){
					int quantity=returnValue.get(supplier);
					int quantityNow=quantity+reception.getQuantityReceived();
					
					returnValue.remove(supplier);
					returnValue.put(supplier, quantityNow);
				}else{
					returnValue.put(supplier, reception.getQuantityReceived());
				}
			}
			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	/*
	 * Sisanya ganti "lainnya....."
	 */
	public Map<Integer, String> getSupplierTransaction(){
		Map<Supplier, Integer> allGoods=getSupplierList();
		
		SortedMap<Integer, String> returnValue=new TreeMap<Integer, String>();
		SortedMap<Integer, String> buffer=new TreeMap<Integer, String>();
		
		for(Map.Entry<Supplier, Integer> entry:allGoods.entrySet()){
			buffer.put(entry.getValue(), entry.getKey().getSupplierName());
		}
		
		//hanya mengambil 5 supplier dengan konsumsi terbanyak
		//Sorted map sudah mengurutkan supplier dari yang transaksinya paling kecil, hingga paling besar
		int i=0;
		int count=buffer.size()-5;
		int otherCount=0; //untuk menghitung supplier lainnya.
		for(Map.Entry<Integer, String> entry:buffer.entrySet()){
			if(i>=count){
				returnValue.put(entry.getKey(), entry.getValue());
			}else{
				otherCount=otherCount+entry.getKey();
			}
			i++;
		}
		if(otherCount!=0){
			returnValue.put(otherCount, "Lainnya");			
		}
		System.out.println(returnValue.size());
		return returnValue;
	}

	
}
