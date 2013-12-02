package com.binar.core.procurement;

import com.binar.core.dataManagement.goodsManagement.GoodsManagementModel;
import com.binar.core.dataManagement.goodsManagement.GoodsManagementPresenter;
import com.binar.core.dataManagement.goodsManagement.GoodsManagementViewImpl;
import com.binar.core.procurement.purchaseOrder.PurchaseOrderModel;
import com.binar.core.procurement.purchaseOrder.PurchaseOrderPresenter;
import com.binar.core.procurement.purchaseOrder.PurchaseOrderViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class PurchaseOrder extends CssLayout {

	
	private GeneralFunction function;
	private PurchaseOrderModel model;
	private PurchaseOrderPresenter presenter;
	private PurchaseOrderViewImpl view;
	
	
	public PurchaseOrder(GeneralFunction function) {
		this.function=function;
		model=new PurchaseOrderModel(function);
		view=new PurchaseOrderViewImpl(function);
		presenter=new PurchaseOrderPresenter(view, model, function);
		this.setCaption("Surat Pesanan");
		this.addComponent(view);
		this.addStyleName("tab-content");

	}
	
	/*
	 * 	private GeneralFunction function;
	private GoodsManagementModel model;
	private GoodsManagementViewImpl view;
	private GoodsManagementPresenter presenter;
	
	public GoodsManagement(GeneralFunction function) {
		this.function=function;
		model=new GoodsManagementModel(function);
		view=new GoodsManagementViewImpl(function);
		
		presenter=new GoodsManagementPresenter(model, view, function);
		
		this.addComponent(view);
		this.setCaption("Manajemen Barang");
		this.setSizeFull();
		this.setStyleName("tab-content");
		
	}
	
	public GoodsManagementPresenter getPresenter(){
		return presenter;
	}
	public GoodsManagementViewImpl getView() {
		return view;
	}

	 */
}
