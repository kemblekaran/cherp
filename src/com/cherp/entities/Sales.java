package com.cherp.entities;

import java.sql.ResultSet;
import java.util.List;

public class Sales {
	private int id;
	private int purchaseId;
	private int outstanding;
	private int salesAmount;
	private String date;
	private String van;
	private String area;
	private ResultSet data;
	
	//Sales Parameters
	private int invoiceNo;
	private String customer;
	private String product;
	private int pieces;
	private int kg;
	private int rate;
	private double amount;
	private double avgWeight;
	private int status;
	
	
	public ResultSet getData() {
		return data;
	}
	public void setData(ResultSet data) {
		this.data = data;
	}
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
	public int getOutstanding() {
		return outstanding;
	}
	public void setOutstanding(int outstanding) {
		this.outstanding = outstanding;
	}
	public int getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(int salesAmount) {
		this.salesAmount = salesAmount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getVan() {
		return van;
	}
	public void setVan(String van) {
		this.van = van;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	//sales Getters and Setters
	public int getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
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
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getAvgWeight() {
		return avgWeight;
	}
	public void setAvgWeight(double avgWeight) {
		this.avgWeight = avgWeight;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
