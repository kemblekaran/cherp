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
import com.cherp.entities.Data;
import com.cherp.entities.Purchase;
import com.cherp.entities.Sales;
import com.cherp.entities.User;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

public class SalesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String jsonFilePath;
	private String operation = "";
	private String operationResp = "";

	 private String dataLoader = "";

	private String date = "";
	private String van = "";
	private String purchase = "";
	private String purchaseId = "";
	private String purchaseView = "";

	private String invoiceNo = "";
	private String customer = "";
	private String product = "";
	private String pieces = "";
	private String kg = "";
	private String rate = "";
	private String amount = "";
	private String avgWeight = "";

	private String productJson = "";

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {

		productJson = request.getParameter("productJson");

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		// Insert Form Parameters
		date = request.getParameter("date");
		van = request.getParameter("van");
		 dataLoader = request.getParameter("dataLoader");
		purchase = request.getParameter("purchase");
		purchaseId = request.getParameter("purchaseId");
		purchaseView = request.getParameter("purchaseView");

		// Insert Form Parameters for sales records
		invoiceNo = request.getParameter("invoiceNo");
		customer = request.getParameter("customer");
		product = request.getParameter("product");
		pieces = request.getParameter("pieces");
		kg = request.getParameter("kg");
		rate = request.getParameter("rate");
		amount = request.getParameter("amount");
		avgWeight = request.getParameter("avgWeight");
		operation = request.getParameter("operation");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		getParaValues(request, response);

		Purchase purchase = new Purchase();
		System.out.println("In sales Servlet");
		PrintWriter pw = response.getWriter();
		SalesDataManager sdm = new SalesDataManager();

		Gson gson = new Gson();
		Data jsonData = gson.fromJson(productJson, Data.class);

		if (operation != null) {
			if (operation.equals("insert")) {
				System.out.println("In sales Insert");
				for (Sales sales : jsonData.getSalesData()) {
					sales.setDate(date);
					sales.setVan(van);
					sales.setPurchaseId(Integer.parseInt(purchaseId));
					sales.setInvoiceNo(Integer.parseInt(invoiceNo));
					sales.setCustomer(customer);
					sales.setProduct(product);
					sales.setPieces(Integer.parseInt(pieces));
					sales.setKg(Integer.parseInt(kg));
					sales.setRate(Integer.parseInt(rate));
					sales.setAmount(Double.parseDouble(amount));
					sales.setAvgWeight(Double.parseDouble(avgWeight));

					operationResp = sdm.addData(sales);
				}

				pw.write(operationResp);

			}
		}

		// Select purchase data according to purchaseid
		if (purchaseView != null) {
			if (purchaseView.equals("true")) {
				List<Purchase> saleViewList = new ArrayList<>();
				purchase.setPurchaseId(Integer.parseInt(purchaseId));

				saleViewList = sdm.selectSales(purchase);
				jsonFileWriterSale(saleViewList);
			}
		}

		// List for storing data that will be loaded into purchase form elements
		List<Purchase> salesList = new ArrayList<>();

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

				System.out.println("Id:" + p.getId() + ",Pid:" + p.getPurchaseId());
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
