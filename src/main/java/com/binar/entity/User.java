package com.binar.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.codec.digest.DigestUtils;


@Entity
@Table(name="user")
public class User {

	@Id
	@Column(name="id_user")
	private int idUser;
	
	private String username;
	
	private String password;
	
	@ManyToOne
	@Column(name="fk")
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

	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {		
		this.password=password;
	}
	public void setPasswordHash(String password){
		this.password=getMD5(password);
	}
	private String getMD5(String input){
		final String KEYVAR="PURWOKERTO";
		String result=DigestUtils.md5Hex(KEYVAR + input+input.toUpperCase());
		return result;	
	}
	public String getPassword() {
		return password;
	}
	public boolean isPasswordMatch(String input){
		System.out.println("versus "+password+" "+getMD5(input)+" "+input);
		return this.password.equals(getMD5(input))?true:false;
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
