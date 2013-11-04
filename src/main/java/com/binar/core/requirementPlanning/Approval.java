package com.binar.core.requirementPlanning;

import com.binar.core.requirementPlanning.approval.ApprovalModel;
import com.binar.core.requirementPlanning.approval.ApprovalPresenter;
import com.binar.core.requirementPlanning.approval.ApprovalView;
import com.binar.core.requirementPlanning.approval.ApprovalViewImpl;
import com.binar.generalFunction.GeneralFunction;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;

public class Approval extends  CssLayout {

	
	VerticalLayout layout=new VerticalLayout();
	
	GeneralFunction generalFunction;
	
	ApprovalModel model;
	ApprovalPresenter presenter;
	ApprovalViewImpl view;
	
	public Approval(GeneralFunction function) {
		generalFunction=function;
		
		model = new ApprovalModel();
		view =new ApprovalViewImpl(generalFunction);
		presenter = new  ApprovalPresenter(model, view);
		
		this.addComponent(view);
		this.setCaption("Persetujuan");
		this.setSizeFull();
		this.addStyleName("tab-content");
	}
}
