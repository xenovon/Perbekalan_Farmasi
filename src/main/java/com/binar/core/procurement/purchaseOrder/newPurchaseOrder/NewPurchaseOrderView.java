package com.binar.core.procurement.purchaseOrder.newPurchaseOrder;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;

import com.binar.entity.PurchaseOrder;
import com.binar.entity.PurchaseOrderItem;
import com.binar.entity.ReqPlanning;
import com.binar.entity.User;
import com.binar.entity.enumeration.EnumPurchaseOrderType;
import com.vaadin.ui.Window;

public interface NewPurchaseOrderView {

	public enum FormViewEnum{
		VIEW_REQPLANNING, VIEW_PO; 
	}
	public interface NewPurchaseOrderListener{
		public void buttonClick(String button);
		public void periodChange();
	}
	
	public void init();
	public void construct();
	public void setListener(NewPurchaseOrderListener listener);
	public void setFormView(FormViewEnum formView);
	public Date getPurchaseDate();
	public String getSelectedPeriod();
	
	public void generateReqPlanningTable(List<ReqPlanning> reqPlanning);
	public void checkReqPlanning(boolean isChecked);
	public void setComboUserData(Map<Integer, String> data);
	public boolean generatePurchaseOrderListView(List<PurchaseOrder> purchaseOrder);
	public List<PurchaseOrder> getPurchaseOrderListData();
	public FormData getFormData();
	
	public FormViewEnum getFormState();
	
	class FormData{
		private List<Integer> reqPlanningId;
		private Date purchaseDate;
		private int idUser;
		 
		public int getIdUser() {
			return idUser;
		}
		public void setIdUser(int idUser) {
			this.idUser = idUser;
		}
		public Date getPurchaseDate() {
			return purchaseDate;
		}
		public void setPurchaseDate(Date date) {
			this.purchaseDate = date;
		}
		public void setReqPlanningId(List<Integer> reqPlanningId) {
			this.reqPlanningId = reqPlanningId;
		}
		public List<Integer> getReqPlanningId() {
			return reqPlanningId;
		}
	}

	
}
