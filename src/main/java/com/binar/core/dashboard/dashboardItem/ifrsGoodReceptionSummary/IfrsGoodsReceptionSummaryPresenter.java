package com.binar.core.dashboard.dashboardItem.ifrsGoodReceptionSummary;

import com.binar.core.dashboard.dashboardItem.ifrsGoodProcurement.IfrsGoodsProcurementViewImpl;
import com.binar.core.dashboard.dashboardItem.ifrsGoodReceptionSummary.IfrsGoodsReceptionSummaryView.IfrsGoodsReceptionSummaryListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class IfrsGoodsReceptionSummaryPresenter implements IfrsGoodsReceptionSummaryListener {
/*
 * Summary daftar penerimaan barang (Berapa % dari rencana kebutuhan yg udah disetujui barangny sudah diterima di gudang farmasi?)
> Pie chart

 */
	IfrsGoodsReceptionSummaryModel model;
	IfrsGoodsReceptionSummaryViewImpl view;
	GeneralFunction function;
	public IfrsGoodsReceptionSummaryPresenter(GeneralFunction function
			, IfrsGoodsReceptionSummaryViewImpl view, IfrsGoodsReceptionSummaryModel model) {
		this.model=model;
		this.function=function;
		this.view=view;
		view.init();
		view.setListener(this);
		
		
	}
	@Override
	public void updateTable() {
		view.updateTable(model.getGoodsList());
	}
	@Override
	public void buttonGo() {
		Navigator navigator=UI.getCurrent().getNavigator();
		navigator.navigateTo("/datamanagement/"+function.VIEW_SUPPLIER_MANAGEMENT);
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
