package com.cherp.controllers;

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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.data.PurchaseDataManager;
import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Purchase;
import com.google.gson.stream.JsonWriter;

public class PurchaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//parameter with value true of false needed for formGenerator method
	private String dataLoader = "";
	
	private String jsonFilePath = "";

	private String operation = "";
	private String operationResp = "";

	private String date = "";
	private String van = "";
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

	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {

		operation = request.getParameter("operation");
		dataLoader = request.getParameter("dataLoader");
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		date = request.getParameter("date");
		van = request.getParameter("van");
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

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter pw = response.getWriter();
		getParaValues(request, response);
		System.out.println("In Purchase Servlet");

		System.out.println(date + " " + van + " " + driver1 + " " + driver2 + "  " + product + " " + company + " "
				+ location + " " + outstanding + " " + challanNo + " " + rent);
		

		//check which operation is performed(insert,update or delete)
		if (operation != null) {
			//For insert set ALL Parameters except ID
			if (operation.equals("insert")) {
				System.out.println("In insert");
				Purchase purchase = new Purchase();
				purchase.setDate(date);
				purchase.setVan(van);
				purchase.setDriver1(driver1);
				purchase.setDriver2(driver2);
				purchase.setCleaner1(cleaner1);
				purchase.setCleaner2(cleaner2);
				purchase.setCompany(company);
				purchase.setLocation(location);
				purchase.setOutstanding(Integer.parseInt(outstanding));
				purchase.setChallanNo(Integer.parseInt(challanNo));
				purchase.setRent(Integer.parseInt(rent));
				purchase.setProduct(product);
				purchase.setPieces(Integer.parseInt(pieces));
				purchase.setKg(Integer.parseInt(kg));
				purchase.setAvgWeight(Integer.parseInt(avgWeight));
				purchase.setAmount(Integer.parseInt(amount));
				purchase.setRate(Integer.parseInt(rate));

				operationResp = new PurchaseDataManager().insertData(purchase);
				pw.println(operationResp);

			}
		}

		
		//List for storing data that will be loaded into purchase form elements
		Map<String, ArrayList<String>> resultSetList = new HashMap<>();
		if (dataLoader != null) {
			if (dataLoader.equals("true")) {
				System.out.println("In Form Data Generator");
				resultSetList = new PurchaseDataManager().formDataGenerator();
				jsonFileWriter(resultSetList);
				
			}
		}

		
		
	}

	// method for creating json file
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
					//System.out.println("key:" + map.getKey() + ",value:" + value);
				}

				jw.endArray();
			}

			jw.endObject();
			jw.close();
		} catch (Exception e) {
		}
	}

}
