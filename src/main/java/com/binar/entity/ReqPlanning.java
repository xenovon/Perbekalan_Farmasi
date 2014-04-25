package com.binar.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;
import org.joda.time.JodaTimePermission;

@Entity 
@Table(name="req_planning")
public class ReqPlanning {

	@Id
	@Column(name="id_req_planning")
	private int idReqPlanning;
	
	@ManyToOne
	@Column(name="fk")
	private SupplierGoods supplierGoods;
	
	private Date period;
	
	private int quantity;
	@Column(name="accepted_quantity")
	private int acceptedQuantity;
	
	private int acceptance;
	
	@Column(columnDefinition="TEXT")
	private String information;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateAccepted;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@Column(name="price_estimation")
	private double priceEstimation;
	
	@Column(name="price_estimation_ppn")
	private double priceEstimationPPN; 
	
	private boolean isPurchaseOrderCreated;
	@Column(columnDefinition="TEXT")
	private String comment;
	public boolean isPurchaseOrderCreated() {
		return isPurchaseOrderCreated;
	}
	public void setPurchaseOrderCreated(boolean isPurchaseOrderCreated) {
		this.isPurchaseOrderCreated = isPurchaseOrderCreated;
	}
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
	public Date getDateAccepted() {
		return dateAccepted;
	}
	public void setDateAccepted(Date dateAccepted) {
		this.dateAccepted = dateAccepted;
	}
	public Date getPeriod() {
		return period;
	}
	public String getPeriodString(){
		Calendar cal=Calendar.getInstance();
		cal.setTime(period);
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH);
		String monthString;
		switch(month){
			case 0 :monthString="Januari";break;
			case 1:monthString="Februari";break;
			case 2:monthString="Maret";break;
			case 3:monthString="April";break;
			case 4:monthString="Mei";break;
			case 5:monthString="Juni";break;
			case 6:monthString="Juli";break;
			case 7:monthString="Agustus";break;
			case 8:monthString="September";break;
			case 9:monthString="Oktober";break;
			case 10:monthString="November";break;
			case 11:monthString="Desember";break;
			default:monthString="Januari";break;
		}
		return monthString+"-"+year;
		
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
	public int getAcceptance() {
		return acceptance;
	}

	public void setAcceptance(int acceptance) {
		this.acceptance = acceptance;
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

	public double getPriceEstimation() {
		return priceEstimation;
	}

	public void setPriceEstimation(double priceEstimation) {
		this.priceEstimation = priceEstimation;
	}
	
	public double getPriceEstimationPPN() {
		return priceEstimationPPN;
	}
	public void setPriceEstimationPPN(double priceEstimationPPN) {
		this.priceEstimationPPN = priceEstimationPPN;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
