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

import com.cherp.dao.masters.DriversDao;
import com.cherp.data.CustomerDataManager;
import com.cherp.data.DriverDataManager;
import com.cherp.entities.Customer;
import com.cherp.entities.Drivers;
import com.cherp.utils.JsonCreator;
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
	private String drLicense;
	private String photo = "";
	private String operation = "";

	private String operationResp = "";

	private String rowId = "";
	private String updatedCellFname = "";
	private String updatedCellLname = "";
	private String updatedCellDrLicense = "";
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
		drLicense = request.getParameter("drLicense");

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
		updatedCellDrLicense = request.getParameter("updatedRow[drLicense]");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("In DriverServlet");

		PrintWriter pw = response.getWriter();

		// get all form data into variables
		getParaValues(request, response);

		DriverDataManager ddm = new DriverDataManager();
		DriversDao driversDao = new DriversDao();
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
				driver.setPanNo(panNo);
				driver.setAdhaarNo(adhaarNo);
				driver.setPhoto(photo);
				driver.setMobile(Long.parseLong(mobile));
				driver.setPhone(Long.parseLong(phone));
				driver.setDrLicense(drLicense);

				operationResp = driversDao.insert(driver);
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
				driver.setPanNo(updatedCellPanNo);
				driver.setAdhaarNo(updatedCellAdhaarNo);
				driver.setMobile(Long.parseLong(updatedCellMobile));
				driver.setPhone(Long.parseLong(updatedCellPhone));
				driver.setDrLicense(updatedCellDrLicense);
				driver.setPhoto(updatedCellPhoto);
				driver.setStatus(1);
				operationResp = driversDao.update(driver);
				pw.println(operationResp);

			} else if (operation.equals("delete")) {
				// For delete set only ID Parameter
				System.out.println("Delete Function");
				driver.setId(Integer.parseInt(rowId));
				operationResp = driversDao.delete(driver);
				pw.println(operationResp);

			}
		}

		// Contains All Data in table
		List<Drivers> driverList = new ArrayList<>();
		driverList = driversDao.selectAll();
		new JsonCreator().createJson(driverList,jsonFilePath+"driver.json");
	}



}
