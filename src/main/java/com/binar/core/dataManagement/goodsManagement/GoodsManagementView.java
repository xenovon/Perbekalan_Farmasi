package com.binar.core.dataManagement.goodsManagement;

import java.util.List;

import com.binar.core.requirementPlanning.reqPlanningList.ReqPlanningListView.ReqPlanningListListener;
import com.binar.entity.Goods;
import com.binar.entity.ReqPlanning;

interface  GoodsManagementView {
  
	interface GoodsManagementListener{
		public void buttonClick(String buttonName);
		public void editClick(String idGoods);
		public void deleteClick(String idGoods);
		public void showClick(String idGoods);
	
	}
	public void init();
	public void construct();
	public void setListener(GoodsManagementListener listener);
	public boolean updateTableData(List<Goods> data);
	public void showDetailWindow(Goods goods);

}

