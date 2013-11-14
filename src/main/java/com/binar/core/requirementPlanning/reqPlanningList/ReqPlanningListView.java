package com.binar.core.requirementPlanning.reqPlanningList;

import java.util.Date;
import java.util.List;

import com.binar.entity.ReqPlanning;

public interface ReqPlanningListView  {

	interface ReqPlanningListListener{
		public void updateTable(String input);
		public void showDetail(int idReq);
	}
	
	public boolean updateTableData(List<ReqPlanning> data);
	public void setListener(ReqPlanningListListener listener);
	public void showDetailWindow(ReqPlanning reqPlanning);
	public String getSelectedPeriod();
}
