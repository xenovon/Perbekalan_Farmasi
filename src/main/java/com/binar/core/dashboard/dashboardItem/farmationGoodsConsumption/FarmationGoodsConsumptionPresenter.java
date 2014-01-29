package com.binar.core.dashboard.dashboardItem.farmationGoodsConsumption;

import com.binar.core.dashboard.dashboardItem.farmationGoodsConsumption.FarmationGoodsConsumptionView.FarmationGoodsConsumptionListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class FarmationGoodsConsumptionPresenter implements FarmationGoodsConsumptionListener {
/*
 * Daftar obat paling banyak keluar selama bulan ini :
> Bar chart, nama obat dan jumlahnya
		
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
	 * DataSeries dataSeries = new DataSeries()
	.newSeries()
	.add("none", 23)
	.add("error", 0)
	.add("click", 5)
	.add("impression", 25);

SeriesDefaults seriesDefaults = new SeriesDefaults()
	.setRenderer(SeriesRenderers.PIE)
	.setRendererOptions(
		new PieRenderer()
			.setShowDataLabels(true));

Legend legend = new Legend()
	.setShow(true);

Highlighter highlighter = new Highlighter()
	.setShow(true)
	.setShowTooltip(true)
	.setTooltipAlwaysVisible(true)
	.setKeepTooltipInsideChart(true);

Options options = new Options()
	.setSeriesDefaults(seriesDefaults)
	.setLegend(legend)
	.setHighlighter(highlighter);

DCharts chart = new DCharts()
	.setDataSeries(dataSeries)
	.setOptions(options)
	.show();
	 */
	FarmationGoodsConsumptionModel model;
	FarmationGoodsConsumptionViewImpl view;
	GeneralFunction function;
	public FarmationGoodsConsumptionPresenter(GeneralFunction function
			, FarmationGoodsConsumptionViewImpl view, FarmationGoodsConsumptionModel model) {
		this.model=model;
		this.function=function;
		this.view=view;
		view.init();
		view.setListener(this);
	}
	
	@Override
	public void updateConsumption() {
			// TODO Auto-generated method stub
			
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
