package com.cherp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
// @Table(name = "payment")
public class Collection {

	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	// @Column(name = "collectionDate")
	private String collectionDate;
	// @Column(name = "customer")
	private String customer;
	// @Column(name = "area")
	private String area;
	// @Column(name = "invoiceNo")
	private int invoiceNo;
	// @Column(name = "salesDate")
	private String salesDate;
	// @Column(name = "invoiceAmount")
	private Double invoiceAmount;
	// @Column(name = "balanceAmount")
	private Double balanceAmount;
	// @Column(name = "paymentMode")
	private String collectionMode;
	// @Column(name = "name")
	private String name;
	// @Column(name = "depositIn")
	private String depositIn;
	// @Column(name = "branch")
	private String branch;
	// @Column(name = "chDate")
	private String chDate;
	// @Column(name = "chNo")
	private long chNo;
	// @Column(name = "toBeReceived")
	private Double toBeReceived;
	// @Column(name = "payNow")
	private Double payNow;
	// @Column(name = "closingBal")
	private Double closingBal;
	// @Column(name = "status")
	private int status;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCollectionDate() {
		return collectionDate;
	}
	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getSalesDate() {
		return salesDate;
	}
	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}
	public Double getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public Double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getCollectionMode() {
		return collectionMode;
	}
	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getDepositIn() {
		return depositIn;
	}
	public void setDepositIn(String depositIn) {
		this.depositIn = depositIn;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getChDate() {
		return chDate;
	}
	public void setChDate(String chDate) {
		this.chDate = chDate;
	}
	public long getChNo() {
		return chNo;
	}
	public void setChNo(long chNo) {
		this.chNo = chNo;
	}
	public Double getToBeReceived() {
		return toBeReceived;
	}
	public void setToBeReceived(Double toBeReceived) {
		this.toBeReceived = toBeReceived;
	}
	public Double getPayNow() {
		return payNow;
	}
	public void setPayNow(Double payNow) {
		this.payNow = payNow;
	}
	public Double getClosingBal() {
		return closingBal;
	}
	public void setClosingBal(Double closingBal) {
		this.closingBal = closingBal;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
