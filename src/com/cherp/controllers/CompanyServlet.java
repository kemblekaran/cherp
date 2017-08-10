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
	private String pre_add = "";
	private String sec_add = "";
	private String mobile = "";
	private String phone = "";
	private String state = "";
	private String city = "";
	private String pin_code = "";
	private String own_name = "";
	private String pan_no = "";
	private String op_bal = "";
	private String operation = "";

	private String operationResp = "";

	private String rowId = "";
	private String updatedCellId = "";
	private String updatedCellName = "";
	private String updatedCellPre_add = "";
	private String updatedCellSec_add = "";
	private String updatedCellMobile = "";
	private String updatedCellPhone = "";
	private String updatedCellState = "";
	private String updatedCellCity = "";
	private String updatedCellPin_code = "";
	private String updatedCellOwn_name = "";
	private String updatedCellPan_no = "";
	private String updatedCellOp_bal = "";

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
		pre_add = request.getParameter("pre_add");
		sec_add = request.getParameter("sec_add");
		mobile = request.getParameter("mobile");
		phone = request.getParameter("phone");
		state = request.getParameter("state");
		city = request.getParameter("city");
		pin_code = request.getParameter("pin_code");
		own_name = request.getParameter("own_name");
		pan_no = request.getParameter("pan_no");
		op_bal = request.getParameter("op_bal");

		// Update Or Delete Parameters
		rowId = request.getParameter("updatedRow[id]");
		updatedCellName = request.getParameter("updatedRow[name]");
		updatedCellPre_add = request.getParameter("updatedRow[pre_add]");
		updatedCellSec_add = request.getParameter("updatedRow[sec_add]");
		updatedCellMobile = request.getParameter("updatedRow[mobile]");
		updatedCellPhone = request.getParameter("updatedRow[phone]");
		updatedCellState = request.getParameter("updatedRow[state]");
		updatedCellCity = request.getParameter("updatedRow[city]");
		updatedCellPin_code = request.getParameter("updatedRow[pin_code]");
		updatedCellOwn_name = request.getParameter("updatedRow[own_name]");
		updatedCellPan_no = request.getParameter("updatedRow[pan_no]");
		updatedCellOp_bal = request.getParameter("updatedRow[op_bal]");

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
				comp.setPre_add(pre_add);
				comp.setSec_add(sec_add);
				comp.setMobile(Integer.parseInt(mobile));
				comp.setPhone(Integer.parseInt(phone));
				comp.setState(state);
				comp.setCity(city);
				comp.setPin_code(Integer.parseInt(pin_code));
				comp.setOwn_name(own_name);
				comp.setPan_no(Integer.parseInt(pan_no));
				comp.setOp_bal(Integer.parseInt(op_bal));

				operationResp = cdm.addData(comp);
				pw.println(operationResp);

			} else if (operation.equals("update")) {
				// For update set ALL Parameters
				System.out.println("Update Function");
				comp.setId(Integer.parseInt(rowId));
				comp.setName(updatedCellName);
				comp.setPre_add(updatedCellPre_add);
				comp.setSec_add(updatedCellSec_add);
				comp.setMobile(Integer.parseInt(updatedCellMobile));
				comp.setPhone(Integer.parseInt(updatedCellPhone));
				comp.setState(updatedCellState);
				comp.setCity(updatedCellCity);
				comp.setPin_code(Integer.parseInt(updatedCellPin_code));
				comp.setOwn_name(updatedCellOwn_name);
				comp.setPan_no(Integer.parseInt(updatedCellPan_no));
				comp.setOp_bal(Integer.parseInt(updatedCellOp_bal));

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
				jw.name("pre_add").value(c.getPre_add());
				jw.name("sec_add").value(c.getSec_add());
				jw.name("mobile").value(c.getMobile());
				jw.name("phone").value(c.getPhone());
				jw.name("state").value(c.getState());
				jw.name("city").value(c.getCity());
				jw.name("pin_code").value(c.getPin_code());
				jw.name("own_name").value(c.getOwn_name());
				jw.name("pan_no").value(c.getPan_no());
				jw.name("op_bal").value(c.getOp_bal());

				jw.endObject();
			}
			jw.endArray();
			jw.endObject();
			jw.close();
		} catch (Exception e) {
		}
	}

}
