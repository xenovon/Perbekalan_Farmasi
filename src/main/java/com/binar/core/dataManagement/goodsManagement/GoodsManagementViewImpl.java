package com.binar.core.dataManagement.goodsManagement;

import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class GoodsManagementViewImpl extends VerticalLayout 
		implements GoodsManagementView {

	private GeneralFunction function;
	private GoodsManagementListener listener;

	private Label title;

	public GoodsManagementViewImpl(GeneralFunction function) {
		this.function=function;
	}
	
	public void init(){
		title=new Label("<h2>Manajemen Barang</h2>", ContentMode.HTML);
		construct();
	}
	@Override
	public void construct() {
		// TODO Auto-generated method stub
		
	}	
	public void addListener(GoodsManagementListener listener){
		this.listener=listener;
	}


	
	
	
	
}
