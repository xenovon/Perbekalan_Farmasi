package com.binar.core.dashboard.dashboardItem.ppkRequirementPlanning;

import java.util.List;

import com.binar.entity.Goods;
import com.vaadin.ui.Panel;

interface  PpkRequirementPlanningView {
	
	public interface PpkRequirementPlanningListener{
		public void updateTable();
		public void buttonGo();
	}
	public void init();
	public void construct();
	public void updateTable(List<Goods> goods);
}
