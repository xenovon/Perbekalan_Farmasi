package com.binar.core.requirementPlanning.reqPlanningList;

import java.util.Calendar;
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
		this.function=function;
		view.init();
		view.setListener(this);
		updateTable(view.getSelectedPeriod());
	}

	@Override
	public void updateTable(String input) {
		System.out.println("input "+input);
		System.out.println("Bulan ini" + Calendar.getInstance().get(Calendar.MONTH));
		DateTime date=function.getDate().parseDateMonth(input);
		List<ReqPlanning> dataTable=model.getTableData(date);
		view.updateTableData(dataTable);
	}

	@Override
	public void showDetail(int idReq) {
		ReqPlanning planning=model.getReqPlanning(idReq);
		if(planning!=null){
			view.showDetailWindow(planning);
		}
	}
	


}
