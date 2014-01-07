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
@Table(name="deleted_goods")
public class DeletedGoods {
	
	@Id
	@Column(name="id_deleted_goods")
	private int idDeletedGoods;
		
	private int quantity;
	private int stockQuantity;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	@Column(columnDefinition="TEXT")
	private String information;
	@ManyToOne
    @Column(name= "fk_goods")
    private Goods goods ;
    @Column(name= "deletion_date")
    private Date deletionDate ;
    @Column(name= "is_accepted")
    private boolean isAccepted ;
   
    @Column(name= "approval_date" )
    private Date approvalDate;

	
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public Date getDeletionDate() {
		return deletionDate;
	}
	public void setDeletionDate(Date deletionDate) {
		this.deletionDate = deletionDate;
	}
	public boolean isAccepted() {
		return isAccepted;
	}
	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}
	public Date getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	public int getIdDeletedGoods() {
		return idDeletedGoods;
	}
	public void setIdDeletedGoods(int idDeletedGoods) {
		this.idDeletedGoods = idDeletedGoods;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
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
