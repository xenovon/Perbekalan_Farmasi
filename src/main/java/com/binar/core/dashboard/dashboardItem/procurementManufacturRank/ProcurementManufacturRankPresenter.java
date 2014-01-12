package com.binar.core.dashboard.dashboardItem.procurementManufacturRank;

import com.binar.core.dashboard.dashboardItem.procurementManufacturRank.ProcurementManufacturRankView.ProcurementManufacturRankListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class ProcurementManufacturRankPresenter implements ProcurementManufacturRankListener {
/*
 * 
5 Produsen dengan jumlah transaksi terbanyak selama...(blm tau berapa, misal 3 bulan) 
> Grafik batang produsen dan nilai transaksinya

 */
	ProcurementManufacturRankModel model;
	ProcurementManufacturRankViewImpl view;
	GeneralFunction function;
	public ProcurementManufacturRankPresenter(GeneralFunction function
			, ProcurementManufacturRankViewImpl view, ProcurementManufacturRankModel model) {
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
