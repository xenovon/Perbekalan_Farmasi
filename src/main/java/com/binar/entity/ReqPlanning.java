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
	
	@Column(name="is_accepted")
	private boolean isAccepted;
	
	@Column(columnDefinition="TEXT")
	private String information;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@Column(name="price_estimation")
	private double priceEstimation;

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

	public double getPriceEstimation() {
		return priceEstimation;
	}

	public void setPriceEstimation(double priceEstimation) {
		this.priceEstimation = priceEstimation;
	}
	
	
}
