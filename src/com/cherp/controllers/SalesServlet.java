package com.cherp.controllers;

import java.io.IOException;
import java.io.PrintWriter;
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

public class SalesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String jsonFilePath;

	private String dataLoader = "";

	private String date = "";
	private String van = "";

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		// Insert Form Parameters
		date = request.getParameter("date");
		van = request.getParameter("van");
		dataLoader = request.getParameter("dataLoader");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("In sales Servlet");
		PrintWriter pw = response.getWriter();
		getParaValues(request, response);
		System.out.println("van : " + van + ", date :" + date + ", dataLoader :" + dataLoader);

		// List for storing data that will be loaded into purchase form elements
		List<Purchase> salesList = new ArrayList<>();
		// if (dataLoader != null) {
		// if (dataLoader.equals("true")) {
		SalesDataManager sdm = new SalesDataManager();
		Sales sales = new Sales();
		sales.setDate(date);
		sales.setVan(van);

		System.out.println("In Table Data Generator");
		salesList = sdm.tableDataGenerator(sales);
		System.out.println("sales"+salesList);
//		 jsonFileWriter(salesList);

		// }
		// }
	}

}
