package com.binar.core.procurement.purchaseOrder;

import com.binar.core.procurement.purchaseOrder.PurchaseOrderView.PurchaseOrderListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Notification;

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
		if(buttonName.equals("buttonNewPurchase")){
			Notification.show("New Surat Pesanan");
		}
	}
	@Override
	public void editClick(int idPurchaseOrder) {
		Notification.show("edit Surat Pesanan"+idPurchaseOrder);
		
	}
	@Override
	public void deleteClick(int idPurchaseOrder) {
		Notification.show("delete Surat Pesanan" +idPurchaseOrder);
		
	}
	@Override
	public void showClick(int idPurchaseOrder) {
		Notification.show("show Surat Pesanan" +idPurchaseOrder);
		
	}
	@Override
	public void valueChange(String value) {
		if(value.equals("")){
			
		}
	}

}
