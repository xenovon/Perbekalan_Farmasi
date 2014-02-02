package com.binar.core.dashboard.dashboardItem.procurementSupplierRank;

import java.util.Map;

import com.binar.core.dashboard.dashboardItem.procurementSupplierRank.ProcurementSupplierRankView.ProcurementManufacturRankListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class ProcurementSupplierRankPresenter implements ProcurementManufacturRankListener {
/*
 * 
5 Produsen dengan jumlah transaksi terbanyak selama...(blm tau berapa, misal 3 bulan) 
> Grafik batang produsen dan nilai transaksinya

DONE
 */
	ProcurementSupplierRankModel model;
	ProcurementSupplierRankViewImpl view;
	GeneralFunction function;
	public ProcurementSupplierRankPresenter(GeneralFunction function
			, ProcurementSupplierRankViewImpl view, ProcurementSupplierRankModel model) {
		this.model=model;
		this.function=function;
		this.view=view;
		view.init();
		view.setListener(this);
		
		
	}
	@Override
	public void updateChart() {
		Map<Integer, String> data=model.getSupplierTransaction();
		if(data==null){
			view.setEmptyDataView();
		}else{
			view.generateChart(data);			
		}
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
