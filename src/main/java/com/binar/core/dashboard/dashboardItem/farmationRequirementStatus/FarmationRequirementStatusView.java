package com.binar.core.dashboard.dashboardItem.farmationRequirementStatus;

import java.util.List;

import com.binar.entity.Goods;
import com.binar.entity.ReqPlanning;
import com.vaadin.ui.Panel;

public interface  FarmationRequirementStatusView {
	
	public interface FarmationRequirementStatusListener{
		public void updateTable();
		public void buttonGo();
	}
	public void init();
	public void construct();
	public void updateTable(List<ReqPlanning> reqPlanning);
}