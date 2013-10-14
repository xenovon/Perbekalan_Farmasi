package com.binar.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {

	@Id
	@Column(name="id_user")
	private int idUser;
	
	@ManyToOne
	@Column(name="id_role")
	private Role role;
	
	@Column(name="employee_num")
	private String employeeNum;
	
	private String name;
	
	@Column(name="place_birth")
	private String placeBirth;
	
	@Column(name="date_of_birth")
	private Date dateOfBirth;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	private String address;
	
	private String email;

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmployeeNum() {
		return employeeNum;
	}

	public void setEmployeeNum(String employeeNum) {
		this.employeeNum = employeeNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlaceBirth() {
		return placeBirth;
	}

	public void setPlaceBirth(String placeBirth) {
		this.placeBirth = placeBirth;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
