package com.binar.core.inventoryManagement;


import com.binar.core.inventoryManagement.consumptionList.ConsumptionListModel;
import com.binar.core.inventoryManagement.consumptionList.ConsumptionListPresenter;
import com.binar.core.inventoryManagement.consumptionList.ConsumptionListViewImpl;
import com.binar.core.inventoryManagement.deletionList.DeletionListModel;
import com.binar.core.inventoryManagement.deletionList.DeletionListPresenter;
import com.binar.core.inventoryManagement.deletionList.DeletionListViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class DeletionList extends CssLayout {
	
	Label label=new Label("Daftar Barang Kadaluarsa");
	GeneralFunction generalFunction;
	DeletionListModel model;
	DeletionListPresenter presenter;
	DeletionListViewImpl view;
	
	public DeletionList(GeneralFunction function) {
		
		this.generalFunction=function;
		model=new DeletionListModel(generalFunction);
		view = new DeletionListViewImpl(generalFunction);
		presenter = new DeletionListPresenter(generalFunction, model, view);
		
		this.setCaption("Daftar Pengeluaran Harian");
		this.addStyleName("tab-content");
		this.addComponent(view);
	}

	public DeletionListModel getModel() {
		return model;
	}

	public void setModel(DeletionListModel model) {
		this.model = model;
	}

	public DeletionListPresenter getPresenter() {
		return presenter;
	}

	public void setPresenter(DeletionListPresenter presenter) {
		this.presenter = presenter;
	}

	public DeletionListViewImpl getView() {
		return view;
	}

	public void setView(DeletionListViewImpl view) {
		this.view = view;
	}
}
