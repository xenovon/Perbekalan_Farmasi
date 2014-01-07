package com.binar.core.inventoryManagement.deletionList.deletionApproval;

import java.util.Date;
import java.util.List;

import com.binar.entity.DeletedGoods;
import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Component;

public interface DeletionApprovalView {
	
	public interface DeletionApprovalListener{
		public void approveClick();
		public void resetClick();
		public void showDetail(int idDel);
		public void updateTable();
		public void saveData();
	}
	public enum ApprovalFilter{
		ALL,ACCEPTED, NON_ACCEPTED,
	}

	public void setListener(DeletionApprovalListener listener);
	public boolean updateTableData(List<DeletedGoods> data);
	public void showDetailWindow(DeletedGoods delGoods);
	public IndexedContainer getContainer();
	public Date getSelectedStartRange();
	public Date getSelectedEndRange();


}
