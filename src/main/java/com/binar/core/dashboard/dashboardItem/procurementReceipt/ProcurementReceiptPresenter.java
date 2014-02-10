package com.binar.core.dashboard.dashboardItem.procurementReceipt;

import java.util.Map;

import com.binar.core.dashboard.dashboardItem.ifrsGoodReceptionSummary.IfrsGoodsReceptionSummaryModel;
import com.binar.core.dashboard.dashboardItem.ifrsGoodReceptionSummary.IfrsGoodsReceptionSummaryView.IfrsGoodsReceptionSummaryListener;
import com.binar.core.dashboard.dashboardItem.ifrsGoodReceptionSummary.IfrsGoodsReceptionSummaryViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class ProcurementReceiptPresenter implements IfrsGoodsReceptionSummaryListener {
/*
 * Summary daftar penerimaan barang (Berapa % dari pengadaan yg udah dilakukan barangny sudah diterima di gudang farmasi?)
> Pie chart

DONE

 */
	IfrsGoodsReceptionSummaryModel model;
	IfrsGoodsReceptionSummaryViewImpl view;
	GeneralFunction function;
	public ProcurementReceiptPresenter(GeneralFunction function
			, IfrsGoodsReceptionSummaryViewImpl view, IfrsGoodsReceptionSummaryModel model) {
		this.model=model;
		this.function=function;
		this.view=view;
		view.init(model.getCurrentMonth());
		view.setListener(this);
		updateChart();
	}
	@Override
	public void buttonGo() {
		Navigator navigator=UI.getCurrent().getNavigator();
		navigator.navigateTo("/inventorymanagement/"+function.VIEW_INVENTORY_RECEPTION);
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
	@Override
	public void updateChart() {
		Map<String, Integer> data=model.getReceptionProcurementCount();
		if(data==null){
			view.setEmptyDataView();
		}else{
			view.generateInformation(data);
			view.generateChart(model.getChartData(data));			
		}
		
		
	}
}
