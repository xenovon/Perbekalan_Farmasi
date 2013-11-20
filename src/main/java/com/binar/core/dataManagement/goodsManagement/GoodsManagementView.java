package com.binar.core.dataManagement.goodsManagement;

interface  GoodsManagementView {
  
	interface GoodsManagementListener{
		
	}
	public void init();
	public void construct();
	public void addListener(GoodsManagementListener listener);
}

