package com.cherp.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.data.CollectionDataManager;
import com.cherp.entities.Collection;
import com.cherp.entities.SalesLoad;
import com.cherp.utils.JsonCreator;

public class CollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String jsonFilePath;

	// private String id;
	// private String purchaseId;
	// private String balanceAmount;

	private String collectionDate = "";
	private String customer = "";
	private String area = "";
	private String collectionMode = "";
	private String name = "";
	private String depositIn = "";
	private String branch = "";
	private String chDate = "";
	private String chNo = "";
	private String toBeReceived;
	private String payNow;
	private String closingBal;

	private String operation = "";

	private String operationResp = "";

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		// insert operation
		operation = request.getParameter("operation");
		

		// Insert Form Parameters
		collectionDate = request.getParameter("collectionDate");
		customer = request.getParameter("customer");
		area = request.getParameter("area");
		collectionMode = request.getParameter("collectionMode");
		name = request.getParameter("name");
		depositIn = request.getParameter("depositIn");
		branch = request.getParameter("branch");
		chDate = request.getParameter("chDate");
		chNo = request.getParameter("chNo");
		toBeReceived = request.getParameter("toBeReceived");
		payNow = request.getParameter("payNow");
		closingBal = request.getParameter("closingBal");
		// System.out.println(closingBal);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("In collectionServelet");

		getParaValues(request, response);
		PrintWriter pw = response.getWriter();

		CollectionDataManager cdm = new CollectionDataManager();

		Collection collection = new Collection();
		SalesLoad SalesLoad = new SalesLoad();

		System.out.println(collectionDate + " " + customer + " " + collectionMode + " " + name + " " + branch + " " +
		depositIn  + " " +chDate + " " + chNo + ""
				+ toBeReceived + " " + payNow + " " + closingBal);

		if (operation != null) {
			if (operation.equals("insert")) {

				System.out.println("Insert Function");

				collection.setCollectionDate(collectionDate);
				collection.setCustomer(customer);
				collection.setArea(area);
				collection.setCollectionMode(collectionMode);
				if (collectionMode.equals("bank")) {
					collection.setName(name);
					collection.setDepositIn(depositIn);
					collection.setBranch(branch);
					collection.setChDate(chDate);
					collection.setChNo(Long.parseLong(chNo));
				}
				collection.setToBeReceived(Double.parseDouble(toBeReceived));
				collection.setPayNow(Double.parseDouble(payNow));
				collection.setClosingBal(Double.parseDouble(closingBal));

				operationResp = cdm.addData(collection); 
				operationResp = cdm.updateSalesLoad(collection);

				operationResp = cdm.deleteSalesLoad(SalesLoad);

				pw.println(operationResp);

			}
		}

		// Contains All Data in table
		List<Collection> collectionList = new ArrayList<>();
		collectionList = new CollectionDataManager().selectData();
		new JsonCreator().createJson(collectionList, jsonFilePath + "collection.json");

		// Contains All Data in SalesLoad table
		List<SalesLoad> salesLoadList = new ArrayList<>();
		salesLoadList = new CollectionDataManager().selectSalesData();
		new JsonCreator().createJson(salesLoadList, jsonFilePath + "salesLoad.json");
	}

}
