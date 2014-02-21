package com.binar.core.dashboard.dashboardItem.ppkExpiredGoodsNonAccepted;

import com.binar.core.dashboard.dashboardItem.ppkExpiredGoodsNonAccepted.PpkExpiredGoodsNonAcceptedView.PpkExpiredGoodsNonAcceptedListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class PpkExpiredGoodsNonAcceptedPresenter implements PpkExpiredGoodsNonAcceptedListener {
/*
 * Daftar barang kadaluarsa yang belum disetujui
Format :
Pengajuan barang kadaluarsa tanggal <<Tanggal Pengajuan>>
<<Nama barang>>
<<Satuan>>
<<Jumlah>>
DONE

 */
	PpkExpiredGoodsNonAcceptedModel model;
	PpkExpiredGoodsNonAcceptedViewImpl view;
	GeneralFunction function;
	public PpkExpiredGoodsNonAcceptedPresenter(GeneralFunction function
			, PpkExpiredGoodsNonAcceptedViewImpl view, PpkExpiredGoodsNonAcceptedModel model) {
		this.model=model;
		this.function=function;
		this.view=view;
		view.init(model.getCurrentMonth());
		view.setListener(this);
		updateTable();
	}
	@Override
	public void updateTable() {
		view.updateTable(model.getGoodsList());
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
