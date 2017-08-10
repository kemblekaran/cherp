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

import com.cherp.data.CustomerDataManager;
import com.cherp.data.UserDataManager;
import com.cherp.entities.Customer;
import com.cherp.entities.User;
import com.google.gson.stream.JsonWriter;

public class CustomerServlet extends HttpServlet {

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
				cust.setMobile(Integer.parseInt(mobile));
				cust.setPhone(Integer.parseInt(phone));
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
				cust.setMobile(Integer.parseInt(updatedCellMobile));
				cust.setPhone(Integer.parseInt(updatedCellPhone));
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
		jsonFileWriter(custList);
	}

	// method for creating json file
	public void jsonFileWriter(List<Customer> custList) {
		try {
			Writer writer = new FileWriter(jsonFilePath + "customer.json");

			JsonWriter jw = new JsonWriter(writer);
			jw.beginObject();
			jw.name("data");
			jw.beginArray();
			for (Customer cust : custList) {
				jw.beginObject();
				jw.name("id").value(cust.getId());
				jw.name("fname").value(cust.getFname());
				jw.name("lname").value(cust.getLname());
				jw.name("shopName").value(cust.getShopName());
				jw.name("curAdd").value(cust.getCurAdd());
				jw.name("perAdd").value(cust.getPerAdd());
				jw.name("state").value(cust.getState());
				jw.name("city").value(cust.getCity());
				jw.name("area").value(cust.getArea());
				jw.name("mobile").value(cust.getMobile());
				jw.name("phone").value(cust.getPhone());
				jw.name("opBal").value(cust.getOpBal());
				jw.endObject();
			}
			jw.endArray();
			jw.endObject();
			jw.close();

		} catch (Exception e) {
		}
	}

}
