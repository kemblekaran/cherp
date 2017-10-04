package com.cherp.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.data.CustomerDataManager;
import com.cherp.entities.Customer;
import com.cherp.utils.JsonCreator;

public class CustomerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private String jsonFilePath = "";
	private String fname = "";
	private String lname = "";
	private String shopName = "";
	private String curAdd = "";
	private String perAdd = "";
	private String state = "";
	private String city = "";
	private String area = "";
	private String mobile;
	private String phone;
	private String dateAccOp = "";
	private String opBal;
	private String operation = "";

	private String operationResp = "";

	private String rowId = "";
	private String updatedCellFname = "";
	private String updatedCellLname = "";
	private String updatedCellShopName = "";
	private String updatedCellCurAdd = "";
	private String updatedCellPerAdd = "";
	private String updatedCellState = "";
	private String updatedCellCity = "";
	private String updatedCellArea = "";
	private String updatedCellMobile = "";
	private String updatedCellPhone = "";
	private String updatedCellDateAccOp = "";
	private String updatedCellOpBal = "";

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {
		// Add Update Or Delete Parameters
		operation = request.getParameter("operation");

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		// Insert Form Parameters
		fname = request.getParameter("fname");
		lname = request.getParameter("lname");
		shopName = request.getParameter("shopName");
		curAdd = request.getParameter("curAdd");
		perAdd = request.getParameter("perAdd");
		state = request.getParameter("state");
		city = request.getParameter("city");
		area = request.getParameter("area");
		mobile = request.getParameter("mobile");
		phone = request.getParameter("phone");
		dateAccOp = request.getParameter("dateAccOp");
		opBal = request.getParameter("opBal");

		// Update Or Delete Parameters
		rowId = request.getParameter("updatedRow[id]");
		updatedCellFname = request.getParameter("updatedRow[fname]");
		updatedCellLname = request.getParameter("updatedRow[lname]");
		updatedCellShopName = request.getParameter("updatedRow[shopName]");
		updatedCellCurAdd = request.getParameter("updatedRow[curAdd]");
		updatedCellPerAdd = request.getParameter("updatedRow[perAdd]");
		updatedCellState = request.getParameter("updatedRow[state]");
		updatedCellCity = request.getParameter("updatedRow[city]");
		updatedCellArea = request.getParameter("updatedRow[area]");
		updatedCellMobile = request.getParameter("updatedRow[mobile]");
		updatedCellPhone = request.getParameter("updatedRow[phone]");
		updatedCellDateAccOp = request.getParameter("updatedRow[dateAccOp]");
		updatedCellOpBal = request.getParameter("updatedRow[opBal]");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("In CustomerServlet");

		PrintWriter pw = response.getWriter();

		// get all form data into variables
		getParaValues(request, response);

		CustomerDataManager custdm = new CustomerDataManager();
		Customer cust = new Customer();

		if (operation != null) {
			// For insert set ALL Parameters except ID
			if (operation.equals("insert")) {

				System.out.println("Insert Function");
				cust.setFname(fname);
				cust.setLname(lname);
				cust.setShopName(shopName);
				cust.setCurAdd(curAdd);
				cust.setPerAdd(perAdd);
				cust.setState(state);
				cust.setCity(city);
				cust.setArea(area);
				cust.setMobile(Long.parseLong(mobile));
				cust.setPhone(Long.parseLong(phone));
				cust.setDateAccOp(dateAccOp);
				cust.setOpBal(Integer.parseInt(opBal));

				operationResp = custdm.addData(cust);
				pw.println(operationResp);

			} else if (operation.equals("update")) {
				// For update set ALL Parameters
				System.out.println("Update Function");
				cust.setId(Integer.parseInt(rowId));
				cust.setFname(updatedCellFname);
				cust.setLname(updatedCellLname);
				cust.setShopName(updatedCellShopName);
				cust.setCurAdd(updatedCellCurAdd);
				cust.setPerAdd(updatedCellPerAdd);
				cust.setState(updatedCellState);
				cust.setCity(updatedCellCity);
				cust.setArea(updatedCellArea);
				cust.setMobile(Long.parseLong(updatedCellMobile));
				cust.setPhone(Long.parseLong(updatedCellPhone));
				cust.setDateAccOp(updatedCellDateAccOp);
				cust.setOpBal(Integer.parseInt(updatedCellOpBal));

				operationResp = custdm.updateData(cust);
				pw.println(operationResp);

			} else if (operation.equals("delete")) {
				// For delete set only ID Parameter
				System.out.println("Delete Function");
				cust.setId(Integer.parseInt(rowId));
				operationResp = custdm.deleteData(cust);
				pw.println(operationResp);

			}
		}

		// Contains All Data in table
		List<Customer> custList = new ArrayList<>();
		custList = custdm.selectData();
		new JsonCreator().createJson(custList,jsonFilePath+"customer.json");
	}


}
