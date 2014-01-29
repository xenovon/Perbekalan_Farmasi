package com.binar.core.dashboard.dashboardItem.procurementDueDate;

import com.binar.core.dashboard.dashboardItem.procurementDueDate.ProcurementDueDateView.ProcurementDueDateListener;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class ProcurementDueDatePresenter implements ProcurementDueDateListener {
/*
 * Barang dengan jatuh tempo paling dekat 
> nama supplier, tanggal jatuh tempo, jumlah hutang

DONE
 */
	ProcurementDueDateModel model;
	ProcurementDueDateViewImpl view;
	GeneralFunction function;
	public ProcurementDueDatePresenter(GeneralFunction function
			, ProcurementDueDateViewImpl view, ProcurementDueDateModel model) {
		this.model=model;
		this.function=function;
		this.view=view;
		view.init();
		view.setListener(this);
		updateTable();
		
	}
	@Override
	public void updateTable() {
		view.updateTable(model.getInvoiceData());
	}
	@Override
	public void buttonGo() {
		Navigator navigator=UI.getCurrent().getNavigator();
		navigator.navigateTo("/procurement/"+function.VIEW_PROCUREMENT_INVOICE);
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
