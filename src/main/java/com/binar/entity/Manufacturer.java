package com.binar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="manufacturer")
public class Manufacturer {

	@Id
	@Column(name="id_manufacturer")
	private int idManufacturer;
	
	@Column(name="manufacturer_name")
	private String manufacturerName;
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	private String address;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	private String fax;

	public int getIdManufacturer() {
		return idManufacturer;
	}

	public void setIdManufacturer(int idManufacturer) {
		this.idManufacturer = idManufacturer;
	}

	public String getManufacturName() {
		return manufacturerName;
	}

	public void setManufacturName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	
	
}
