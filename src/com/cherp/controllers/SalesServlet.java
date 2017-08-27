package com.cherp.controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.data.PurchaseDataManager;
import com.cherp.data.SalesDataManager;
import com.cherp.entities.Purchase;
import com.cherp.entities.Sales;
import com.cherp.entities.User;
import com.google.gson.stream.JsonWriter;

public class SalesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String jsonFilePath;

	private String dataLoader = "";

	private String date = "";
	private String van = "";
	private String purchaseId = "";
	private String purchaseView = "";

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		// Insert Form Parameters
		date = request.getParameter("date");
		van = request.getParameter("van");
		dataLoader = request.getParameter("dataLoader");
		purchaseId = request.getParameter("purchaseId");
		purchaseView = request.getParameter("purchaseView");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Purchase purchase = new Purchase();
		System.out.println("In sales Servlet");
		PrintWriter pw = response.getWriter();

		getParaValues(request, response);

		// System.out.println("van : " + van + ", date :" + date + ", dataLoader
		// :" +
		// dataLoader);
		System.out.println("purchaseId" + purchaseId);

		// Select purchase data according to purchaseid
		if (purchaseView != null) {
			if (purchaseView.equals("true")) {
				List<Purchase> saleViewList = new ArrayList<>();
				SalesDataManager sdm = new SalesDataManager();
				purchase.setPurchaseId(Integer.parseInt(purchaseId));

				saleViewList = sdm.selectSales(purchase);
				jsonFileWriterSale(saleViewList);
			}
		}

		// List for storing data that will be loaded into purchase form elements
		List<Purchase> salesList = new ArrayList<>();
	
		SalesDataManager sdm = new SalesDataManager();
		Sales sales = new Sales();
		sales.setDate(date);
		sales.setVan(van);

		
		salesList = sdm.tableDataGenerator(sales);
		
	}

	// method for creating json file for saleView.json
	public void jsonFileWriterSale(List<Purchase> saleViewList) {
		try {

			// MOST IMPORTANT
			// The second parameter in constructor of FileWriter opens file in append mode
			Writer writer = new FileWriter(jsonFilePath + "saleView.json");
			JsonWriter jw = new JsonWriter(writer);
			System.out.println("Inside jsonFileWriterSale");

			jw.beginObject();
			jw.name("data");
			jw.beginArray();
			for (Purchase p : saleViewList) {

				System.out.println("Id:"+p.getId()+",Pid:"+p.getPurchaseId());
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
				jw.endObject();

			}
			jw.endArray();
			jw.endObject();
			jw.close();
		} catch (Exception e) {
		}
	}

}
