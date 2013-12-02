package com.binar.core.procurement.purchaseOrder;

import java.util.List;

import com.binar.entity.Goods;
import com.binar.entity.PurchaseOrder;

interface PurchaseOrderView {
	
	interface PurchaseOrderListener{
		public void buttonClick(String buttonName);
		public void editClick(String idGoods);
		public void deleteClick(String idGoods);
		public void showClick(String idGoods);
	
	}
	public void init();
	public void construct();
	public void setListener(PurchaseOrderListener listener);
	public boolean updateTableData(List<PurchaseOrder> data);
	public void showDetailWindow(PurchaseOrder purchaseOrder);

}
