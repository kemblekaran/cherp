package com.cherp.entities;

import java.util.ArrayList;
import java.util.List;

public class Data {
	private List<Purchase> data = new ArrayList<>();

	public List<Purchase> getData() {
		return data;
	}

	public void setData(List<Purchase> data) {
		this.data = data;
	}

}
