package com.binar.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="deleted_goods")
public class DeletedGoods {
	
	@Id
	@Column(name="id_deleted_goods")
	private int idDeletedGoods;
	
	@ManyToOne
	@Column(name="id_goods")
	private Goods goods;
	
	private int quantity;
	private int stock_quantity;
	Date timestamp;
	@Column(columnDefinition="TEXT")
	private String information;
	
	
	public int getIdDeletedGoods() {
		return idDeletedGoods;
	}
	public void setIdDeletedGoods(int idDeletedGoods) {
		this.idDeletedGoods = idDeletedGoods;
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
	public int getStock_quantity() {
		return stock_quantity;
	}
	public void setStock_quantity(int stock_quantity) {
		this.stock_quantity = stock_quantity;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	
}
