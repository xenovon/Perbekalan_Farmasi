package com.binar.core.requirementPlanning.approval;

import java.util.List;

import com.binar.core.requirementPlanning.approval.ApprovalModel.AcceptData;
import com.binar.core.requirementPlanning.reqPlanningList.ReqPlanningListModel;
import com.binar.entity.ReqPlanning;
import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;

public interface ApprovalView {

	public interface ApprovalListener{
		public void buttonClick(String param);
		public void showDetail(int idReq);
		public void updateTable(String input);
		public void saveData(Container tableContainer);
	}
	

	public void setListener(ApprovalListener listener);
	public boolean updateTableData(List<ReqPlanning> data);
	public void showDetailWindow(ReqPlanning reqPlanning);
	public String getSelectedPeriod();
	public IndexedContainer getContainer();

}
