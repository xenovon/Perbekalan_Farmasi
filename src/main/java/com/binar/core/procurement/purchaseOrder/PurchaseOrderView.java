package com.binar.core.procurement.purchaseOrder;

import java.util.List;

import com.binar.entity.Goods;

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
	public boolean updateTableData(List<Goods> data);
	public void showDetailWindow(Goods goods);

}
