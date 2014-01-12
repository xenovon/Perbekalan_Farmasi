package com.binar.core.dashboard.dashboardItem.ppkGoodsProcurementSummary;

import com.binar.core.dashboard.dashboardItem.ppkGoodsProcurementSummary.PpkGoodsProcurementView.PpkGoodsProcurementListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class PpkGoodsProcurementPresenter implements PpkGoodsProcurementListener {
 /*
  * Summary daftar pengadaan barang (Berapa % dari jumlah yg sudah disetujui yg udah dilakukan pengadaan barang?)
> Pie chart

  */
	PpkGoodsProcurementModel model;
	PpkGoodsProcurementViewImpl view;
	GeneralFunction function;
	public PpkGoodsProcurementPresenter(GeneralFunction function
			, PpkGoodsProcurementViewImpl view, PpkGoodsProcurementModel model) {
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
