package com.binar.core.dashboard.dashboardItem.farmationGoodsConsumption;

import com.binar.core.dashboard.dashboardItem.farmationGoodsConsumption.FarmationGoodsConsumptionView.FarmationGoodsConsumptionListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class FarmationGoodsConsumptionPresenter implements FarmationGoodsConsumptionListener {
/*
 * Daftar obat paling banyak keluar selama bulan ini :
> Bar chart, nama obat dan jumlahnya

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
