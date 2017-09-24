package com.cherp.entities;

import java.util.ArrayList;
import java.util.List;

public class Data {
	private List<Purchase> data = new ArrayList<>();
	private List<Sales> salesData = new ArrayList<>();
	private List<Purchase> dataSelector = new ArrayList<>();
	private List<PayLoad> payLoadData = new ArrayList<>();
	
	

	
	public List<PayLoad> getPayLoadData() {
		return payLoadData;
	}

	public void setPayLoadData(List<PayLoad> payLoadData) {
		this.payLoadData = payLoadData;
	}

	public List<Purchase> getDataSelector() {
		return dataSelector;
	}

	public void setDataSelector(List<Purchase> dataSelector) {
		this.dataSelector = dataSelector;
	}

	public List<Purchase> getData() {
		return data;
	}

	public void setData(List<Purchase> data) {
		this.data = data;
	}

	public List<Sales> getSalesData() {
		return salesData;
	}

	public void setSalesData(List<Sales> salesData) {
		this.salesData = salesData;
	}
	

}
