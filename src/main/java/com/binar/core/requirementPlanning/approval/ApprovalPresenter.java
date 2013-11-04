package com.binar.core.requirementPlanning.approval;

import com.binar.core.requirementPlanning.approval.ApprovalView.ApprovalListener;
import com.vaadin.ui.Notification;

public class ApprovalPresenter implements ApprovalListener {

	ApprovalModel model;
	ApprovalViewImpl view;
	
	public ApprovalPresenter(ApprovalModel model, ApprovalViewImpl view){
		this.view=view;
		this.model=model;
		this.view.setListener(this);
		view.init();
	}
	
	@Override
	public void buttonClick(String param, String[] data) {
		if(param.equals("buttonAccept")){
			Notification.show("Tombol Accept ditekan "+data[0]+" "+data[1]);
		}
		
	}

}
