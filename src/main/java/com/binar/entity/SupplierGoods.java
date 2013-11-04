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
@Table(name="supplier_goods")
public class SupplierGoods {

	@Id
	@Column(name="id_supplier_goods")
	private int idSupplierGoods;

	@ManyToOne
	@Column(name="fk")
	private Supplier supplier;
	
	@ManyToOne
	@Column(name="fk")
	private Goods goods;
	
	@ManyToOne
	@Column(name="fk")
	private Manufacturer manufacturer;
	
	
	@Column(name="last_price")
	private double lastPrice;
		
	@Column(name="last_update")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

	public int getIdSupplierGoods() {
		return idSupplierGoods;
	}

	public void setIdSupplierGoods(int idSupplierGoods) {
		this.idSupplierGoods = idSupplierGoods;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	
}
