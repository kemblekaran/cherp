package com.cherp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "purchase")
public class Purchase {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name= "purchaseId")
	private int purchaseId;
	@Column(name="date")
	private String date;
	@Column(name="vanName")
	private String vanName;
	@Column(name="driver1")
	private String driver1;
	@Column(name="driver2")
	private String driver2;
	@Column(name="cleaner1")
	private String cleaner1;
	@Column(name="cleaner2")
	private String cleaner2;
	@Column(name="company")
	private String company;
	@Column(name="location")
	private String location;
	@Column(name="outstanding")
	private int outstanding;
	@Column(name="challanNo")
	private long challanNo;
	@Column(name="rent")
	private int rent;
	@Column(name="product")
	private String product;
	@Column(name="pieces")
	private int pieces;
	@Column(name="kg")
	private int kg;
	@Column(name="rate")
	private int rate;
	@Column(name="amount")
	private int amount;
	@Column(name="avgWeight")
	private Double avgWeight;
	@Column(name="finalAmount")
	private Double finalAmount;
	@Column(name="status")
	private int status;
	@Transient
	private int invoiceNo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getVanName() {
		return vanName;
	}

	public void setVanName(String vanName) {
		this.vanName = vanName;
	}

	public String getDriver1() {
		return driver1;
	}

	public void setDriver1(String driver1) {
		this.driver1 = driver1;
	}

	public String getDriver2() {
		return driver2;
	}

	public void setDriver2(String driver2) {
		this.driver2 = driver2;
	}

	public String getCleaner1() {
		return cleaner1;
	}

	public void setCleaner1(String cleaner1) {
		this.cleaner1 = cleaner1;
	}

	public String getCleaner2() {
		return cleaner2;
	}

	public void setCleaner2(String cleaner2) {
		this.cleaner2 = cleaner2;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(int outstanding) {
		this.outstanding = outstanding;
	}

	public long getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(long challanNo) {
		this.challanNo = challanNo;
	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getPieces() {
		return pieces;
	}

	public void setPieces(int pieces) {
		this.pieces = pieces;
	}

	public int getKg() {
		return kg;
	}

	public void setKg(int kg) {
		this.kg = kg;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Double getAvgWeight() {
		return avgWeight;
	}

	public void setAvgWeight(Double avgWeight) {
		this.avgWeight = avgWeight;
	}

	public Double getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}

	public int getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


}
