package com.binar.core.dashboard.dashboardItem.farmationExpireGoods;

import com.binar.core.dashboard.dashboardItem.farmationExpireGoods.FarmationExpiredGoodsView.FarmationExpiredGoodsListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class FarmationExpiredGoodsPresenter implements FarmationExpiredGoodsListener {

	
/*
 * Barang mendekati kadaluarsa
> Nama barang, jumlah stok, satuan, expired date

 */
	FarmationExpiredGoodsModel model;
	FarmationExpiredGoodsViewImpl view;
	GeneralFunction function;
	public FarmationExpiredGoodsPresenter(GeneralFunction function
			, FarmationExpiredGoodsViewImpl view, FarmationExpiredGoodsModel model) {
		this.model=model;
		this.function=function;
		this.view=view;
		view.init();
		view.setListener(this);
		
		
	}
	@Override
	public void updateTable() {
		view.updateTable(model.getFarmationExpiredGoods());
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