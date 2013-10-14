package com.binar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="manufactur")
public class Manufacturer {

	@Id
	@Column(name="id_manufactur")
	private int idManufactur;
	
	@Column(name="manufactur_name")
	private String manufacturName;
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	private String address;
	
	private String phone_number;
	
	private String fax;

	public int getIdManufactur() {
		return idManufactur;
	}

	public void setIdManufactur(int idManufactur) {
		this.idManufactur = idManufactur;
	}

	public String getManufacturName() {
		return manufacturName;
	}

	public void setManufacturName(String manufacturName) {
		this.manufacturName = manufacturName;
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

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	
	
}
