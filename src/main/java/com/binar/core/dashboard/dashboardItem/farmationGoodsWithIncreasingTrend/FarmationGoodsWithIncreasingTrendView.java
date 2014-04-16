package com.binar.core.dashboard.dashboardItem.farmationGoodsWithIncreasingTrend;

import java.util.List;
import java.util.Map;

import com.binar.entity.Goods;
import com.vaadin.ui.Panel;

public interface  FarmationGoodsWithIncreasingTrendView {
	public interface FarmationGoodsWithIncreasingTrendListener{
		public void updateChart();
		public void buttonGo();
	}
	public void init(String month);
	public void construct(String month);
	public void setEmptyDataView();
	public void generateChart(Map<String, List<Integer>> data);
}
