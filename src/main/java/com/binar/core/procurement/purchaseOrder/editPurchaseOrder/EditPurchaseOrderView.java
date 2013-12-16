package com.binar.core.procurement.purchaseOrder.editPurchaseOrder;

import java.util.Map;

import com.binar.entity.Goods;
import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;

public interface EditPurchaseOrderView {

	public enum ErrorLabel{
	}
	interface EditPurchaseOrderListener{
		public void buttonClick(String button);
		public void realTimeValidator(String inputFields);
		

	}
	public void init(); 
	public void construct();
	public void setListener(EditPurchaseOrderListener listener);
	public void setFormData(Goods data); //untuk mengeset data form
	public FormData getFormData(); //untuk mendapatkan data form
 	public void resetForm(); //mereset isi form
	public void showError(ErrorLabel label, String content); //menampilkan error di tempat tertentu dengan konten tertentu
	public void hideError(ErrorLabel label); //menyembunyikan error tertentu
	public void hideAllError(); //menyembunyikan semua error
	public void setComboBoxData(ComboDataList list);
	
	
	public class ComboDataList{
		private Map<Integer, String> userList;
		private Map<Integer, String> distributor;
		public Map<Integer, String> getUserList() {
			return userList;
		}
		public void setUserList(Map<Integer, String> userList) {
			this.userList = userList;
		}
		public Map<Integer, String> getDistributor() {
			return distributor;
		}
		public void setDistributor(Map<Integer, String> distributor) {
			this.distributor = distributor;
		}
	
		
	}
	

}
