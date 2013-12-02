package com.binar.core.procurement.purchaseOrder;

import com.binar.core.procurement.purchaseOrder.PurchaseOrderView.PurchaseOrderListener;
import com.binar.generalFunction.GeneralFunction;

public class PurchaseOrderPresenter implements PurchaseOrderListener {

	PurchaseOrderModel model;
	PurchaseOrderViewImpl view;
	GeneralFunction function;
	public PurchaseOrderPresenter(PurchaseOrderViewImpl view, PurchaseOrderModel model, GeneralFunction function) {
		this.function=function;
		this.model=model;
		this.view=view;
	}
	@Override
	public void buttonClick(String buttonName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void editClick(String idGoods) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteClick(String idGoods) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void showClick(String idGoods) {
		// TODO Auto-generated method stub
		
	}
}
