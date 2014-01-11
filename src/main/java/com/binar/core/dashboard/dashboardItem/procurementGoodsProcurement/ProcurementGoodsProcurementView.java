package com.binar.core.dashboard.dashboardItem.procurementGoodsProcurement;

import java.util.List;

import com.binar.entity.Goods;
import com.vaadin.ui.Panel;

interface  ProcurementGoodsProcurementView {
	
	public interface ProcurementGoodsProcurementListener{
		public void updateTable();
		public void buttonGo();
	}
	public void init();
	public void construct();
	public void updateTable(List<Goods> goods);
}
