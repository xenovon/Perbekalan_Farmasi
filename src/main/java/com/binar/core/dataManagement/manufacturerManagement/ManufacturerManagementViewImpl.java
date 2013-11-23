package com.binar.core.dataManagement.manufacturerManagement;

import java.util.List;

import com.binar.entity.Goods;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ManufacturerManagementViewImpl extends VerticalLayout implements ManufacturerManagementView{

	GeneralFunction function;
	Label title;
	
	public ManufacturerManagementViewImpl(GeneralFunction function) {
		this.function=function;
	}
	@Override
	public void init() {
		title=new Label("<h2>Manajemen Produsen</h2>", ContentMode.HTML);

		construct();
	}
	@Override
	public void construct() {
		this.addComponent(title);
	}
	ProducerManagementListener listener;
	@Override
	public void setListener(ProducerManagementListener listener) {
		this.listener=listener;
	}
	@Override
	public boolean updateTableData(List<Goods> data) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void showDetailWindow(Goods goods) {
		// TODO Auto-generated method stub
		
	}	
	
}
