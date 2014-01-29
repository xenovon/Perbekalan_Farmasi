package com.binar.core.procurement.invoice;

import java.util.List;

import com.binar.entity.Invoice;

public interface InvoiceView {

	public interface  InvoiceListener{
		public void buttonClick(String buttonName);
		public void editClick(int idInvoice);
		public void deleteClick(int idInvoice);
		public void showClick(int idInvoice);
		public void valueChange(String value);
		public void updateTable();
		
	}
	
	public void init();
	public void construct();
	public void setListener(InvoiceListener listener);
	public boolean updateTableData(List<Invoice> data, boolean withEditInvoice);
	public void showDetailWindow(Invoice invoice);
	public String getSelectedPeriod();


}
