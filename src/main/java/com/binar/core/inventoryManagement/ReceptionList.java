package com.binar.core.inventoryManagement;

import com.binar.core.inventoryManagement.receptionList.ReceptionListModel;
import com.binar.core.inventoryManagement.receptionList.ReceptionListPresenter;
import com.binar.core.inventoryManagement.receptionList.ReceptionListViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class ReceptionList extends CssLayout {

	Label label=new Label("Daftar Penerimaan");
	GeneralFunction generalFunction;
	
	ReceptionListModel model;
	ReceptionListPresenter presenter;
	ReceptionListViewImpl view;
	
	public ReceptionList (GeneralFunction function) {
		this.generalFunction=function;
		model=new ReceptionListModel(generalFunction);
		view = new ReceptionListViewImpl(generalFunction);
		presenter = new ReceptionListPresenter(generalFunction, model, view);
		
		this.setCaption("Daftar Penerimaan");
		this.addStyleName("tab-content");
		this.addComponent(view);
	}
	
	public ReceptionListPresenter getPresenter() {
		return presenter;
	}

	public void setPresenter(ReceptionListPresenter presenter) {
		this.presenter = presenter;
	}

	public ReceptionListModel getModel() {
		return model;
	}

	public void setModel(ReceptionListModel model) {
		this.model = model;
	}

	public ReceptionListViewImpl getView() {
		return view;
	}

	public void setView(ReceptionListViewImpl view) {
		this.view = view;
	}	
}
