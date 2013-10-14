package com.binar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="insurance")
public class Insurance {

	@Id
	@Column(name="id_insurance")
	private int idInsurance;
	
	private String name;
	
	@Column(columnDefinition="TEXT")
	private String description;

	public int getIdInsurance() {
		return idInsurance;
	}
	

	public void setIdInsurance(int idInsurance) {
		this.idInsurance = idInsurance;
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
	
	
}
