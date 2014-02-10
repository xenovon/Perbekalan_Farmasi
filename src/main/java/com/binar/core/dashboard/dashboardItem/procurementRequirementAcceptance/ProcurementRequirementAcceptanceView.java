package com.binar.core.dashboard.dashboardItem.procurementRequirementAcceptance;

import java.util.List;

import com.binar.entity.Goods;
import com.binar.entity.ReqPlanning;
import com.vaadin.ui.Panel;

interface  ProcurementRequirementAcceptanceView {
	
	public interface ProcurementRequirementAcceptanceListener{
		public void updateTable();
		public void buttonGo();
	}
	public void init(String month);
	public void construct();
	public void updateTable(List<ReqPlanning> goods);
}
