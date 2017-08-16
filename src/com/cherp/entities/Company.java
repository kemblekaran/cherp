package com.cherp.entities;

public class Company {

	private int id;
	private String name;
	private String preAdd;
	private String secAdd;
	private Long phone;
	private String state;
	private String city;
	private int pinCode;
	private String ownerName;
	private String panNo;
	private int opBal;
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
