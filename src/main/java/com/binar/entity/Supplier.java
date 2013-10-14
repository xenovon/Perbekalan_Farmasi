package com.binar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="supplier")
public class Supplier {

	@Id
	@Column(name="id_supplier")
	private int idSupplier;
	
	@Column(name="supplier_abbr")
	private String supplierAbbr;
	
	@Column(name="supplier_name")
	private String  supplierName;
	
	@Column(name="supplier_address")
	private String  supplierAddress;
	
	@Column(name="phone_number")
	private String  phoneNumber;
	
	@Column(name="email")
	private String  email;

	public int getIdSupplier() {
		return idSupplier;
	}

	public void setIdSupplier(int idSupplier) {
		this.idSupplier = idSupplier;
	}

	public String getSupplierAbbr() {
		return supplierAbbr;
	}

	public void setSupplierAbbr(String supplierAbbr) {
		this.supplierAbbr = supplierAbbr;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierAddress() {
		return supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
