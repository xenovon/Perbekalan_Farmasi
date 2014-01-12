package com.binar.core.dashboard.dashboardItem.procurementGoodsProcurement;

import com.binar.core.dashboard.dashboardItem.procurementGoodsProcurement.ProcurementGoodsProcurementView.ProcurementGoodsProcurementListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class ProcurementGoodsProcurementPresenter implements ProcurementGoodsProcurementListener {
/*
 * Summary daftar pengadaan barang (Berapa % dari jumlah yg sudah disetujui yg udah dilakukan pengadaan barang?)
> Pie chart

 */
	ProcurementGoodsProcurementModel model;
	ProcurementGoodsProcurementViewImpl view;
	GeneralFunction function;
	public ProcurementGoodsProcurementPresenter(GeneralFunction function
			, ProcurementGoodsProcurementViewImpl view, ProcurementGoodsProcurementModel model) {
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
