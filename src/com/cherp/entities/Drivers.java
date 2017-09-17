package com.cherp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Driver")
public class Drivers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "fname")
	private String fname;
	@Column(name = "lname")
	private String lname;
	@Column(name = "curAdd")
	private String curAdd;
	@Column(name="perAdd")
	private String perAdd;
	@Column(name = "state")
	private String state;
	@Column(name = "city")
	private String city;
	@Column(name = "drlicense")
	private String drlicense;
	@Column(name = "panNo")
	private String panNo;
	@Column(name = "adhaarNo")
	private String adhaarNo;
	@Column(name = "photo")
	private String photo;
	@Column(name = "mobile")
	private long mobile;
	@Column(name = "phone")
	private long phone;
	@Column(name = "status")
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getCurAdd() {
		return curAdd;
	}

	public void setCurAdd(String curAdd) {
		this.curAdd = curAdd;
	}

	public String getPerAdd() {
		return perAdd;
	}

	public void setPerAdd(String perAdd) {
		this.perAdd = perAdd;
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

	public String getDrlicense() {
		return drlicense;
	}

	public void setDrlicense(String drlicense) {
		this.drlicense = drlicense;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getAdhaarNo() {
		return adhaarNo;
	}

	public void setAdhaarNo(String adhaarNo) {
		this.adhaarNo = adhaarNo;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
