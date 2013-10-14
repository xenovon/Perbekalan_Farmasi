package com.binar.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.binar.entity.enumeration.EnumPurchaseOrderType;

@Entity
@Table(name="purchase_order")
public class PurchaseOrder {

	@Id
	@Column(name="id_purchase_order")
	private int idPurchaseOrder;
	
	@Column(name="purchase_order_number")
	private int purchaseOrderNumber;
	
	private String rayon;
	
	@Column(name="purchase_order_type")
	private EnumPurchaseOrderType purchaseOrderType;
	
	@OneToMany
	private List<PurchaseOrderItem> purchaseOrderItem;

	public int getIdPurchaseOrder() {
		return idPurchaseOrder;
	}

	public void setIdPurchaseOrder(int idPurchaseOrder) {
		this.idPurchaseOrder = idPurchaseOrder;
	}

	public int getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(int purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public String getRayon() {
		return rayon;
	}

	public void setRayon(String rayon) {
		this.rayon = rayon;
	}

	public EnumPurchaseOrderType getPurchaseOrderType() {
		return purchaseOrderType;
	}

	public void setPurchaseOrderType(EnumPurchaseOrderType purchaseOrderType) {
		this.purchaseOrderType = purchaseOrderType;
	}

	public List<PurchaseOrderItem> getPurchaseOrderItem() {
		return purchaseOrderItem;
	}

	public void setPurchaseOrderItem(List<PurchaseOrderItem> purchaseOrderItem) {
		this.purchaseOrderItem = purchaseOrderItem;
	}
	
	
}
