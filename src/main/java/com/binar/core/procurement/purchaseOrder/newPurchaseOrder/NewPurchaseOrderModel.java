package com.binar.core.procurement.purchaseOrder.newPurchaseOrder;

import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;

public class NewPurchaseOrderModel {
	
	GeneralFunction function;
	EbeanServer server;
	
	public NewPurchaseOrderModel(GeneralFunction function) {
		this.function=function;
		server=function.getServer();
		
	}
	//menghasilkan obyek purchase order dari req planning
	public List<PurchaseOrder> generatePurchaseOrder(List<ReqPlanning> reqPlannings){
		return null;
	}
	
	public boolean saveSinglePurchaseOrder(PurchaseOrder order){
		return true;
	}
	public boolean updateSinglePurchaseOrder(PurchaseOrder order){
		return true;
	}

}
