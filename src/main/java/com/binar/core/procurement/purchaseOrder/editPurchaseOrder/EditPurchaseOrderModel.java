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
	public PurchaseOrder getPurchaseOrder(int id){
		return server.find(PurchaseOrder.class, id);
	}
	public boolean updatePurchaseOrder(PurchaseOrder order){
		return true;
	}

}
