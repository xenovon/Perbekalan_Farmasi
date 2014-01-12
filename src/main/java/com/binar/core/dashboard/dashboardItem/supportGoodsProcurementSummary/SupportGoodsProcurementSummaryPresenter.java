package com.binar.core.dashboard.dashboardItem.supportGoodsProcurementSummary;

import com.binar.core.dashboard.dashboardItem.supportGoodsProcurementSummary.SupportGoodsProcurementSummaryView.SupportGoodsProcurementSummaryListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class SupportGoodsProcurementSummaryPresenter implements SupportGoodsProcurementSummaryListener {
/*
 * Summary daftar pengadaan barang (Berapa % dari jumlah yg sudah disetujui yg udah dilakukan pengadaan barang?)
> Pie chart

 */
	SupportGoodsProcurementSummaryModel model;
	SupportGoodsProcurementSummaryViewImpl view;
	GeneralFunction function;
	public SupportGoodsProcurementSummaryPresenter(GeneralFunction function
			, SupportGoodsProcurementSummaryViewImpl view, SupportGoodsProcurementSummaryModel model) {
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
