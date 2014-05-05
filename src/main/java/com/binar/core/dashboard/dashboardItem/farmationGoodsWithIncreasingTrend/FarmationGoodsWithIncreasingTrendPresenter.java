package com.binar.core.dashboard.dashboardItem.farmationGoodsWithIncreasingTrend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Months;

import com.binar.core.dashboard.dashboardItem.farmationGoodsWithIncreasingTrend.FarmationGoodsWithIncreasingTrendView.FarmationGoodsWithIncreasingTrendListener;
import com.binar.entity.Goods;
import com.binar.generalFunction.DateManipulator;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class FarmationGoodsWithIncreasingTrendPresenter implements FarmationGoodsWithIncreasingTrendListener {
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
	DateManipulator dateMan;
	public FarmationGoodsWithIncreasingTrendPresenter(GeneralFunction function
			, FarmationGoodsWithIncreasingTrendViewImpl view, FarmationGoodsWithIncreasingTrendModel model) {
		this.model=model;
		this.function=function;
		this.view=view;
		this.dateMan=function.getDate();
		view.init(model.getCurrentMonth());
		view.setListener(this);
		buttonSubmit();
	}
	

	@Override
	public void updateChart(DateTime dateStart, int period) {
//		Map<String, List<Integer>> data=model.getChartDataDummy(period);
		Map<String, List<Integer>> data=model.getChartData(dateStart, period);
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
	@Override
	public void buttonSubmit() {
		String[] date=view.getSubmitData();
		System.out.println(date.toString());
		DateTime dateStart=dateMan.parseDateMonth(date[0]);
		DateTime dateEnd=dateMan.parseDateMonth(date[1]);
		
		//mengambil period bulan yang dipilih
		int period=Math.abs(Months.monthsBetween(dateStart, dateEnd).getMonths());

		//untuk date yang akan diteruskan ke model
		DateTime dateSelected;		
		if(dateStart.isAfter(dateEnd)){
			dateSelected=dateStart;
		}else{
			dateSelected=dateEnd;
		}
		
		updateChart(dateSelected, period);
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

	public static void main(String[] args) {
		DateTime a=new DateTime();
		DateTime b=a.minusMonths(8);
		
		System.out.println(Months.monthsBetween(a, b).getMonths());
		System.out.println(Months.monthsBetween(b, a).getMonths());
	}
}

	
