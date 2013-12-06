package com.binar.core.procurement.purchaseOrder.newPurchaseOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.PurchaseOrderItem;
import com.binar.entity.ReqPlanning;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.entity.enumeration.EnumPurchaseOrderType;
import com.binar.generalFunction.GeneralFunction;

public class NewPurchaseOrderModel {
	
	GeneralFunction function;
	EbeanServer server;
	
	public NewPurchaseOrderModel(GeneralFunction function) {
		this.function=function;
		server=function.getServer();
	}
	public List<ReqPlanning> getReqPlanning(DateTime periode){
		Date startDate=periode.toDate();
		Date endDate=periode.withDayOfMonth(periode.dayOfMonth().getMaximumValue()).toDate();
		List<ReqPlanning> returnValue=server.find(ReqPlanning.class).where().
				between("period", startDate, endDate).findList();
		return returnValue;
		
	}
	//menghasilkan obyek purchase order dari req planning
	//Disini dilakukan pemisahan antar tiap supplier dan jenis obat
	public List<PurchaseOrder> generatePurchaseOrder(List<ReqPlanning> reqPlannings, Date purchaseDate){
		List<PurchaseOrder> list=new ArrayList<PurchaseOrder>();
		Map<Integer, PurchaseOrder> poGeneral=new HashMap<Integer, PurchaseOrder>();
		
		for(ReqPlanning reqPlanning:reqPlannings){
			EnumGoodsType goodsType=reqPlanning.getSupplierGoods().getGoods().getType();
			
			if(goodsType==EnumGoodsType.NARKOTIKA){
				PurchaseOrder order=new PurchaseOrder();
				order.setDate(purchaseDate);
				order.setPurchaseOrderType(EnumPurchaseOrderType.NARKOTIKA);					
				
				PurchaseOrderItem item=new PurchaseOrderItem();
				item.setPurchaseOrder(order);
				item.setSupplierGoods(reqPlanning.getSupplierGoods());
				item.setQuantity(reqPlanning.getAcceptedQuantity());
				item.setInformation(reqPlanning.getInformation());
				
				List<PurchaseOrderItem> itemList=new ArrayList<PurchaseOrderItem>();
				itemList.add(item);
				
				order.setPurchaseOrderItem(itemList);
				
				list.add(order);
			}else{
				//jika bukan psikotoprika dan narkotika, maka dikelompokan berdasarkan supplier
				int mapKey=reqPlanning.getSupplierGoods().getSupplier().getIdSupplier();
				PurchaseOrderItem item=new PurchaseOrderItem();
				item.setSupplierGoods(reqPlanning.getSupplierGoods());
				item.setQuantity(reqPlanning.getAcceptedQuantity());
				item.setInformation(reqPlanning.getInformation());

				//jika purchase order untuk supplier ini belum ditambahkan 
				if(!poGeneral.containsKey(mapKey)){
					PurchaseOrder order=new PurchaseOrder();
					if(goodsType==EnumGoodsType.PSIKOTROPIKA){
						order.setPurchaseOrderType(EnumPurchaseOrderType.PSIKOTROPIKA);					
					}else{
						order.setPurchaseOrderType(EnumPurchaseOrderType.GENERAL);
					}

					item.setPurchaseOrder(order);
					
					List<PurchaseOrderItem> itemList=new ArrayList<PurchaseOrderItem>();
					itemList.add(item);
					
					order.setPurchaseOrderItem(itemList);
					
					poGeneral.put(mapKey,order);
				}else{
					//jika untuk supplier di req planning udah ada, maka tinggal nambahin item
					PurchaseOrder order=poGeneral.get(mapKey);
					item.setPurchaseOrder(order);
					order.getPurchaseOrderItem().add(item);
				}
			}
		}
		return list;
	}
	public boolean saveSinglePurchaseOrder(PurchaseOrder order){
		return true;
	}
	public boolean updateSinglePurchaseOrder(PurchaseOrder order){
		return true;
	}
	private String generateName(PurchaseOrder purchaseOrder){
		String goodsType=getPurchaseType(purchaseOrder);
		String supplierName=getSupplierName(purchaseOrder);
		String date=function.getDate().dateToText(purchaseOrder.getDate());
		return "Surat Pesanan "+goodsType+", bulan "+date+","
				+ " Untuk Distributor "+supplierName;
	}
	private EnumGoodsType getPurchaseTypeEnum(PurchaseOrder purchaseOrder){
		return purchaseOrder.getPurchaseOrderItem().
				get(0).getSupplierGoods().getGoods().getType();		
	}
	private String getPurchaseType(PurchaseOrder purchaseOrder){
		String goodsType;
		try {
			EnumGoodsType enumGoodsType=purchaseOrder.getPurchaseOrderItem().
					get(0).getSupplierGoods().getGoods().getType();
			if(enumGoodsType==EnumGoodsType.NARKOTIKA ||
					enumGoodsType==EnumGoodsType.PSIKOTROPIKA){
				goodsType=enumGoodsType.toString().toLowerCase();
			}else{
				goodsType="Biasa";
			}
		} catch (Exception e) {
			goodsType="Biasa";
		}
		return goodsType;
	}
	private String getSupplierName(PurchaseOrder purchaseOrder){
		String supplierName;
		try {
			supplierName=purchaseOrder.getPurchaseOrderItem().
					get(0).getSupplierGoods().getSupplier().getSupplierName();
		} catch (Exception e) {
			supplierName="";
		}		
		return supplierName;
	}



}
