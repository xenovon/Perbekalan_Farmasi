package com.binar.core.dashboard.dashboardItem.farmationMinimumStock;

import com.binar.core.dashboard.dashboardItem.farmationMinimumStock.FarmationMinumumStockView.FarmationMinimumStockListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class FarmationMinimumStockPresenter implements FarmationMinimumStockListener {

	/*
	 * Barang dengan stok mendekati minimum
> Nama barang, jumlah stok, satuan

UDah
	 */
			
	FarmationMinimumStockModel model;
	FarmationMinimumStockViewImpl view;
	GeneralFunction function;
	public FarmationMinimumStockPresenter(GeneralFunction function
			, FarmationMinimumStockViewImpl view, FarmationMinimumStockModel model) {
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
		navigator.navigateTo("/datamanagement/"+function.VIEW_GOODS_MANAGEMENT);
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
