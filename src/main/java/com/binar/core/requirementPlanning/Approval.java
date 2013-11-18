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

	
	private VerticalLayout layout=new VerticalLayout();
	
	private GeneralFunction generalFunction;
	
	private ApprovalModel model;
	private ApprovalPresenter presenter;
	private ApprovalViewImpl view;
	
	public Approval(GeneralFunction function) {
		generalFunction=function;
		
		model = new ApprovalModel(generalFunction);
		view =new ApprovalViewImpl(generalFunction);
		presenter = new  ApprovalPresenter(model, view, generalFunction);
		
		this.addComponent(view);
		this.setCaption("Persetujuan");
		this.setSizeFull();
		this.addStyleName("tab-content");
	}
	
	public ApprovalPresenter getPresenter(){
		return presenter;
		
	}
	public ApprovalViewImpl getView() {
		return view;
	}
}
