package com.binar.core.requirementPlanning.reqPlanningList;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.binar.core.PresenterInterface;
import com.binar.core.requirementPlanning.reqPlanningList.ReqPlanningListView.ReqPlanningListListener;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.Component;

public class ReqPlanningListPresenter implements ReqPlanningListListener{

	GeneralFunction function;
	ReqPlanningListModel model;
	ReqPlanningListViewImpl view;
	
	public ReqPlanningListPresenter(GeneralFunction function, 
			ReqPlanningListModel model, ReqPlanningListViewImpl view){
		this.model=model;
		this.view=view;
		
		view.init();
	}

	@Override
	public void updateTable(String input) {
		DateTime date=function.getDate().parseDateMonth(input);
		List<ReqPlanning> dataTable=model.getTableData(date);
		view.updateTableData(dataTable);
	}
	


}
