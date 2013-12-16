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
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	@Column(name="supplier_address")
	private String  supplierAddress;
	private String city;
	@Column(name="phone_number")
	private String  phoneNumber;
	
	private String  email;
	
	private String fax;

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
