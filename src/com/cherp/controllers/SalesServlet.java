package com.cherp.controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.dao.dataentry.PurchaseDao;
import com.cherp.dao.dataentry.SalesDAO;
import com.cherp.entities.Data;
import com.cherp.entities.Purchase;
import com.cherp.entities.Sales;
import com.cherp.entities.SalesLoad;
import com.cherp.utils.JsonCreator;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

public class SalesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String jsonFilePath;
	private String operation = "";
	private String operationResp = "";

	private String salesDate = "";
	private String purchaseDate = "";
	private String van = "";
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
	private String salesLoadJson = "";
	
	private String invoiceNoLoader = "";
	
	private String company = "";

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {

		productJson = request.getParameter("productJson");
		salesLoadJson = request.getParameter("salesLoadJson");

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		salesDate = request.getParameter("salesDate");
		System.out.println("sales date "+ salesDate);
		invoiceNoLoader = request.getParameter("invoiceNoLoader");
		System.out.println("invoiceNoLoader-------"+invoiceNoLoader);
		
		// Insert Form Parameters
		purchaseDate = request.getParameter("purchaseDate");
		System.out.println("purchase date received in sales servlet " + purchaseDate);
		van = request.getParameter("van");
		System.out.println("van name received in sales servlet " + van);
		purchaseId = request.getParameter("purchaseId");
		System.out.println("purchase id received in sales servlet " + purchaseId);
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
		
		company = request.getParameter("companyName");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		getParaValues(request, response);

		System.out.println("In sales Servlet");
		PrintWriter pw = response.getWriter();

		Gson gson = new Gson();

		// Contains JSON Data of product Table on Sales.html
		Data jsonData = gson.fromJson(productJson, Data.class);
		Data salesData = gson.fromJson(salesLoadJson, Data.class);
		System.out.println("productJson ----"+productJson);
		System.out.println("salesLoad---"+salesLoadJson);
		// Inserts Record into Sales Table(DB)
		if (operation != null) {
			if (operation.equals("insert")) {
				System.out.println(" in insert");
				for (Sales sales : jsonData.getSalesData()) {
					sales.setPurchaseDate(purchaseDate);
					sales.setVan(van);
					sales.setPurchaseId(purchaseId);
					sales.setInvoiceNo(Integer.parseInt(invoiceNo));
					sales.setCustomer(customer);
					sales.setProduct(product);
					sales.setPieces(Integer.parseInt(pieces));
					sales.setKg(Integer.parseInt(kg));
					sales.setRate(Integer.parseInt(rate));
					sales.setAmount(Double.parseDouble(amount));
					sales.setAvgWeight(Double.parseDouble(avgWeight));
					sales.setSalesDate(salesDate);
					sales.setStatus(1);
					operationResp = new SalesDAO().insert(sales);
				}
				
				for(SalesLoad salesload : salesData.getSalesLoadData()) {
					salesload.setInvoiceNo(Integer.parseInt(invoiceNo));
					salesload.setCustomer(customer);
					salesload.setDate(new Timestamp(System.currentTimeMillis()));
					salesload.setInvoiceAmount(Double.parseDouble(amount));
					salesload.setBalanceAmount(Double.parseDouble(amount));
					salesload.setStatus(1);
					operationResp = new SalesDAO().insertSales(salesload);
				}

				pw.write(operationResp);

			}
		}

		// Select purchase data according to Date and Van In purchaseTable(DB)
		if (purchaseView != null) {
			if (purchaseView.equals("true")) {
				System.out.println("purchaseView");
				List<Purchase> purchaseTableList = new ArrayList<>();
				purchaseTableList = new SalesDAO().selectAll(purchaseDate, van);
				
				
				// Writes into JSON File
//				jsonFileWriter(purchaseTableList);
				new JsonCreator().createJson(purchaseTableList, jsonFilePath + "salesTable.json");
			}
		}
		
		if(invoiceNoLoader != null) {
			if(invoiceNoLoader.equals("true")) {
				int invoiceNO = new SalesDAO().getInvoiceNo();
				System.out.println("invoice no in sales servlet "+invoiceNO);
				jsonInvoiceNoLoader(invoiceNO);
			}
		}
		
		List<Sales> salesViewList = new ArrayList<>();
		salesViewList = new SalesDAO().selectSales();
		new JsonCreator().createJson(salesViewList, jsonFilePath + "salesView.json");

	}
	
	public void jsonInvoiceNoLoader(int invoiceNO) {
		try {
			Writer writer = new FileWriter(jsonFilePath + "invoiceNo.json");
			JsonWriter jw = new JsonWriter(writer);
			jw.beginObject();
			jw.name("data");
			jw.beginArray();
			jw.beginObject();
			jw.name("invoiceNo").value(invoiceNO);
			jw.endObject();
			jw.endArray();
			jw.endObject();
			jw.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


}
