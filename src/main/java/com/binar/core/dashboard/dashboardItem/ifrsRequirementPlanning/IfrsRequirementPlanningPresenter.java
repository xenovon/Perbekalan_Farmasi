package com.binar.core.dashboard.dashboardItem.ifrsRequirementPlanning;

import com.binar.core.dashboard.dashboardItem.farmationRequirementStatus.FarmationRequirementStatusModel;
import com.binar.core.dashboard.dashboardItem.farmationRequirementStatus.FarmationRequirementStatusView.FarmationRequirementStatusListener;
import com.binar.core.dashboard.dashboardItem.farmationRequirementStatus.FarmationRequirementStatusViewImpl;
import com.binar.core.dashboard.dashboardItem.supportRequirementNonApproved.SupportRequirementNonApprovedModel;
import com.binar.core.dashboard.dashboardItem.supportRequirementNonApproved.SupportRequirementNonApprovedView.SupportRequirementNonApprovedListener;
import com.binar.core.dashboard.dashboardItem.supportRequirementNonApproved.SupportRequirementNonApprovedViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class IfrsRequirementPlanningPresenter implements SupportRequirementNonApprovedListener{
/*
 * Pengajuan rencana kebutuhan yang belum disetujui 
Format :

Pengajuan Rencana Kebutuhan tanggal <<Tanggal Pengajuan>>
<<Nama barang>>
<<Satuan>>
<<Jumlah>>
DONE  
 */
	SupportRequirementNonApprovedModel model;
	SupportRequirementNonApprovedViewImpl view;
	GeneralFunction function;
	public IfrsRequirementPlanningPresenter(GeneralFunction function
			, SupportRequirementNonApprovedViewImpl view, SupportRequirementNonApprovedModel model) {
		this.model=model;
		this.function=function;
		this.view=view;
		view.init();
		view.setListener(this);
		
		
	}
	@Override
	public void updateTable() {
		view.updateTable(model.getReqList());
	}
	@Override
	public void buttonGo() {
		Navigator navigator=UI.getCurrent().getNavigator();
		navigator.navigateTo("/requirementplanning/"+function.VIEW_REQ_PLANNING_APPROVAL);
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
