package com.binar.core.dashboard.dashboardItem.ifrsGoodsWithIncreasingTrend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.binar.core.dashboard.dashboardItem.farmationGoodsWithIncreasingTrend.FarmationGoodsWithIncreasingTrendModel;
import com.binar.core.dashboard.dashboardItem.farmationGoodsWithIncreasingTrend.FarmationGoodsWithIncreasingTrendView.FarmationGoodsWithIncreasingTrendListener;
import com.binar.core.dashboard.dashboardItem.farmationGoodsWithIncreasingTrend.FarmationGoodsWithIncreasingTrendViewImpl;
import com.binar.entity.Goods;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class IfrsGoodsWithIncreasingTrendPresenter implements FarmationGoodsWithIncreasingTrendListener {
/*
 * 5 Obat yang trendnya naik
> Line Chart, nama obat dan jumlahnya
		
		DataSeries dataSeries = new DataSeries()
	.newSeries()
	.add("none", 23)
	.add("error", 0)
	.add("click", 5)
	.add("impression", 25);

SeriesDefaults seriesDefaults = new SeriesDefaults()
	.setRenderer(SeriesRenderers.PIE);

Options options = new Options()
	.setSeriesDefaults(seriesDefaults);

DCharts chart = new DCharts()
	.setDataSeries(dataSeries)
	.setOptions(options)
	.show();
		BELUM
 */
	
	
	/*
	 * 
	 */
	FarmationGoodsWithIncreasingTrendModel model;
	FarmationGoodsWithIncreasingTrendViewImpl view;
	GeneralFunction function;
	public IfrsGoodsWithIncreasingTrendPresenter(GeneralFunction function
			, FarmationGoodsWithIncreasingTrendViewImpl view, FarmationGoodsWithIncreasingTrendModel model) {
		this.model=model;
		this.function=function;
		this.view=view;
		view.init(model.getCurrentMonth());
		view.setListener(this);
		updateChart();
	}
	

	@Override
	public void updateChart() {
//		Map<String, List<Integer>> data=model.getChartDataDummy();
		Map<String, List<Integer>> data=model.getChartData();
		if(data==null){
			view.setEmptyDataView();
		}else{
			view.generateChart(data);			
		}
	}
	
	@Override
	public void buttonGo() {
		Navigator navigator=UI.getCurrent().getNavigator();
		navigator.navigateTo("/inventorymanagement/"+function.VIEW_INVENTORY_CONSUMPTION);
	}
//  put("/dashboard", DashboardView.class);
//  put("/requirementplanning/", RequirementPlanningView.class);
//  put("/procurement", ProcurementView.class);
//  put("/inventorymanagement", InventoryManagementView.class);
//  put("/report", ReportView.class);
//  put("/datamanagement", DataManagementView.class);
//  put("/usermanagement", UserManagementView.class);
//  put("/setting", SettingView.class);
//  put("/usersetting", UserSettingView.class);

}
