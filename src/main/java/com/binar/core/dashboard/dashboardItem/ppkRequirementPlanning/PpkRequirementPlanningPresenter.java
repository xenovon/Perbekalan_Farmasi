package com.binar.core.dashboard.dashboardItem.ppkRequirementPlanning;

import java.util.List;

import com.binar.core.dashboard.dashboardItem.farmationRequirementStatus.FarmationRequirementStatusModel;
import com.binar.core.dashboard.dashboardItem.farmationRequirementStatus.FarmationRequirementStatusViewImpl;
import com.binar.core.dashboard.dashboardItem.farmationRequirementStatus.FarmationRequirementStatusView.FarmationRequirementStatusListener;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class PpkRequirementPlanningPresenter implements FarmationRequirementStatusListener {
 /*
  * 
Pengajuan Rencana Kebutuhan tanggal <<Tanggal Pengajuan>>
<<Nama barang>>
<<Satuan>>
<<Jumlah>>

*/
	FarmationRequirementStatusModel model;
	FarmationRequirementStatusViewImpl view;
	GeneralFunction function;
	public PpkRequirementPlanningPresenter(GeneralFunction function
			, FarmationRequirementStatusViewImpl view, FarmationRequirementStatusModel model) {
		this.model=model;
		this.function=function;
		this.view=view;
		view.init(model.getCurrentMonth());
		view.setListener(this);
		updateTable();
		
	}
	@Override
	public void updateTable() {
		List<ReqPlanning> data=model.getReqList();
		if(data.size()==0){
			view.setEmptyDataView();
		}else{
			view.updateTable(model.getReqList());			
		}
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
