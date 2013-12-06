package com.binar.core.procurement.purchaseOrder.newPurchaseOrder;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.binar.entity.PurchaseOrder;
import com.binar.entity.ReqPlanning;
import com.vaadin.ui.Window;

public interface NewPurchaseOrderView {

	public enum FormViewEnum{
		VIEW_PERIODE, VIEW_REQPLANNING, VIEW_PO; 
	}
	public interface NewPurchaseOrderListener{
		public void buttonClick(String button);
	}
	
	public void init();
	public void construct();
	public Window getWindow();
	public void setFormView(FormViewEnum formView);
	public void setComboPeriode(Map<String, String> periodeList);
	public String getComboPeriode();
	public Date getPurchaseDate();
	
	public void generateReqPlanningTable(List<ReqPlanning> reqPlanning);
	public List<Integer> getReqPlanningSelected();
	
	public void generatePurchaseOrderListView(List<PurchaseOrder> purchaseOrder);
	public void getPurchaseOrderListData();
}
