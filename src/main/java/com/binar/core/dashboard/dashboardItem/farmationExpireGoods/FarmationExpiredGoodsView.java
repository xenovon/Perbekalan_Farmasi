package com.binar.core.dashboard.dashboardItem.farmationExpireGoods;

import java.util.List;

import com.binar.entity.Goods;
import com.binar.entity.GoodsReception;
import com.vaadin.ui.Panel;

interface  FarmationExpiredGoodsView {
	
	public interface FarmationExpiredGoodsListener{
		public void updateTable();
		public void buttonGo();
	}
	public void init();
	public void construct();
	public void updateTable(List<GoodsReception> goods);
}
