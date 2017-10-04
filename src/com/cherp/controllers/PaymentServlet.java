package com.cherp.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.data.PaymentDataManager;
import com.cherp.entities.PayLoad;
import com.cherp.entities.Payment;
import com.cherp.utils.JsonCreator;

public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String jsonFilePath;

	// private String id;
	// private String purchaseId;
	// private String balanceAmount;

	private String paymentDate = "";
	private String company = "";
	// private String purchaseDate;
	private String paymentMode = "";
	private String name = "";
	private String chDate = "";
	private String chNo = "";
	private String toBePaid;
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
		// System.out.println(operation + " operation");

		// id = request.getParameter("id");
		// purchaseId = request.getParameter("purchaseId");
		// balanceAmount = request.getParameter("balanceAmount");
		//

		// Insert Form Parameters
		paymentDate = request.getParameter("paymentDate");
		company = request.getParameter("company");
		paymentMode = request.getParameter("paymentMode");
		name = request.getParameter("name");
		chDate = request.getParameter("chDate");
		chNo = request.getParameter("chNo");
		toBePaid = request.getParameter("toBePaid");
		payNow = request.getParameter("payNow");
		closingBal = request.getParameter("closingBal");
		// System.out.println(closingBal);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("In PaymentServelet");

		getParaValues(request, response);
		PrintWriter pw = response.getWriter();

		PaymentDataManager pdm = new PaymentDataManager();

		Payment payment = new Payment();
		PayLoad payload = new PayLoad();

		System.out.println(paymentDate + " " + company + " " + paymentMode + " " + name + " " + chDate + " " + chNo + ""
				+ toBePaid + " " + payNow + " " + closingBal);

		if (operation != null) {
			if (operation.equals("insert")) {

				System.out.println("Insert Function");

				payment.setPaymentDate(paymentDate);
				payment.setCompany(company);
				payment.setPaymentMode(paymentMode);
				if (paymentMode.equals("bank")) {
					payment.setName(name);
					payment.setChDate(chDate);
					payment.setChNo(Long.parseLong(chNo));
				}
				payment.setToBePaid((double) Integer.parseInt(toBePaid));
				payment.setPayNow((double) Integer.parseInt(payNow));
				payment.setClosingBal((double) Integer.parseInt(closingBal));

				operationResp = pdm.addData(payment); 
				operationResp = pdm.updatePayLoad(payment);

				operationResp = pdm.deletePayLoad(payload);

				pw.println(operationResp);

			}
		}

		// Contains All Data in table
		List<Payment> paymentList = new ArrayList<>();
		paymentList = new PaymentDataManager().selectData();
		new JsonCreator().createJson(paymentList, jsonFilePath + "payment.json");

		// Contains All Data in payload table
		List<PayLoad> payloadList = new ArrayList<>();
		payloadList = new PaymentDataManager().selectPayData();
		new JsonCreator().createJson(payloadList, jsonFilePath + "payload.json");
	}

}
