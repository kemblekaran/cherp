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
	
	
}
