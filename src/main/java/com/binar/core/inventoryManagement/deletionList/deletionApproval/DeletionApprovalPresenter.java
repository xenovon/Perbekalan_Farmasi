package com.binar.core.inventoryManagement.deletionList.deletionApproval;

import java.util.Calendar;
import java.util.Date;
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
		updateTable();
	}

	@Override
	public void showDetail(int idDel) {
		DeletedGoods delGoods=model.getDeletedGoods(idDel);
		if(delGoods!=null){
			view.showDetailWindow(delGoods);
		}
	}

	@Override
	public void updateTable() {
		System.out.println("Bulan ini" + Calendar.getInstance().get(Calendar.MONTH));
		Date dateStart=view.getSelectedStartRange();
		Date dateEnd=view.getSelectedEndRange();
		List<DeletedGoods> dataTable=model.getDeletedTable(dateStart, dateEnd, view.getApprovalFilter());
		view.updateTableData(dataTable);	
	}



	@Override
	public void approveClick() {
		String status=model.acceptData(view.getContainer());
		if(status==null){
			Notification.show("Penyimpanan berhasil");
		}else{
			Notification.show("Penyimpanan Gagal : "+status, Type.ERROR_MESSAGE);
		}
		updateTable();
		
	}

	@Override
	public void resetClick() {
		updateTable();
	}

	@Override
	public void saveData() {
		// TODO Auto-generated method stub
		
	}
}
