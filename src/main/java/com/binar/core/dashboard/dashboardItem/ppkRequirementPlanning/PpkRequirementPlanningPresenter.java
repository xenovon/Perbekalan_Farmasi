package com.binar.core.dashboard.dashboardItem.ppkRequirementPlanning;

import com.binar.core.dashboard.dashboardItem.ppkGoodsProcurementSummary.PpkGoodsProcurementModel;
import com.binar.core.dashboard.dashboardItem.ppkGoodsProcurementSummary.PpkGoodsProcurementViewImpl;
import com.binar.core.dashboard.dashboardItem.ppkRequirementPlanning.PpkRequirementPlanningView.PpkRequirementPlanningListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class PpkRequirementPlanningPresenter implements PpkRequirementPlanningListener {
 /*
  * 
Pengajuan Rencana Kebutuhan tanggal <<Tanggal Pengajuan>>
<<Nama barang>>
<<Satuan>>
<<Jumlah>>
  */
	PpkRequirementPlanningModel model;
	PpkRequirementPlanningViewImpl view;
	GeneralFunction function;
	public PpkRequirementPlanningPresenter(GeneralFunction function
			, PpkRequirementPlanningViewImpl view, PpkRequirementPlanningModel model) {
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
