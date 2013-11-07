package com.binar.core.requirementPlanning.approval;

public interface ApprovalView {

	public interface ApprovalListener{
		public void buttonClick(String param, String[] data);
	}
	

	public void setListener(ApprovalListener listener);
	
}
