package com.binar.core.dashboard.dashboardItem.ifrsDeletionApproval;

import java.util.List;

import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.vaadin.ui.Panel;

interface  IfrsDeletionApprovalView {
	
	public interface IfrsDeletionApprovalListener{
		public void updateTable();
		public void buttonGo();
	}
	public void init();
	public void construct();
	public void updateTable(List<DeletedGoods> deletedGoods);
}
