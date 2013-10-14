package com.binar.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity 
@Table(name="purchase_order_item")
public class PurchaseOrderItem {

	@Id
	@Column(name="id_purchase_order_item")
	private int idPurchaseOrderItem;
	
	@ManyToOne
	@Column(name="id_supplier_goods")
	private SupplierGoods supplierGoods;
	
	@ManyToOne 
	@Column(name="id_purchase_order")
	private PurchaseOrder purchaseOrder;
	
	private int quantity;
	@Column(columnDefinition="TEXT")
	private String information;
	

	public int getIdPurchaseOrderItem() {
		return idPurchaseOrderItem;
	}

	public void setIdPurchaseOrderItem(int idPurchaseOrderItem) {
		this.idPurchaseOrderItem = idPurchaseOrderItem;
	}

	public SupplierGoods getSupplierGoods() {
		return supplierGoods;
	}

	public void setSupplierGoods(SupplierGoods supplierGoods) {
		this.supplierGoods = supplierGoods;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}


	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}


	
	
}
