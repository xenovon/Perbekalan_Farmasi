package com.binar.core.procurement.purchaseOrder.editPurchaseOrder;

import com.binar.core.procurement.purchaseOrder.editPurchaseOrder.EditPurchaseOrderView.EditPurchaseOrderListener;
import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderView.NewPurchaseOrderListener;
import com.binar.generalFunction.GeneralFunction;

public class EditPurchaseOrderPresenter implements EditPurchaseOrderListener {

	private GeneralFunction function;
	private EditPurchaseOrderModel model;
	private EditPurchaseOrderViewImpl view;
	public EditPurchaseOrderPresenter(EditPurchaseOrderModel model, 
			EditPurchaseOrderViewImpl view, GeneralFunction function) {
	}
}