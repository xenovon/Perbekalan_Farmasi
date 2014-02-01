package com.binar.core.dashboard.dashboardItem.ifrsGoodReceptionSummary;

import java.util.List;
import java.util.Map;

import com.binar.entity.Goods;
import com.vaadin.ui.Panel;

interface  IfrsGoodsReceptionSummaryView {
	
	public interface IfrsGoodsReceptionSummaryListener{
		public void updateChart();
		public void buttonGo();
	}
	public void init();
	public void construct();
	public void setEmptyDataView();

	public void generateChart(Map<Integer, String> data);
}
