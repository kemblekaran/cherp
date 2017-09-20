package com.cherp.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.cherp.dao.dataentry.SalesDAO;
import com.cherp.entities.Data;
import com.cherp.entities.Purchase;
import com.cherp.entities.Sales;
import com.cherp.utils.JsonCreator;
import com.google.gson.Gson;

public class SalesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String jsonFilePath;
	private String operation = "";
	private String operationResp = "";

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

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {

		productJson = request.getParameter("productJson");

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		// Insert Form Parameters
		purchaseDate = request.getParameter("purchaseDate");
		van = request.getParameter("van");
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

		System.out.println("In sales Servlet");
		PrintWriter pw = response.getWriter();

		Gson gson = new Gson();
		
		//Contains JSON Data of product Table on Sales.html
		Data jsonData = gson.fromJson(productJson, Data.class);

		//Inserts Record into Sales Table(DB)
		if (operation != null) {
			if (operation.equals("insert")) {
				
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
					sales.setSalesDate(new Timestamp(System.currentTimeMillis()));
					sales.setStatus(1);
					operationResp = new SalesDAO().insert(sales);
				}

				pw.write(operationResp);

			}
		}

		// Select purchase data according to Date and Van In purchaseTable(DB)
		if (purchaseView != null) {
			if (purchaseView.equals("true")) {
				System.out.println("purchaseView");
				List<Purchase> salesTableList = new ArrayList<>();
				salesTableList = new SalesDAO().selectSales(purchaseDate, van);
		
				//Writes into JSON File
				new JsonCreator().createJson(salesTableList, jsonFilePath+"salesTable.json");
			}
		}

	}
}
