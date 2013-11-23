package com.binar.core.dataManagement.goodsManagement;

import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.binar.entity.GoodsReceipt;
import com.binar.entity.InvoiceItem;
import com.binar.entity.PurchaseOrderItem;
import com.binar.entity.ReqPlanning;
import com.binar.entity.SupplierGoods;
import com.binar.generalFunction.GeneralFunction;

public class GoodsManagementModel {

	GeneralFunction function;
	EbeanServer server;
	public GoodsManagementModel(GeneralFunction function) {
		this.function=function;
		this.server=function.getServer();
	}
	public List<Goods> getGoods(){
		return server.find(Goods.class).findList();
	}
	
	public Goods getSingleGoods(String idGoods){
		return server.find(Goods.class,idGoods);
	}	
	public String deleteGoods(String idGoods){
		try {
			Goods goods=server.find(Goods.class, idGoods);
			List<SupplierGoods> supplierGoods=server.find(SupplierGoods.class)
					.where().eq("goods", goods).select("idSupplierGoods").findList();
			server.delete(supplierGoods);
			server.delete(goods); 
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
	//menghapus data yang terkait dengan barang ini
	public String forcedDeleteGoods(String goods){
		server.beginTransaction();
		try {
			List<GoodsConsumption> goodsConsumption=server.find(GoodsConsumption.class).
					where().eq("goods", getSingleGoods(goods)).select("idGoodsConsumption").findList();
			server.delete(goodsConsumption);

			List<DeletedGoods> deleteGoods=server.find(DeletedGoods.class).where().
					eq("goods", goods).select("idDeletedGoods").findList();
			server.delete(deleteGoods);
			
			
			List<SupplierGoods> supplierGoodsList=server.find(SupplierGoods.class).where().
					eq("goods", goods).select("idSupplierGoods").findList();
			
			List<ReqPlanning>  reqPlanningList=server.find(ReqPlanning.class).
					where().in("supplierGoods", supplierGoodsList).select("idReqPlanning").findList();
			
			List<PurchaseOrderItem> purchaseOrderItem=server.find(PurchaseOrderItem.class).
					where().in("supplierGoods", supplierGoodsList).select("idPurchaseOrderItem").findList();
			
			List<InvoiceItem> invoiceItem=server.find(InvoiceItem.class).
							where().in("purchaseOrderItem", purchaseOrderItem).select("idInvoiceItem").findList();
			
			List<GoodsReceipt> receiptItem=server.find(GoodsReceipt.class).where().
					in("invoiceItem", invoiceItem).select("idGoodsReceipt").findList();
			
			server.delete(receiptItem);
			server.delete(invoiceItem);
			server.delete(purchaseOrderItem);
			server.delete(reqPlanningList);
			server.delete(supplierGoodsList);
			server.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			server.rollbackTransaction();
			return "Kesalahan penghapusan : "+e.getMessage();
		} finally{
			server.endTransaction();
		}
		return null;
	}
}
