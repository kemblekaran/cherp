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
import com.cherp.data.DriverDataManager;
import com.cherp.entities.Customer;
import com.cherp.entities.Drivers;
import com.google.gson.stream.JsonWriter;

public class DriverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String jsonFilePath = "";
	private String fname = "";
	private String lname = "";
	private String curAdd = "";
	private String perAdd = "";
	private String state = "";
	private String city = "";
	private String mobile;
	private String phone;
	private String panNo;
	private String adhaarNo;
	private String drLiscense;
	private String photo = "";
	private String operation = "";

	private String operationResp = "";

	private String rowId = "";
	private String updatedCellFname = "";
	private String updatedCellLname = "";
	private String updatedCellDrLiscense = "";
	private String updatedCellCurAdd = "";
	private String updatedCellPerAdd = "";
	private String updatedCellState = "";
	private String updatedCellCity = "";
	private String updatedCellPanNo = "";
	private String updatedCellMobile = "";
	private String updatedCellPhone = "";
	private String updatedCellPhoto = "";
	private String updatedCellAdhaarNo = "";

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {
		// Add Update Or Delete Parameters
		operation = request.getParameter("operation");

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		// Insert Form Parameters
		fname = request.getParameter("fname");
		lname = request.getParameter("lname");
		photo = request.getParameter("photo");
		curAdd = request.getParameter("curAdd");
		perAdd = request.getParameter("perAdd");
		state = request.getParameter("state");
		city = request.getParameter("city");
		panNo = request.getParameter("panNo");
		mobile = request.getParameter("mobile");
		phone = request.getParameter("phone");
		adhaarNo = request.getParameter("adhaarNo");
		drLiscense = request.getParameter("drLiscense");

		// Update Or Delete Parameters
		rowId = request.getParameter("updatedRow[id]");
		updatedCellFname = request.getParameter("updatedRow[fname]");
		updatedCellLname = request.getParameter("updatedRow[lname]");
		updatedCellPhoto = request.getParameter("updatedRow[photo]");
		updatedCellCurAdd = request.getParameter("updatedRow[curAdd]");
		updatedCellPerAdd = request.getParameter("updatedRow[perAdd]");
		updatedCellState = request.getParameter("updatedRow[state]");
		updatedCellCity = request.getParameter("updatedRow[city]");
		updatedCellPanNo = request.getParameter("updatedRow[panNo]");
		updatedCellMobile = request.getParameter("updatedRow[mobile]");
		updatedCellPhone = request.getParameter("updatedRow[phone]");
		updatedCellAdhaarNo = request.getParameter("updatedRow[adhaarNo]");
		updatedCellDrLiscense = request.getParameter("updatedRow[drLiscense]");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("In DriverServlet");

		PrintWriter pw = response.getWriter();

		// get all form data into variables
		getParaValues(request, response);

		DriverDataManager ddm = new DriverDataManager();
		Drivers driver = new Drivers();

		if (operation != null) {
			// For insert set ALL Parameters except ID
			if (operation.equals("insert")) {

				System.out.println("Insert Function");
				driver.setFname(fname);
				driver.setLname(lname);
				driver.setCurAdd(curAdd);
				driver.setPerAdd(perAdd);
				driver.setState(state);
				driver.setCity(city);
				driver.setMobile(Integer.parseInt(mobile));
				driver.setPhone(Integer.parseInt(phone));
				driver.setDrLiscense(Integer.parseInt(drLiscense));
				driver.setPanNo(Integer.parseInt(panNo));
				driver.setAdhaarNo(Integer.parseInt(adhaarNo));
				driver.setPhoto(photo);

				operationResp = ddm.addData(driver);
				pw.println(operationResp);

			} else if (operation.equals("update")) {
				// For update set ALL Parameters
				System.out.println("Update Function");
				driver.setId(Integer.parseInt(rowId));
				driver.setFname(updatedCellFname);
				driver.setLname(updatedCellLname);
				driver.setCurAdd(updatedCellCurAdd);
				driver.setPerAdd(updatedCellPerAdd);
				driver.setState(updatedCellState);
				driver.setCity(updatedCellCity);
				driver.setPanNo(Integer.parseInt(updatedCellPanNo));
				driver.setAdhaarNo(Integer.parseInt(updatedCellAdhaarNo));
				driver.setMobile(Integer.parseInt(updatedCellMobile));
				driver.setPhone(Integer.parseInt(updatedCellPhone));
				driver.setDrLiscense(Integer.parseInt(updatedCellDrLiscense));
				driver.setPhoto(updatedCellPhoto);

				operationResp = ddm.updateData(driver);
				pw.println(operationResp);

			} else if (operation.equals("delete")) {
				// For delete set only ID Parameter
				System.out.println("Delete Function");
				driver.setId(Integer.parseInt(rowId));
				operationResp = ddm.deleteData(driver);
				pw.println(operationResp);

			}
		}

		// Contains All Data in table
		List<Drivers> driverList = new ArrayList<>();
		driverList = ddm.selectData();
		jsonFileWriter(driverList);
	}

	// method for creating json file
	public void jsonFileWriter(List<Drivers> driverList) {
		try {
			Writer writer = new FileWriter(jsonFilePath + "driver.json");

			JsonWriter jw = new JsonWriter(writer);
			jw.beginObject();
			jw.name("data");
			jw.beginArray();
			for (Drivers d : driverList) {
				jw.beginObject();
				jw.name("id").value(d.getId());
				jw.name("fname").value(d.getFname());
				jw.name("lname").value(d.getLname());
				jw.name("adhaarNo").value(d.getAdhaarNo());
				jw.name("curAdd").value(d.getCurAdd());
				jw.name("perAdd").value(d.getPerAdd());
				jw.name("state").value(d.getState());
				jw.name("city").value(d.getCity());
				jw.name("panNo").value(d.getPanNo());
				jw.name("mobile").value(d.getMobile());
				jw.name("phone").value(d.getPhone());
				jw.name("drLiscense").value(d.getDrLiscense());
				jw.name("photo").value(d.getPhoto());
				jw.endObject();
			}
			jw.endArray();
			jw.endObject();
			jw.close();

		} catch (Exception e) {
		}
	}

}
