package com.cherp.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cherp.data.AreaDataManager;
import com.cherp.data.PurchaseDataManager;
import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Area;
import com.cherp.entities.Purchase;
import com.google.gson.stream.JsonWriter;

public class PurchaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// parameter with value true of false needed for formGenerator method
	private String dataLoader = "";
	private String purchaseId = "";
	private String purchaseView = "";

	private String jsonFilePath = "";

	private String operation = "";
	private String operationResp = "";

	private String date = "";
	private String vanName = "";
	private String driver1 = "";
	private String driver2 = "";
	private String cleaner1 = "";
	private String cleaner2 = "";
	private String company = "";
	private String location = "";
	private String outstanding;
	private String challanNo;
	private String rent;
	private String product = "";
	private String pieces = "";
	private String kg = "";
	private String rate = "";
	private String amount = "";
	private String avgWeight = "";
	private String combinePurchaseToggle = "";

	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {

		operation = request.getParameter("operation");
		dataLoader = request.getParameter("dataLoader");
		purchaseId = request.getParameter("purchaseId");
		purchaseView = request.getParameter("purchaseView");

		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		date = request.getParameter("date");
		vanName = request.getParameter("vanName");
		driver1 = request.getParameter("driver1");
		driver2 = request.getParameter("driver2");
		cleaner1 = request.getParameter("cleaner1");
		cleaner2 = request.getParameter("cleaner2");
		company = request.getParameter("company");
		location = request.getParameter("location");
		outstanding = request.getParameter("outstanding");
		challanNo = request.getParameter("challanNo");
		rent = request.getParameter("rent");

		product = request.getParameter("product");
		pieces = request.getParameter("pieces");
		kg = request.getParameter("kg");
		rate = request.getParameter("rate");
		amount = request.getParameter("amount");
		avgWeight = request.getParameter("avgWeight");

		combinePurchaseToggle = request.getParameter("combinePurchaseToggle");
		if (combinePurchaseToggle == null) {
			combinePurchaseToggle = "off";
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter pw = response.getWriter();
		System.out.println("In Purchase Servlet");
		getParaValues(request, response);

		System.out.println("Data:" + date + ",Van:" + vanName + ",Driver1:" + driver1 + ",Driver2:" + driver2
				+ ",Product:" + product + ",Company:" + company + ",Location:" + location + ",Outstanding:"
				+ outstanding + ",ChallanNo:" + challanNo + ",Rent:" + rent + ",AvgWeight:" + avgWeight);

		System.out.println("purchaseId" + purchaseId);

		PurchaseDataManager pdm = new PurchaseDataManager();
		Purchase purchase = new Purchase();

		// check which operation is performed(insert,update or delete)
		if (operation != null) {
			// For insert set ALL Parameters except ID
			if (operation.equals("insert")) {
				System.out.println("In insert");
				purchase.setDate(date);
				purchase.setVanName(vanName);
				purchase.setDriver1(driver1);
				purchase.setDriver2(driver2);
				purchase.setCleaner1(cleaner1);
				purchase.setCleaner2(cleaner2);
				purchase.setCompany(company);
				purchase.setLocation(location);
				purchase.setOutstanding(Long.parseLong(outstanding));
				purchase.setChallanNo(Long.parseLong(challanNo));
				purchase.setRent(Integer.parseInt(rent));
				purchase.setProduct(product);
				purchase.setPieces(Integer.parseInt(pieces));
				purchase.setKg(Long.parseLong(kg));
				purchase.setAvgWeight(Long.parseLong(avgWeight));
				purchase.setAmount(Long.parseLong(amount));
				purchase.setRate(Long.parseLong(rate));
				purchase.setCombinePurchaseToggle(combinePurchaseToggle);

				operationResp = new PurchaseDataManager().insertData(purchase);
				pw.println(operationResp);

			}
		}
		
		//Select purchase data according to purchaseid
		if (purchaseView != null) {
			if (purchaseView.equals("true")) {
				List<Purchase> saleViewList = new ArrayList<>();
				purchase.setPurchaseId(Integer.parseInt(purchaseId));;
				saleViewList = pdm.selectSales(purchase);
				jsonFileWriterSale(saleViewList);
			}
		}
		
		// List for storing data that will be loaded into purchase form elements
		Map<String, ArrayList<String>> resultSetList = new HashMap<>();
		if (dataLoader != null) {
			if (dataLoader.equals("true")) {
				System.out.println("In Form Data Generator");
				resultSetList = new PurchaseDataManager().formDataGenerator();
				jsonFileWriter(resultSetList);

			}
		}

		// Contains All Data in table
		List<Purchase> purchaseViewList = new ArrayList<>();
		purchaseViewList = pdm.selectData();
		jsonFileWriter(purchaseViewList);

	}

	// method for creating json file for loading data into inputs of purchase.html
	public void jsonFileWriter(Map<String, ArrayList<String>> resultSetList) {
		try {
			Writer writer = new FileWriter(jsonFilePath + "purchaseLoader.json");

			JsonWriter jw = new JsonWriter(writer);
			jw.beginObject();

			for (Map.Entry<String, ArrayList<String>> map : resultSetList.entrySet()) {
				jw.name(map.getKey());
				jw.beginArray();

				for (String value : map.getValue()) {
					jw.beginObject();
					jw.name("name").value(value);
					jw.endObject();
					// System.out.println("key:" + map.getKey() + ",value:" + value);
				}

				jw.endArray();
			}

			jw.endObject();
			jw.close();
		} catch (Exception e) {
		}
	}

	// method for creating json file for purchaseView.json
	public void jsonFileWriter(List<Purchase> purchaseViewList) {
		try {
			Writer writer = new FileWriter(jsonFilePath + "purchaseView.json");
			JsonWriter jw = new JsonWriter(writer);
			jw.beginObject();
			jw.name("data");
			jw.beginArray();
			for (Purchase p : purchaseViewList) {
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
	
	// method for creating json file for saleView.json
		public void jsonFileWriterSale(List<Purchase> saleViewList) {
			try {
				Writer writer = new FileWriter(jsonFilePath + "saleView.json");
				JsonWriter jw = new JsonWriter(writer);
				jw.beginObject();
				jw.name("data");
				jw.beginArray();
				for (Purchase p : saleViewList) {
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
