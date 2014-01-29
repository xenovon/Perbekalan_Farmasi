package com.binar.core.inventoryManagement.deletionList;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.binar.entity.DeletedGoods;
import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public interface DeletionListView {

	public enum ApprovalFilter{
		ALL,ACCEPTED, NON_ACCEPTED,
	}
	interface DeletionListListener{
		public void newDeletion();
		public void editClick(int idDeletion);
		public void deleteClick(int idDeletion);
		public void showClick(int idDeletion);
		public void dateRangeChange();
		
	
	}
	public void init();
	public void construct();
	public void setListener(DeletionListListener listener);
	public boolean updateTableData(List<DeletedGoods> data, final boolean withEditDeletion);
	public void showDetailWindow(DeletedGoods deletedGoods);
	public Date getSelectedStartRange();
	public Date getSelectedEndRange();
	public void displayForm(Component content, String title);
}
