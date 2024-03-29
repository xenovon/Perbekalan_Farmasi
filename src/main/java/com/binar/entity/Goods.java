package com.binar.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.binar.entity.enumeration.EnumGoodsCategory;
import com.binar.entity.enumeration.EnumGoodsType;
import com.binar.entity.enumeration.EnumStockStatus;

@Entity
@Table(name="goods")
public class Goods {
	
	@Id
	@Column(name="id_goods")
	private String idGoods;
	
	@ManyToOne
	@Column(name="fk_insurance")
	private Insurance insurance;
	
	private String name;
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	EnumGoodsType  type;
	
	private String unit;
	
	@Column(name="current_stock")
	private int currentStock;
	
	@Column(columnDefinition="TEXT")
	private String information;
	
	@Column(name="goods_package")
	private String goodsPackage;
	
	@OneToMany
	private List<SupplierGoods> supplierGoods;
	
	private EnumGoodsCategory category;
	
	@Column(name="minimum_stock")
	private int minimumStock;
	
	private boolean important;
	
	private EnumStockStatus stockStatus;
	private double het;
	
	public EnumStockStatus getStockStatus() {
		return stockStatus;
	}
	public void setStockStatus(EnumStockStatus stockStatus) {
		this.stockStatus = stockStatus;
	}
	public List<SupplierGoods> getSupplierGoods() {
		return supplierGoods;
	}
	public void setSupplierGoods(List<SupplierGoods> supplierGoods) {
		this.supplierGoods = supplierGoods;
	}
	public String getIdGoods() {
		return idGoods;
	}

	public void setIdGoods(String idGoods) {
		this.idGoods = idGoods;
	}

	public Insurance getInsurance() {
		return insurance;
	}

	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EnumGoodsType getType() {
		return type;
	}

	public void setType(EnumGoodsType type) {
		this.type = type;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(int currentStock) {
		this.currentStock = currentStock;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public EnumGoodsCategory getCategory() {
		return category;
	}

	public void setCategory(EnumGoodsCategory category) {
		this.category = category;
	}

	public int getMinimumStock() {
		return minimumStock;
	}

	public void setMinimumStock(int minimumStock) {
		this.minimumStock = minimumStock;
	}

	public boolean isImportant() {
		return important;
	}

	public void setImportant(boolean isImportant) {
		this.important = isImportant;
	}

	public double getHet() {
		return het;
	}

	public void setHet(double het) {
		this.het = het;
	}
	
	
	
}
