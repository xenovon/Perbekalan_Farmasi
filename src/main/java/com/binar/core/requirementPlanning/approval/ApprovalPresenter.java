package com.binar.core.requirementPlanning.approval;

import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;

import com.binar.core.requirementPlanning.approval.ApprovalModel.AcceptData;
import com.binar.core.requirementPlanning.approval.ApprovalView.ApprovalListener;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.data.Container;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class ApprovalPresenter implements ApprovalListener {

	GeneralFunction function;
	ApprovalModel model;
	ApprovalViewImpl view;
	
	public ApprovalPresenter(ApprovalModel model, ApprovalViewImpl view, GeneralFunction function){
		this.model=model;
		this.view=view;
		this.function=function;
		view.init();
		view.setListener(this);
		updateTable(view.getSelectedPeriod());
	}

	@Override
	public void buttonClick(String param) {
		if(param.equals("buttonAccept")){
			saveData(view.getContainer());
		}
	}

	@Override
	public void showDetail(int idReq) {
		ReqPlanning planning=model.getReqPlanning(idReq);
		if(planning!=null){
			view.showDetailWindow(planning);
		}
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
	public void saveData(Container container) {
		String status=model.acceptData(container);
		if(status==null){
			Notification.show("Penyimpanan berhasil");
		}else{
			Notification.show("Penyimpanan Gagal : "+status, Type.ERROR_MESSAGE);
		}
	}

}
