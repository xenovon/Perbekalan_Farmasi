package com.binar.core.dashboard.dashboardItem.ifrsGoodProcurement;

import java.util.List;
import java.util.Map;

import com.binar.entity.Goods;
import com.vaadin.ui.Panel;

public interface  IfrsGoodsProcurementView {
	
	public interface IfrsGoodsProcurementListener{
		public void updateChart();
		public void buttonGo();
	}
	public void init(String month);
	public void construct(String month);
	public void setEmptyDataView();
	public void generateChart(Map<String, Integer> data);
}
