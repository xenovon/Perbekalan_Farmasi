package com.binar.core.inventoryManagement.stockList;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.binar.entity.GoodsConsumption;
import com.binar.entity.GoodsReception;
import com.binar.entity.PurchaseOrder;

public interface StockListView {
	
	interface StockListListener{
		public void buttonClick(String buttonName);
		public void showClick(int idPurchaseOrder);
		public void valueChange(String value);
		public void updateTable();
	
	}
	public void init();
	public void construct();
	public void setListener(StockListListener listener);
	public boolean updateTableData(List<GoodsConsumption> consumption, List<GoodsReception> reception);
	public void showDetailConsumption(GoodsConsumption consumption);
	public void showDetailReception(GoodsReception reception);
	public String getSelectedPeriod();
	public String getSelectedGoods();
	public void setComboGoodsData(Map<String, String> data);
	
	public class TableData{
		private GoodsConsumption consumption;
		private GoodsReception reception;
		private DateTime date;
		public GoodsConsumption getConsumption() {
			return consumption;
		}
		public void setConsumption(GoodsConsumption consumption) {
			this.consumption = consumption;
		}
		public GoodsReception getReception() {
			return reception;
		}
		public void setReception(GoodsReception reception) {
			this.reception = reception;
		}
		
		public DateTime getDate() {
			return date;
		}
		public void setDate(DateTime date) {
			this.date = date;
		}
		
		
	}
}
