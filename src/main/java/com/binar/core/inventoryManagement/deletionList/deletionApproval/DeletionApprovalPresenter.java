package com.binar.core.inventoryManagement.deletionList.deletionApproval;

import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;

import com.binar.core.inventoryManagement.deletionList.deletionApproval.DeletionApprovalView.DeletionApprovalListener;
import com.binar.core.requirementPlanning.approval.ApprovalModel;
import com.binar.core.requirementPlanning.approval.ApprovalViewImpl;
import com.binar.entity.DeletedGoods;
import com.binar.entity.ReqPlanning;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.data.Container;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class DeletionApprovalPresenter implements DeletionApprovalListener{
	
	GeneralFunction function;
	DeletionApprovalModel model;
	DeletionApprovalViewImpl view;

	public DeletionApprovalPresenter (DeletionApprovalModel model, DeletionApprovalViewImpl view, GeneralFunction function) {
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
	public void showDetail(int idDel) {
		DeletedGoods delGoods=model.getDeletedGoods(idDel);
		if(delGoods!=null){
			view.showDetailWindow(delGoods);
		}
	}

	@Override
	public void updateTable(String input) {
		System.out.println("input "+input);
		System.out.println("Bulan ini" + Calendar.getInstance().get(Calendar.MONTH));
		DateTime date=function.getDate().parseDateMonth(input);
		List<DeletedGoods> dataTable=model.getTableData(date);
		view.updateTableData(dataTable);	
	}

	@Override
	public void saveData(Container tableContainer) {
		String status=model.acceptData(tableContainer);
		if(status==null){
			Notification.show("Penyimpanan berhasil");
		}else{
			Notification.show("Penyimpanan Gagal : "+status, Type.ERROR_MESSAGE);
		}
	}
}
