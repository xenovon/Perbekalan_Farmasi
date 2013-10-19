package com.binar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="invoice_item")
public class InvoiceItem {

	@Id
	@Column(name="id_invoice_item")
	private int idInvoiceItem;
	
	@ManyToOne
	@Column(name="fk")
	private Invoice invoice;
	
	@OneToOne
	@Column(name="fk")
	private PurchaseOrderItem purchaseOrderItem;
	
	private String batch;
	private float discount;
	private int price;
	private int quantity;
	public int getIdInvoiceItem() {
		return idInvoiceItem;
	}
	public void setIdInvoiceItem(int idInvoiceItem) {
		this.idInvoiceItem = idInvoiceItem;
	}
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	public PurchaseOrderItem getPurchaseOrderItem() {
		return purchaseOrderItem;
	}
	public void setPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
		this.purchaseOrderItem = purchaseOrderItem;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
}	