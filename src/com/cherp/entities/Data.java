package com.cherp.entities;

import java.util.ArrayList;
import java.util.List;

public class Data {
	private List<Purchase> data = new ArrayList<>();
	private List<Purchase> dataSelector = new ArrayList<>();
	
	

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

}
