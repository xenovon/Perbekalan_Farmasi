package com.binar.core.dashboard.dashboardItem.ifrsGoodProcurement;

import com.binar.core.dashboard.dashboardItem.ifrsGoodProcurement.IfrsGoodsProcurementView.IfrsGoodsProcurementListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class IfrsGoodsProcurementPresenter implements IfrsGoodsProcurementListener {
/*
 * Summary daftar pengadaan barang (Berapa % dari jumlah yg sudah disetujui yg udah dilakukan pengadaan barang?)
> Pie chart
	Belum
 */
	IfrsGoodsProcurementModel model;
	IfrsGoodsProcurementViewImpl view;
	GeneralFunction function;
	public IfrsGoodsProcurementPresenter(GeneralFunction function
			, IfrsGoodsProcurementViewImpl view, IfrsGoodsProcurementModel model) {
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
		navigator.navigateTo("/datamanagement/"+function.VIEW_PROCUREMENT_PURCHASE);
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
