package com.binar.core.inventoryManagement.consumptionList;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.binar.entity.Goods;
import com.binar.entity.GoodsConsumption;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public interface ConsumptionListView {
	
	interface ConsumptionListListener{
		public void updateTable(String input);
		public void updateTableByDate(String input);
		public void showDetail(String idGoods, int quantity);
		void delete(int consId);
		void edit(int consId);
		void buttonClick(String source, Object data);
		public void showDetailByDate(DateTime date);
		public List <GoodsConsumption> getConsByDate (DateTime date);
		public int getNumberOfConsumptionsByDate(DateTime periode);
		public void getViewMode(String selectedViewMode);
	}
	
	public boolean updateTableData(Map<Goods, Integer> data);
	public boolean updateTableDataByDate(Map<DateTime, Integer> data);
	public void setListener(ConsumptionListListener listener);
	public void showDetailWindow(List<GoodsConsumption> data, int quantity);
	public String getSelectedPeriod();
	public String getSelectedViewMode();
	public void displayForm(Component content, String title);
	public Window getWindow();
}
