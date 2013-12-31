package com.binar.core.inventoryManagement.deletionList.deletionApproval;

import java.util.List;

import com.binar.entity.DeletedGoods;
import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;

public interface DeletionApprovalView {
	
	public interface DeletionApprovalListener{
		public void buttonClick(String param);
		public void showDetail(int idDel);
		public void updateTable(String input);
		public void saveData(Container tableContainer);
	}
	
	public void setListener(DeletionApprovalListener listener);
	public boolean updateTableData(List<DeletedGoods> data);
	public void showDetailWindow(DeletedGoods delGoods);
	public String getSelectedPeriod();
	public IndexedContainer getContainer();

}
