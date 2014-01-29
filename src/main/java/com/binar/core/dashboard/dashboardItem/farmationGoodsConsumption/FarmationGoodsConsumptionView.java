package com.binar.core.dashboard.dashboardItem.farmationGoodsConsumption;

import java.util.List;

import com.binar.entity.Goods;
import com.vaadin.ui.Panel;

interface  FarmationGoodsConsumptionView {
	public interface FarmationGoodsConsumptionListener{
		public void updateConsumption();
		public void buttonGo();
	}
	public void init();
	public void construct();
	public void updateTable(List<Goods> goods);
}
