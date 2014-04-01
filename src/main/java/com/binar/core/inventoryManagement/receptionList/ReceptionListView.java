package com.binar.core.inventoryManagement.receptionList;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.binar.entity.Goods;
import com.binar.entity.GoodsReception;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public interface ReceptionListView {
	
	interface ReceptionListListener{
		public void updateTable(String input, boolean isConstructor);
		public void updateTableByDate(String input, boolean isConstructor);
		public void showDetail(String idGoods, int quantity);
		void delete(int recId);
		void edit(int recId);
		void buttonClick(String source, Object data);
		public void showDetailByDate(DateTime date);
		public List <GoodsReception> getRecByDate (DateTime date);
		public int getNumberOfReceptionsByDate(DateTime periode);
		public void getViewMode(String selectedViewMode);
		public double getPercentage(GoodsReception reception);
	}
	
	public boolean updateTableData(Map<Goods, Integer> data);
	public boolean updateTableDataByDate(Map<DateTime, Integer> data);
	public void setListener(ReceptionListListener listener);
	public void showDetailWindow(List<GoodsReception> data, int quantity, boolean withEditReception);
	public String getSelectedPeriod();
	public String getSelectedViewMode();
	public void displayForm(Component content, String title);
	public Window getWindow();
}