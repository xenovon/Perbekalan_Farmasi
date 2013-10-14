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
@Table(name="req_planning")
public class ReqPlanning {

	@Id
	@Column(name="id_req_planning")
	private int idReqPlanning;
	
	@ManyToOne
	@Column(name="id_supplier_goods")
	private SupplierGoods supplierGoods;
	
	private Date period;
	
	private int quantity;
	@Column(name="accepted_quantity")
	private int acceptedQuantity;
	
	@Column(name="is_accepted")
	private boolean isAccepted;
	
	@Column(columnDefinition="TEXT")
	private String information;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@Column(name="price_estimation")
	private int priceEstimation;

	public int getIdReqPlanning() {
		return idReqPlanning;
	}

	public void setIdReqPlanning(int idReqPlanning) {
		this.idReqPlanning = idReqPlanning;
	}

	public SupplierGoods getSupplierGoods() {
		return supplierGoods;
	}

	public void setSupplierGoods(SupplierGoods supplierGoods) {
		this.supplierGoods = supplierGoods;
	}

	public Date getPeriod() {
		return period;
	}

	public void setPeriod(Date period) {
		this.period = period;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getAcceptedQuantity() {
		return acceptedQuantity;
	}

	public void setAcceptedQuantity(int acceptedQuantity) {
		this.acceptedQuantity = acceptedQuantity;
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getPriceEstimation() {
		return priceEstimation;
	}

	public void setPriceEstimation(int priceEstimation) {
		this.priceEstimation = priceEstimation;
	}
	
	
}
