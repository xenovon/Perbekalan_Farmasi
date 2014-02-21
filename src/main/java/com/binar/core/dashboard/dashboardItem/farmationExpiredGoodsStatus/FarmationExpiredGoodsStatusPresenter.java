package com.binar.core.dashboard.dashboardItem.farmationExpiredGoodsStatus;

import java.util.List;

import com.binar.core.dashboard.dashboardItem.farmationExpiredGoodsStatus.FarmationExpiredGoodsStatusView.FarmationExpiredGoodsStatusListener;
import com.binar.entity.DeletedGoods;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class FarmationExpiredGoodsStatusPresenter implements FarmationExpiredGoodsStatusListener {

/*
 * Status daftar obat kadaluarsa diterima atau ditolak.

<<Nama barang>>
<<Satuan>>
<<Status pengajuan>>
<<Oleh>>

udah
 */
	
	FarmationExpiredGoodsStatusModel model;
	FarmationExpiredGoodsStatusViewImpl view;
	GeneralFunction function;
	public FarmationExpiredGoodsStatusPresenter(GeneralFunction function
			, FarmationExpiredGoodsStatusViewImpl view, FarmationExpiredGoodsStatusModel model) {
		this.model=model;
		this.function=function;
		this.view=view;
		view.init(model.getCurrentMonth());
		view.setListener(this);
		updateTable();
		
	}
	@Override
	public void updateTable() {
		List<DeletedGoods> deleted=model.getDeletedGoodsList();
		if(deleted.size()==0){
			view.setEmptyDataView();
		}else{
			view.updateTable(model.getDeletedGoodsList());
		}
	}
	@Override
	public void buttonGo() {
		Navigator navigator=UI.getCurrent().getNavigator();
		navigator.navigateTo("/inventorymanagement/"+function.VIEW_INVENTORY_DELETION);
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
