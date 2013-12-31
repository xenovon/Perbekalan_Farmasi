package com.binar.core.inventoryManagement;

import com.binar.core.inventoryManagement.consumptionList.ConsumptionListModel;
import com.binar.core.inventoryManagement.consumptionList.ConsumptionListPresenter;
import com.binar.core.inventoryManagement.consumptionList.ConsumptionListViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class ConsumptionList extends CssLayout {

	Label label=new Label("Daftar Pengeluaran Harian");
	GeneralFunction generalFunction;
	
	ConsumptionListModel model;
	ConsumptionListPresenter presenter;
	ConsumptionListViewImpl view;
	
	public ConsumptionList( GeneralFunction function) {
		this.generalFunction=function;
		model=new ConsumptionListModel(generalFunction);
		view = new ConsumptionListViewImpl(generalFunction);
		presenter = new ConsumptionListPresenter(generalFunction, model, view);
		
		this.setCaption("Daftar Pengeluaran Harian");
		this.addStyleName("tab-content");
		this.addComponent(view);
	}
	
	public ConsumptionListPresenter getPresenter() {
		return presenter;
	}

	public void setPresenter(ConsumptionListPresenter presenter) {
		this.presenter = presenter;
	}

	public ConsumptionListModel getModel() {
		return model;
	}

	public void setModel(ConsumptionListModel model) {
		this.model = model;
	}

	public ConsumptionListViewImpl getView() {
		return view;
	}

	public void setView(ConsumptionListViewImpl view) {
		this.view = view;
	}	
}