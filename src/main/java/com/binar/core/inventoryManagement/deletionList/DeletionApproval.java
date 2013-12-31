package com.binar.core.inventoryManagement.deletionList;

import com.binar.core.inventoryManagement.deletionList.deletionApproval.DeletionApprovalModel;
import com.binar.core.inventoryManagement.deletionList.deletionApproval.DeletionApprovalPresenter;
import com.binar.core.inventoryManagement.deletionList.deletionApproval.DeletionApprovalViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;

public class DeletionApproval extends CssLayout{

private VerticalLayout layout=new VerticalLayout();
	
	private GeneralFunction generalFunction;
	private DeletionApprovalModel model;
	private DeletionApprovalPresenter presenter;
	private DeletionApprovalViewImpl view;
	
	public DeletionApproval(GeneralFunction function) {
		generalFunction=function;
		model = new DeletionApprovalModel(generalFunction);
		view =new DeletionApprovalViewImpl(generalFunction);
		presenter = new DeletionApprovalPresenter(model, view, generalFunction);
		
		this.addComponent(view);
		this.setCaption("Persetujuan");
		this.setSizeFull();
		this.addStyleName("tab-content");
	}

	public DeletionApprovalPresenter getPresenter() {
		return presenter;
	}

	public void setPresenter(DeletionApprovalPresenter presenter) {
		this.presenter = presenter;
	}

	public DeletionApprovalViewImpl getView() {
		return view;
	}

	public void setView(DeletionApprovalViewImpl view) {
		this.view = view;
	}
}
