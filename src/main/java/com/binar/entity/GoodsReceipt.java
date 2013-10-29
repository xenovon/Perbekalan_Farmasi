package com.binar.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="goods_receipt")
public class GoodsReceipt {

	@Id
	@Column(name="id_goods_receipt")
	private int idGoodsReceipt;
	
	@OneToOne
	@Column(name="fk")
	private InvoiceItem invoiceItem;
	

	@Column(name="quantity_received")
	private int quantityReceived;
	
	@Column(columnDefinition="TEXT")
	private String information;
	
	private Date date;
	@Column(name="expired_date")
	private Date expiredDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@Column(name="stock_quantity")
	private int stockQuantity;

	public int getIdGoodsReceipt() {
		return idGoodsReceipt;
	}

	public void setIdGoodsReceipt(int idGoodsReceipt) {
		this.idGoodsReceipt = idGoodsReceipt;
	}


	public int getQuantityReceived() {
		return quantityReceived;
	}

	public void setQuantityReceived(int quantityReceived) {
		this.quantityReceived = quantityReceived;
	}

	public String getInformation() {
		return information;
	}

	public InvoiceItem getInvoiceItem() {
		return invoiceItem;
	}

	public void setInvoiceItem(InvoiceItem invoiceItem) {
		this.invoiceItem = invoiceItem;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	
	
}
