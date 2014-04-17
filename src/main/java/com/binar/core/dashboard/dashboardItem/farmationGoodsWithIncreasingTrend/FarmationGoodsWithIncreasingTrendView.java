package com.binar.core.dashboard.dashboardItem.farmationGoodsWithIncreasingTrend;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.binar.entity.Goods;
import com.vaadin.ui.Panel;

public interface  FarmationGoodsWithIncreasingTrendView {
	public interface FarmationGoodsWithIncreasingTrendListener{
		public void updateChart(DateTime dateStart, int period);
		public void buttonGo();
		public void buttonSubmit();
	}
	public void init(String month);
	public void construct(String month);
	public void setEmptyDataView();
	public void generateChart(Map<String, List<Integer>> data);
}
