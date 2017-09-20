package com.cherp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "preAdd")
	private String preAdd;
	@Column(name = "secAdd")
	private String secAdd;
	@Column(name = "phone")
	private Long phone;
	@Column(name = "state")
	private String state;
	@Column(name = "city")
	private String city;
	@Column(name = "pinCode")
	private int pinCode;
	@Column(name = "ownerName")
	private String ownerName;
	@Column(name = "panNo")
	private String panNo;
	@Column(name = "opBal")
	private int opBal;
	@Column(name = "status")
	private int status;

	public String getPanNo() {
		return panNo;
	}

	private Long mobile;

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPreAdd() {
		return preAdd;
	}

	public void setPreAdd(String preAdd) {
		this.preAdd = preAdd;
	}

	public String getSecAdd() {
		return secAdd;
	}

	public void setSecAdd(String secAdd) {
		this.secAdd = secAdd;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getPinCode() {
		return pinCode;
	}

	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public int getOpBal() {
		return opBal;
	}

	public void setOpBal(int opBal) {
		this.opBal = opBal;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
