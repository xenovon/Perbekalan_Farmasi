package com.binar.core.procurement.purchaseOrder;

import java.util.List;

import com.binar.entity.Goods;
import com.binar.entity.PurchaseOrder;

interface PurchaseOrderView {
	
	interface PurchaseOrderListener{
		public void buttonClick(String buttonName);
		public void editClick(int idPurchaseOrder);
		public void deleteClick(int idPurchaseOrder);
		public void showClick(int idPurchaseOrder);
		public void valueChange(String value);
		public void updateTable();
	
	}
	public void init();
	public void construct();
	public void setListener(PurchaseOrderListener listener);
	public boolean updateTableData(List<PurchaseOrder> data, boolean withEditPurchaseOrder);
	public void showDetailWindow(PurchaseOrder purchaseOrder);
	public String getSelectedPeriod();

}
