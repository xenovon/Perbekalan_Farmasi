package com.binar.core.dashboard.dashboardItem.procurementRequirementAcceptance;

import com.binar.core.dashboard.dashboardItem.procurementRequirementAcceptance.ProcurementRequirementAcceptanceView.ProcurementRequirementAcceptanceListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class ProcurementRequirementAcceptancePresenter implements ProcurementRequirementAcceptanceListener {
/*
 * Pengajuan rencana kebutuhan yang sudah disetujui (maksudnya daftar barang yang perlu dibeli)

	DONE
 */
	ProcurementRequirementAcceptanceModel model;
	ProcurementRequirementAcceptanceViewImpl view;
	GeneralFunction function;
	public ProcurementRequirementAcceptancePresenter(GeneralFunction function
			, ProcurementRequirementAcceptanceViewImpl view, ProcurementRequirementAcceptanceModel model) {
		this.model=model;
		this.function=function;
		this.view=view;
		view.init(model.getCurrentMonth());
		view.setListener(this);
		updateTable();
	}
	@Override
	public void updateTable() {
		view.updateTable(model.getReqAcceptedList());
	}
	@Override
	public void buttonGo() {
		Navigator navigator=UI.getCurrent().getNavigator();
		navigator.navigateTo("/requirementplanning/"+function.VIEW_REQ_PLANNING_LIST);
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
