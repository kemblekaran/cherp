package com.cherp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity	
//@Table(name = "payment")
public class Payment {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	@Column(name = "paymentDate")
	private String paymentDate;
//	@Column(name = "company")
	private String company;
//	@Column(name = "purchaseId")
	private int purchaseId;
//	@Column(name = "purchaseDate")
	private String purchaseDate;
//	@Column(name = "finalAmount")
	private double finalAmount;
//	@Column(name = "balanceAmount")
	private double balanceAmount;
//	@Column(name = "paymentMode")
	private String paymentMode;
//	@Column(name = "name")
	private String name;
//	@Column(name = "chDate")
	private String chDate;
//	@Column(name = "chNo")
	private long chNo;
//	@Column(name = "toBePaid")
	private double toBePaid;
//	@Column(name = "payNow")
	private double payNow;
//	@Column(name = "closingBal")
	private double closingBal;
//	@Column(name = "status")
	private int status;
	
	
	public int getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public Double getFinalAmount() {
		return finalAmount;
	}
	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}
	public Double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public Double getToBePaid() {
		return toBePaid;
	}
	public void setToBePaid(Double toBePaid) {
		this.toBePaid = toBePaid;
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
	
	
	
}
