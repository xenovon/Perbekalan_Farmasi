package com.binar.core.procurement.purchaseOrder.newPurchaseOrder;

import com.binar.core.procurement.purchaseOrder.newPurchaseOrder.NewPurchaseOrderView.NewPurchaseOrderListener;
import com.binar.generalFunction.GeneralFunction;

public class NewPurchaseOrderPresenter implements NewPurchaseOrderListener {

	private GeneralFunction function;
	private NewPurchaseOrderModel model;
	private NewPurchaseOrderViewImpl view;
	public NewPurchaseOrderPresenter(NewPurchaseOrderModel model, 
			NewPurchaseOrderViewImpl view, GeneralFunction function) {
		this.model=model;
		this.view=view;
		this.function=function;
		this.view.init();
		this.view.setComboUserData(model.getUserComboData());
	}
	@Override
	public void buttonClick(String button) {
		if(button.equals("buttonNext")){
			
		}else if(button.equals("buttonBack")){
			
		}else if(button.equals("buttonCreate")){
			
		}else if(button.equals("buttonCheckAll")){
			
		}else if(button.equals("buttonUncheckAll")){
			
		}

	}
	@Override
	public void periodChange() {
	
	}
	
	
	
}
