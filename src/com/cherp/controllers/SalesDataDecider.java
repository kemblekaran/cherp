package com.cherp.controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.data.SalesDataSelectorManager;
import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Data;
import com.cherp.entities.Purchase;
import com.google.gson.stream.JsonWriter;

/**
 * Servlet implementation class SalesDataDecider
 */
public class SalesDataDecider extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String jsonFilePath = "";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("In SalesDataDecider");

		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		String date = request.getParameter("date");
		String van = request.getParameter("van");

		System.out.println(date + van);
		Purchase purchase = new Purchase();

		SalesDataSelectorManager sm = new SalesDataSelectorManager();
		purchase.setVanName(van);
		purchase.setDate(date);

		List<Purchase> salesSelectorList = new ArrayList<>();
		salesSelectorList = sm.DataSelector(purchase);
		jsonFileWriter(salesSelectorList);

	}

	// method for creating json file for purchaseView.json
	public void jsonFileWriter(List<Purchase> salesSelectorList) {
		try {
			System.out.println("In salesDataSelector json writer");
			Writer writer = new FileWriter(jsonFilePath + "salesDataSelector.json");
			JsonWriter jw = new JsonWriter(writer);
			jw.beginObject();
			jw.name("data");
			jw.beginArray();
			for (Purchase p : salesSelectorList) {
				jw.beginObject();
				jw.name("id").value(p.getId());
				jw.name("purchaseId").value(p.getPurchaseId());
				jw.name("date").value(p.getDate());
				jw.name("vanName").value(p.getVanName());
				jw.name("driver1").value(p.getDriver1());
				jw.name("driver2").value(p.getDriver2());
				jw.name("cleaner1").value(p.getCleaner1());
				jw.name("cleaner2").value(p.getCleaner2());
				jw.name("company").value(p.getCompany());
				jw.name("location").value(p.getLocation());
				jw.name("outstanding").value(p.getOutstanding());
				jw.name("challanNo").value(p.getChallanNo());
				jw.name("rent").value(p.getRent());
				jw.name("product").value(p.getProduct());
				jw.name("pieces").value(p.getPieces());
				jw.name("kg").value(p.getKg());
				jw.name("rate").value(p.getRate());
				jw.name("amount").value(p.getAmount());
				jw.name("avgWeight").value(p.getAvgWeight());
				jw.name("finalAmount").value(p.getFinalAmount());
				jw.endObject();
			}
			jw.endArray();
			jw.endObject();
			jw.close();
		} catch (Exception e) {
		}
	}

}
