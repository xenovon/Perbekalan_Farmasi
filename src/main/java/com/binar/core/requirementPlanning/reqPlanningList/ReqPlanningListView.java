package com.binar.core.requirementPlanning.reqPlanningList;

import java.util.Date;
import java.util.List;

import com.binar.entity.ReqPlanning;

public interface ReqPlanningListView  {

	interface ReqPlanningListListener{
		public void updateTable(String input);
	}
	
	public boolean updateTableData(List<ReqPlanning> data);
}
