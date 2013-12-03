package com.binar.core.procurement.purchaseOrder.showPurchaseOrder;

import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderView.NewPurchaseOrderListener;
import com.binar.generalFunction.GeneralFunction;

public class ShowPurchaseOrderPresenter implements NewPurchaseOrderListener {

	private GeneralFunction function;
	private ShowPurchaseOrderModel model;
	private ShowPurchaseOrderViewImpl view;
	public ShowPurchaseOrderPresenter(ShowPurchaseOrderModel model, 
			ShowPurchaseOrderViewImpl view, GeneralFunction function) {
	}
}
