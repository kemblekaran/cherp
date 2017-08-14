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

import com.cherp.data.AreaDataManager;
import com.cherp.data.CompanyDataManager;
import com.cherp.entities.Area;
import com.cherp.entities.Company;
import com.google.gson.stream.JsonWriter;

public class CompanyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String jsonFilePath = "";

	private String name = "";
	private String preAdd = "";
	private String secAdd = "";
	private String mobile = "";
	private String phone = "";
	private String state = "";
	private String city = "";
	private String pinCode = "";
	private String ownName = "";
	private String panNo = "";
	private String opBal = "";
	private String operation = "";

	private String operationResp = "";

	private String rowId = "";
	private String updatedCellName = "";
	private String updatedCellPreAdd = "";
	private String updatedCellSecAdd = "";
	private String updatedCellMobile = "";
	private String updatedCellPhone = "";
	private String updatedCellState = "";
	private String updatedCellCity = "";
	private String updatedCellPinCode = "";
	private String updatedCellOwnName = "";
	private String updatedCellPanNo = "";
	private String updatedCellOpBal = "";

	public CompanyServlet() {
		super();

	}

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {
		// Add Update Or Delete Parameters
		operation = request.getParameter("operation");

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		// Insert Form Parameters
		name = request.getParameter("name");
		preAdd = request.getParameter("preAdd");
		secAdd = request.getParameter("secAdd");
		mobile = request.getParameter("mobile");
		phone = request.getParameter("phone");
		state = request.getParameter("state");
		city = request.getParameter("city");
		pinCode = request.getParameter("pinCode");
		ownName = request.getParameter("ownName");
		panNo = request.getParameter("panNo");
		opBal = request.getParameter("opBal");

		// Update Or Delete Parameters
		rowId = request.getParameter("updatedRow[id]");
		updatedCellName = request.getParameter("updatedRow[name]");
		updatedCellPreAdd = request.getParameter("updatedRow[preAdd]");
		updatedCellSecAdd = request.getParameter("updatedRow[secAdd]");
		updatedCellMobile = request.getParameter("updatedRow[mobile]");
		updatedCellPhone = request.getParameter("updatedRow[phone]");
		updatedCellState = request.getParameter("updatedRow[state]");
		updatedCellCity = request.getParameter("updatedRow[city]");
		updatedCellPinCode = request.getParameter("updatedRow[pinCode]");
		updatedCellOwnName = request.getParameter("updatedRow[ownName]");
		updatedCellPanNo = request.getParameter("updatedRow[panNo]");
		updatedCellOpBal = request.getParameter("updatedRow[opBal]");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("In Company Servlet");

		PrintWriter pw = response.getWriter();

		// get all form data into variables
		getParaValues(request, response);

		CompanyDataManager cdm = new CompanyDataManager();
		Company comp = new Company();

		if (operation != null) {
			// For insert set ALL Parameters except ID
			if (operation.equals("insert")) {

				System.out.println("Insert Function");
				comp.setName(name);
				comp.setPreAdd(preAdd);
				comp.setSecAdd(secAdd);
				comp.setMobile(Integer.parseInt(mobile));
				comp.setPhone(Integer.parseInt(phone));
				comp.setState(state);
				comp.setCity(city);
				comp.setPinCode(Integer.parseInt(pinCode));
				comp.setOwnName(ownName);
				comp.setPanNo(Integer.parseInt(panNo));
				comp.setOpBal(Integer.parseInt(opBal));

				operationResp = cdm.addData(comp);
				pw.println(operationResp);

			} else if (operation.equals("update")) {
				// For update set ALL Parameters
				System.out.println("Update Function");
				comp.setId(Integer.parseInt(rowId));
				comp.setName(updatedCellName);
				comp.setPreAdd(updatedCellPreAdd);
				comp.setSecAdd(updatedCellSecAdd);
				comp.setMobile(Integer.parseInt(updatedCellMobile));
				comp.setPhone(Integer.parseInt(updatedCellPhone));
				comp.setState(updatedCellState);
				comp.setCity(updatedCellCity);
				comp.setPinCode(Integer.parseInt(updatedCellPinCode));
				comp.setOwnName(updatedCellOwnName);
				comp.setPanNo(Integer.parseInt(updatedCellPanNo));
				comp.setOpBal(Integer.parseInt(updatedCellOpBal));

				operationResp = cdm.updateData(comp);
				pw.println(operationResp);

			} else if (operation.equals("delete")) {
				// For delete set only ID Parameter
				System.out.println("Delete Function");
				comp.setId(Integer.parseInt(rowId));
				operationResp = cdm.deleteData(comp);
				pw.println(operationResp);

			}
		}

		// Contains All Data in table
		List<Company> compList = new ArrayList<>();
		compList = cdm.selectData();
		jsonFileWriter(compList);
	}

	// method for creating json file
	public void jsonFileWriter(List<Company> compList) {
		try (Writer writer = new FileWriter(jsonFilePath + "company.json")) {
			JsonWriter jw = new JsonWriter(writer);
			jw.beginObject();
			jw.name("data");
			jw.beginArray();
			for (Company c : compList) {
				jw.beginObject();

				jw.name("id").value(c.getId());
				jw.name("name").value(c.getName());
				jw.name("preAdd").value(c.getPreAdd());
				jw.name("secAdd").value(c.getSecAdd());
				jw.name("mobile").value(c.getMobile());
				jw.name("phone").value(c.getPhone());
				jw.name("state").value(c.getState());
				jw.name("city").value(c.getCity());
				jw.name("pinCode").value(c.getPinCode());
				jw.name("ownName").value(c.getOwnName());
				jw.name("panNo").value(c.getPanNo());
				jw.name("opBal").value(c.getOpBal());

				jw.endObject();
			}
			jw.endArray();
			jw.endObject();
			jw.close();
		} catch (Exception e) {
		}
	}

}
