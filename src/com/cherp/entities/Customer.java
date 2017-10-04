package com.cherp.entities;

public class Customer {
	private int id;
	private String fname;
	private String lname;
	private String shopName;
	private String curAdd;
	private String perAdd;
	private String state;
	private String city;
	private String area;
	private long mobile;
	private long phone;
	private String dateAccOp;
	private int opBal;
	private int status;

	
	
	
	public String getDateAccOp() {
		return dateAccOp;
	}

	public void setDateAccOp(String dateAccOp) {
		this.dateAccOp = dateAccOp;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * @param fname
	 *            the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * @param lname
	 *            the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}

	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}

	/**
	 * @param shopName
	 *            the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	/**
	 * @return the curAdd
	 */
	public String getCurAdd() {
		return curAdd;
	}

	/**
	 * @param curAdd
	 *            the curAdd to set
	 */
	public void setCurAdd(String curAdd) {
		this.curAdd = curAdd;
	}

	/**
	 * @return the perAdd
	 */
	public String getPerAdd() {
		return perAdd;
	}

	/**
	 * @param perAdd
	 *            the perAdd to set
	 */
	public void setPerAdd(String perAdd) {
		this.perAdd = perAdd;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
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

	public int getOpBal() {
		return opBal;
	}

	/**
	 * @param opBal
	 *            the opBal to set
	 */
	public void setOpBal(int opBal) {
		this.opBal = opBal;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

}
