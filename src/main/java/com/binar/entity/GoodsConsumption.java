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
@Table(name="goods_consumption")
public class GoodsConsumption {

	@Id
	@Column(name="id_goods_consumption")
	private int idGoodsConsumption;
	
	@ManyToOne
	@Column(name="fk_goods")
	private Goods goods; 
	
	private int quantity;
	
	@Column(name="consumption_date")
	private Date consumptionDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@Column(name="stock_quantity")
	private int stockQuantity;
	
	@Column(columnDefinition="TEXT")
	private String information;

	public int getIdGoodsConsumption() {
		return idGoodsConsumption;
	}

	public void setIdGoodsConsumption(int idGoodsConsumption) {
		this.idGoodsConsumption = idGoodsConsumption;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}
	
	public Date getConsumptionDate() {
		return consumptionDate;
	}
	public void setConsumptionDate(Date consumptionDate) {
		this.consumptionDate = consumptionDate;
	}
}
