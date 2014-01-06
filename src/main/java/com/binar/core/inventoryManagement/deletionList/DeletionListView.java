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

	interface DeletionListListener{
		public void buttonClick(String buttonName);
		public void editClick(String idGoods);
		public void deleteClick(String idGoods);
		public void showClick(String idGoods);
	
	}
	public void init();
	public void construct();
	public void setListener(DeletionListListener listener);
	public boolean updateTableData(List<DeletedGoods> data);
	public void showDetailWindow(DeletedGoods deletedGoods);
	public Date getSelectedStartRange();
	public Date getSelectedEndRange();
}
