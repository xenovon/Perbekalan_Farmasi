package com.binar.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="invoice")
public class Invoice {

	@Id
	@Column(name="id_invoice")
	private int idInvoice;
		
	@Column(name="invoice_number")
	private String invoiceNumber;
	
	private String invoiceName;
	
	@Column(name="due_date")
	private Date dueDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@Column(name="amoount_paid")
	private double amountPaid;
	
	@OneToMany(cascade=CascadeType.ALL)
	@Column(name="invoice_item")
	private List<InvoiceItem> invoiceItem;

	public Date getDueDate() {
		return dueDate;
	}
	public void setDue_date(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public int getIdInvoice() {
		return idInvoice;
	}

	public void setIdInvoice(int idInvoice) {
		this.idInvoice = idInvoice;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public List<InvoiceItem> getInvoiceItem() {
		return invoiceItem;
	}

	public void setInvoiceItem(List<InvoiceItem> invoiceItem) {
		this.invoiceItem = invoiceItem;
	}
	
	public String getInvoiceName() {
		return invoiceName;
	}
	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}
	
	public double getTotalPrice(){
		double total=0;
		for(InvoiceItem item:getInvoiceItem()){
			total=total+item.getTotalPrice();
		}
		return total;
	}

	
	
}
