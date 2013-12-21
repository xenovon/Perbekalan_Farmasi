package com.binar.core.procurement.invoice.editInvoice;

import java.util.Map;

import com.binar.core.procurement.purchaseOrder.editPurchaseOrder.FormData;
import com.binar.core.procurement.purchaseOrder.editPurchaseOrder.EditPurchaseOrderView.EditPurchaseOrderListener;
import com.binar.entity.PurchaseOrder;

public interface EditInvoiceView {

	interface EditInvoiceListener{
		public void buttonClick(String button);
		public void valueChange();

	}
	public void init(); 
	public void construct();
	public void setListener(EditPurchaseOrderListener listener);
	public void setFormData(PurchaseOrder data); //untuk mengeset data form
	public FormData getFormData(); //untuk mendapatkan data form
 	public void resetForm(); //mereset isi form
	public void showError(String content); //menampilkan error di tempat tertentu dengan konten tertentu
	public void hideError(); //menyembunyikan error tertentu
	public void setComboBoxData(Map<Integer, String> data);
		
	
}
