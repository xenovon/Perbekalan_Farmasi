package com.binar.core.procurement.invoice.editInvoice;

import java.util.Date;
import java.util.Map;

import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.FormData;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.FormViewEnum;
import com.binar.core.procurement.invoice.newInvoice.NewInvoiceView.NewInvoiceListener;
import com.binar.core.procurement.purchaseOrder.editPurchaseOrder.EditPurchaseOrderView.EditPurchaseOrderListener;
import com.binar.entity.Invoice;
import com.binar.entity.PurchaseOrder;

public interface EditInvoiceView {

	interface EditInvoiceListener{
		public void buttonClick(String button);
		public void valueChange();
		public double countPrice(boolean ppn, String quantity, String price,
				String discount);


	}
	
	public void setListener(EditInvoiceListener listener);
	
	public void generateInvoiceView(Invoice data);
	public FormData getEditedInvoice();

	public void init(); 
	public void construct();
	public void setFormData(Invoice data); //untuk mengeset data form
 	public void resetForm(); //mereset isi form
	public void showError(String content); //menampilkan error di tempat tertentu dengan konten tertentu
	public void hideError(); //menyembunyikan error tertentu		
	
}
