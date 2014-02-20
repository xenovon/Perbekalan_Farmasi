package com.binar.core.dashboard.dashboardItem.farmationExpiredGoodsStatus;

import java.util.List;

import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.vaadin.ui.Panel;

interface  FarmationExpiredGoodsStatusView {
	
	public interface FarmationExpiredGoodsStatusListener{
		public void updateTable();
		public void buttonGo();
	}
	public void init(String month);
	public void construct(String month);
	public void updateTable(List<DeletedGoods> goods);
}
