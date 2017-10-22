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

import com.cherp.dao.dataentry.PurchaseDao;
import com.cherp.data.PurchaseDataManager;
import com.cherp.entities.Data;
import com.cherp.entities.PayLoad;
import com.cherp.entities.Purchase;
import com.cherp.utils.JsonCreator;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

public class PurchaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// parameter with value true of false needed for formGenerator method
	private String dataLoader = "";

	private String jsonFilePath = "";

	private String operation = "";
	private String operationResp = "";

	private String purchaseId = "";
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
	private String finalAmount = "";

	private String productJson = "";
	private String payloadJson = "";

	private String vanWiseSales = "";
	
	private String updatePurchase = "";
	private String balancePieces = "";
	private String balanceKG = "";

	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {

		operation = request.getParameter("operation");
		dataLoader = request.getParameter("dataLoader");
		productJson = request.getParameter("productJson");
		payloadJson = request.getParameter("payloadJson");
		vanWiseSales = request.getParameter("vanWiseSales");

		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		purchaseId = request.getParameter("purchaseId");
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
		finalAmount = request.getParameter("finalAmount");
		
		updatePurchase = request.getParameter("updatePurchase");
		balancePieces = request.getParameter("balancePieces");
		balanceKG = request.getParameter("balanceKG");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter pw = response.getWriter();
		System.out.println("In Purchase Servlet\n\n");
		getParaValues(request, response);

		System.out.println("Date:" + date + ", vanName :" + vanName);
		System.out.println("finalAmount:" + finalAmount + "\n");
		PurchaseDataManager pdm = new PurchaseDataManager();
		// Purchase purchase = new Purchase();

		Gson gson = new Gson();
		Data jsonData = gson.fromJson(productJson, Data.class);
		Data payData = gson.fromJson(payloadJson, Data.class);

		System.out.println("payloadjson----------------- " + payloadJson);
		// check which operation is performed(insert,update or delete)
		if (operation != null) {
			// For insert set ALL Parameters except ID
			if (operation.equals("insert")) {
				System.out.println("In insert" + "\n");
				int count = 0;
				for (Purchase purchase : jsonData.getData()) {
					System.out.println("Counter:" + count++);
					purchase.setBalancePieces(0);
					purchase.setBalanceKG(0);
					purchase.setFinalAmount(Double.parseDouble(finalAmount));
					purchase.setStatus(1);
					System.out.println("purchase final amount" + Double.parseDouble(finalAmount));
					// operationResp = pdm.insertData(purchase);
					operationResp = new PurchaseDao().insert(purchase);
				}
			}
			if (operation.equals("insert")) {

				for (PayLoad payload : payData.getPayLoadData()) {
//					PayLoad payload = new PayLoad();
//					payload.setPurchaseId(Integer.parseInt(purchaseId));
//					payload.setDate(date);
//					payload.setCompany(company);
					payload.setFinalAmount(Double.parseDouble(finalAmount));
					payload.setBalanceAmount(Double.parseDouble(finalAmount));
					payload.setStatus(1);
					System.out.println("payload final amount" + Double.parseDouble(finalAmount));
					new PurchaseDao().insertPay(payload);
				}
				pw.println(operationResp);
			}

		}

		// List for storing data that will be loaded into purchase form elements
		Map<String, ArrayList<String>> resultSetList = new HashMap<>();
		if (dataLoader != null) {
			if (dataLoader.equals("true")) {
				System.out.println("In Form Data Generator");
				resultSetList = new PurchaseDao().formDataGenerator();
				int purchaseId = new PurchaseDao().getPurchaseId();
				System.out.println("purchaseId :" + purchaseId);
				jsonFileWriter(resultSetList, purchaseId);

			}
		}

		// Contains All Data in table
		List<Purchase> purchaseViewList = new ArrayList<>();
		purchaseViewList = new PurchaseDao().selectAll();
		new JsonCreator().createJson(purchaseViewList, jsonFilePath + "purchaseView.json");
		// jsonFileWriter(purchaseViewList);

		if (vanWiseSales != null) {
			if (vanWiseSales.equals("true")) {
				List<Purchase> vanWiseSalesList = new ArrayList<>();
				vanWiseSalesList = new PurchaseDao().selectVanWise(date, vanName);
				new JsonCreator().createJson(vanWiseSalesList, jsonFilePath + "vanWiseSales.json");
			}
		}
	}

	// method for creating json file for loading data into inputs of
	// purchase.html
	public void jsonFileWriter(Map<String, ArrayList<String>> resultSetList, int purchaseId) {
		try {
			System.out.println("In purchase loader json writer");
			Writer writer = new FileWriter(jsonFilePath + "purchaseLoader.json");

			JsonWriter jw = new JsonWriter(writer);
			jw.beginObject();
			jw.name("purchaseId").value(purchaseId);
			for (Map.Entry<String, ArrayList<String>> map : resultSetList.entrySet()) {
				jw.name(map.getKey());
				jw.beginArray();

				for (String value : map.getValue()) {
					jw.beginObject();
					jw.name("name").value(value);
					jw.endObject();
					// System.out.println("key:" + map.getKey() + ",value:" +
					// value);
				}

				jw.endArray();
			}

			jw.endObject();
			jw.close();
		} catch (Exception e) {
		}
	}

	// method for creating json file for purchaseView.json
//	public void jsonFileWriterList(List<Purchase> vanWiseSalesList) {
//		try {
//			System.out.println("In vanWiseSales json writer");
//			Writer writer = new FileWriter(jsonFilePath + "vanWiseSales.json");
//			JsonWriter jw = new JsonWriter(writer);
//			jw.beginObject();
//			jw.name("data");
//			jw.beginArray();
//			for (Purchase p : vanWiseSalesList) {
//				jw.beginObject();
//				jw.name("purchaseId").value(p.getPurchaseId());
//
//				jw.name("driver1").value(p.getDriver1());
//				jw.name("driver2").value(p.getDriver2());
//				jw.name("cleaner1").value(p.getCleaner1());
//				jw.name("cleaner2").value(p.getCleaner2());
//				jw.name("rate").value(p.getRate());
//				jw.name("amount").value(p.getAmount());
//				jw.name("avgWeight").value(p.getAvgWeight());
//				jw.name("finalAmount").value(p.getFinalAmount());
//				jw.endObject();
//			}
//			jw.endArray();
//			jw.endObject();
//			jw.close();
//		} catch (Exception e) {
//		}
//	}

}
