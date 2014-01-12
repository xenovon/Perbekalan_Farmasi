package com.binar.core.dashboard.dashboardItem.farmationRequirementStatus;

import com.binar.core.dashboard.dashboardItem.farmationRequirementStatus.FarmationRequirementStatusView.FarmationRequirementStatusListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class FarmationRequirementStatusPresenter implements FarmationRequirementStatusListener {
/*
 * Pengajuan Rencana Kebutuhan tanggal <<Tanggal Pengajuan>>

<<Nama barang>>
<<Satuan>>
<<Jumlah pengajuan>>
<<Jumlah disetujui>>
<<Oleh*>>
*yang posisinya lebih tinggi. Misal sudah disetujui oleh Ka IFRS sama kabis penunjang, yg ditampilin kabid penunjang

 */
	FarmationRequirementStatusModel model;
	FarmationRequirementStatusViewImpl view;
	GeneralFunction function;
	public FarmationRequirementStatusPresenter(GeneralFunction function
			, FarmationRequirementStatusViewImpl view, FarmationRequirementStatusModel model) {
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
