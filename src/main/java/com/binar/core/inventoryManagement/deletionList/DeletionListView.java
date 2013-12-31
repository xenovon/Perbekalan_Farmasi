package com.binar.core.inventoryManagement.deletionList;

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
		public void updateTable(String input);
		public void updateTableByDate(String input);
		public void showDetail(String idGoods, int quantity);
		void delete(int delGoodsId);
		void edit(int delGoodsId);
		void buttonClick(String source, Object data);
		public void showDetailByDate(DateTime date);
		public List <DeletedGoods> getDelGoodsByDate (DateTime date);
		public int getNumberOfDeletionsByDate(DateTime periode);
		public void getViewMode(String selectedViewMode);
	}
	
	public boolean updateTableData(Map<Goods, Integer> data);
	public boolean updateTableDataByDate(Map<DateTime, Integer> data);
	public void setListener(DeletionListListener listener);
	public void showDetailWindow(List<DeletedGoods> data, int quantity);
	public String getSelectedPeriod();
	public String getSelectedViewMode();
	public void displayForm(Component content, String title);
	public Window getWindow();
}
