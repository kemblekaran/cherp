package com.cherp.controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.data.BankDataManager;
import com.cherp.data.UserDataManager;
import com.cherp.entities.Bank;
import com.cherp.entities.User;
import com.cherp.utils.JsonCreator;
import com.google.gson.stream.JsonWriter;

public class BankServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String jsonFilePath = "";

	private String bankName = "";
	private String branchName = "";
	private String accType = "";
	private String accNo = "";
	private String address = "";
	private String ifscCode = "";
	private String opBal = "";

	private String operation = "";

	private String operationResp = "";

	private String rowId = "";
	private String updatedCellBankName = "";
	private String updatedCellBranchName = "";
	private String updatedCellAccType = "";
	private String updatedCellAccNo = "";
	private String updatedCellAddress = "";
	private String updatedCellIfscCode = "";
	private String updatedCellOpBal = "";

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {
		// Add Update Or Delete Parameters
		operation = request.getParameter("operation");

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");
		System.out.println(jsonFilePath);

		// Insert Form Parameters
		bankName = request.getParameter("bankName");
		branchName = request.getParameter("branchName");
		accType = request.getParameter("accType");
		accNo = request.getParameter("accNo");
		address = request.getParameter("address");
		ifscCode = request.getParameter("ifscCode");
		opBal = request.getParameter("opBal");

		// Update Or Delete Parameters
		rowId = request.getParameter("updatedRow[id]");
		updatedCellBankName = request.getParameter("updatedRow[bankName]");
		updatedCellBranchName = request.getParameter("updatedRow[branchName]");
		updatedCellAccType = request.getParameter("updatedRow[accType]");
		updatedCellAccNo = request.getParameter("updatedRow[accNo]");
		updatedCellAddress = request.getParameter("updatedRow[address]");
		updatedCellIfscCode = request.getParameter("updatedRow[ifscCode]");
		updatedCellOpBal = request.getParameter("updatedRow[opBal]");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("In BankServlet");

		PrintWriter pw = response.getWriter();

		// get all form data into variables
		getParaValues(request, response);

		BankDataManager bdm = new BankDataManager();
		Bank bank = new Bank();

		if (operation != null) {
			// For insert set ALL Parameters except ID
			if (operation.equals("insert")) {

				System.out.println("Insert Function");
				bank.setBankName(bankName);
				bank.setBrachName(branchName);
				bank.setAccType(accType);
				bank.setAddress(address);
				bank.setAccNo(Long.parseLong(accNo));
				bank.setIfscCode(ifscCode);
				bank.setOpBal(Integer.parseInt(opBal));

				operationResp = bdm.addData(bank);
				pw.println(operationResp);

			} else if (operation.equals("update")) {
				// For update set ALL Parameters
				System.out.println("Update Function");
				bank.setId(Integer.parseInt(rowId));
				bank.setBankName(updatedCellBankName);
				bank.setBrachName(updatedCellBranchName);
				bank.setAccType(updatedCellAccType);
				bank.setAddress(updatedCellAddress);
				bank.setAccNo(Long.parseLong(updatedCellAccNo));
				bank.setIfscCode(updatedCellIfscCode);
				bank.setOpBal(Integer.parseInt(updatedCellOpBal));

				operationResp = bdm.updateData(bank);
				pw.println(operationResp);

			} else if (operation.equals("delete")) {
				// For delete set only ID Parameter
				System.out.println("Delete Function");
				bank.setId(Integer.parseInt(rowId));
				operationResp = bdm.deleteData(bank);
				pw.println(operationResp);

			}
		}
		// Contains All Data in table
		List<Bank> bankList = new ArrayList<>();
		bankList = bdm.selectData();
		new JsonCreator().createJson(bankList,jsonFilePath+"bank.json");

	}

}
