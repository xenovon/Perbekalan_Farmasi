package com.binar.core.dashboard.dashboardItem.ifrsDeletionApproval;

import com.binar.core.dashboard.dashboardItem.ifrsDeletionApproval.IfrsDeletionApprovalView.IfrsDeletionApprovalListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class IfrsDeletionApprovalPresenter implements IfrsDeletionApprovalListener {
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
	public IfrsDeletionApprovalPresenter(GeneralFunction function
			, IfrsDeletionApprovalViewImpl view, IfrsDeletionApprovalModel model) {
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