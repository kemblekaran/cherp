package com.cherp.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CmpLedgerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String jsonFilePath;

	private String cmpName = "";
	private String dateAccOp = "";
	private String fromDate = "";
	private String toDate = "";
	private String opBal = "";
	private String totalKgs = "";
	private String totalPcs = "";
	private String weekPayment = "";
	private String weekPurchase = "";
	private String totalPayment = "";
	private String paymentGiven = "";
	private String addLess = "";
	private String closingBal = "";

	private String cmpLedgerJson = "";
	
	private String operation = "";

	private String operationResp = "";

	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		operation = request.getParameter("operation");

		// Insert Form Parameters
		cmpName = request.getParameter("cmpName");
		cmpName = request.getParameter("cmpName");
		cmpName = request.getParameter("cmpName");
		cmpName = request.getParameter("cmpName");
		cmpName = request.getParameter("cmpName");
		cmpName = request.getParameter("cmpName");
		cmpName = request.getParameter("cmpName");
		
		cmpLedgerJson = request.getParameter("cmpLedgerJson");
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getParaValues(request, response);
		System.out.println("In companyLedger servlet");
		
		System.out.println(cmpLedgerJson);
	}

}
