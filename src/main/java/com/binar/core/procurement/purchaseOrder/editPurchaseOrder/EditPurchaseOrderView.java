package com.binar.core.procurement.purchaseOrder.editPurchaseOrder;

import java.util.Map;

import com.binar.entity.Goods;
import com.binar.entity.PurchaseOrder;
import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;

public interface EditPurchaseOrderView {

	public enum ErrorLabel{
	}
	interface EditPurchaseOrderListener{
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
