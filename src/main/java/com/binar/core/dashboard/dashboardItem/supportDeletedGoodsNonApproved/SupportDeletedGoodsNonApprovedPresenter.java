package com.binar.core.dashboard.dashboardItem.supportDeletedGoodsNonApproved;

import com.binar.core.dashboard.dashboardItem.farmationRequirementStatus.FarmationRequirementStatusModel;
import com.binar.core.dashboard.dashboardItem.farmationRequirementStatus.FarmationRequirementStatusViewImpl;
import com.binar.core.dashboard.dashboardItem.ifrsDeletionApproval.IfrsDeletionApprovalModel;
import com.binar.core.dashboard.dashboardItem.ifrsDeletionApproval.IfrsDeletionApprovalView.IfrsDeletionApprovalListener;
import com.binar.core.dashboard.dashboardItem.ifrsDeletionApproval.IfrsDeletionApprovalViewImpl;
import com.binar.core.dashboard.dashboardItem.ifrsGoodProcurement.IfrsGoodsProcurementViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class SupportDeletedGoodsNonApprovedPresenter implements IfrsDeletionApprovalListener {
/*
 * Daftar barang kadaluarsa yang belum disetujui

Format :
Pengajuan barang kadaluarsa tanggal <<Tanggal Pengajuan>>
<<Nama barang>>
<<Satuan>>
<<Jumlah>>
 */
	IfrsDeletionApprovalModel model;
	IfrsDeletionApprovalViewImpl view;
	GeneralFunction function;
	public SupportDeletedGoodsNonApprovedPresenter(GeneralFunction function
			, IfrsDeletionApprovalViewImpl view, IfrsDeletionApprovalModel model) {
		this.model=model;
		this.function=function;
		this.view=view;
		view.init();
		view.setListener(this);
		updateTable();
	}
	@Override
	public void updateTable() {
		view.updateTable(model.getDeletedGoodsList());
	}
	@Override
	public void buttonGo() {
		Navigator navigator=UI.getCurrent().getNavigator();
		navigator.navigateTo("/inventorymanagement/"+function.VIEW_INVENTORY_DELETION_APPROVAL);
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
