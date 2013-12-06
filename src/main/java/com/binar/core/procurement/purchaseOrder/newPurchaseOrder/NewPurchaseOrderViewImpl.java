package com.binar.core.procurement.purchaseOrder.newPurchaseOrder;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.binar.entity.PurchaseOrder;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Window;

public class NewPurchaseOrderViewImpl implements NewPurchaseOrderView{

	GeneralFunction function;
	
	public NewPurchaseOrderViewImpl(GeneralFunction function) {
		this.function=function;
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void construct() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Window getWindow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFormView(FormViewEnum formView) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setComboPeriode(Map<String, String> periodeList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getComboPeriode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getPurchaseDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateReqPlanningTable(List<ReqPlanning> reqPlanning) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Integer> getReqPlanningSelected() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generatePurchaseOrderListView(List<PurchaseOrder> purchaseOrder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getPurchaseOrderListData() {
		// TODO Auto-generated method stub
		
	}

}
