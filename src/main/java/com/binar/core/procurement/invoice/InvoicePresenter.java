package com.binar.core.procurement.invoice;

import com.binar.core.procurement.invoice.InvoiceView.InvoiceListener;
import com.binar.generalFunction.GeneralFunction;

public class InvoicePresenter implements InvoiceListener{
	
	InvoiceModel model;
	InvoicePresenter presenter;
	InvoiceView view;
	GeneralFunction function;
	public InvoicePresenter(InvoiceView view, InvoiceModel model, GeneralFunction function) {
		this.function=function;
		this.model=model;
		this.view=view;
	}
	@Override
	public void buttonClick(String buttonName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void editClick(int idInvoice) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteClick(int idInvoice) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void showClick(int idInvoice) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void valueChange(String value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateTable() {
		// TODO Auto-generated method stub
		
	}
}
