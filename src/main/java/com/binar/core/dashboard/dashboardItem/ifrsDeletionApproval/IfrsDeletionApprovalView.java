package com.binar.core.dashboard.dashboardItem.ifrsDeletionApproval;

import java.util.List;

import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.vaadin.ui.Panel;

public interface  IfrsDeletionApprovalView {
	
	public interface IfrsDeletionApprovalListener{
		public void updateTable();
		public void buttonGo();
	}
	public void init(String month);
	public void construct(String month);
	public void updateTable(List<DeletedGoods> deletedGoods);
}
