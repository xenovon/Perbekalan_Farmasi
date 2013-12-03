package com.binar.core.procurement.purchaseOrder.editPurchaseOrder;

import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;

public class EditPurchaseOrderModel {
	
	GeneralFunction function;
	EbeanServer server;
	
	public EditPurchaseOrderModel(GeneralFunction function) {
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
