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
	
	
	public void generateReqPlanningTable(List<ReqPlanning> reqPlanning);
	public List<Integer> getReqPlanningSelected();
	
	public void setComboUserData(Map<Integer, String> data);
	public void generatePurchaseOrderListView(List<PurchaseOrder> purchaseOrder);
	public List<PurchaseOrder> getPurchaseOrderListData();
	
	public FormViewEnum getFormState();
	
	class PurchaseOrderData{
		private Date date;
		private int idUser;
		 
		public int getIdUser() {
			return idUser;
		}
		public void setIdUser(int idUser) {
			this.idUser = idUser;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
	}

	
}
