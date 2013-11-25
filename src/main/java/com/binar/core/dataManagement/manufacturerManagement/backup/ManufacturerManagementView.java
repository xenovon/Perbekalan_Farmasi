package com.binar.core.dataManagement.manufacturerManagement;

import java.util.List;

import com.binar.entity.Goods;

public interface ManufacturerManagementView {

	
	interface ProducerManagementListener{
		public void buttonClick(String buttonName);
		public void editClick(String idGoods);
		public void deleteClick(String idGoods);
		public void showClick(String idGoods);
	
	}
	public void init();
	public void construct();
	public void setListener(ProducerManagementListener listener);
	public boolean updateTableData(List<Goods> data);
	public void showDetailWindow(Goods goods);
}
