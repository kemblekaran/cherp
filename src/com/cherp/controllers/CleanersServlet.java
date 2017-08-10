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

import com.cherp.data.CleanersDataManager;
import com.cherp.data.ExpensesDataManager;
import com.cherp.entities.Cleaners;
import com.cherp.entities.Expenses;
import com.google.gson.stream.JsonWriter;

public class CleanersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String jsonFilePath = "";
	private String id = "";
	private String fname = "";
	private String lname = "";
	private String cur_add = "";
	private String per_add = "";
	private String state = "";
	private String city = "";
	private String mobile = "";
	private String phone = "";
	private String dr_lisence = "";
	private String pan_no = "";
	private String adhaar_no = "";
	private String photo = "";
	private String operation = "";

	private String operationResp = "";

	private String rowId = "";
	private String updatedCellFname = "";
	private String updatedCellLname = "";
	private String updatedCur_add = "";
	private String updatedPer_add = "";
	private String updatedCellState = "";
	private String updatedCellCity = "";
	private String updatedCellMobile = "";
	private String updatedCellPhone = "";
	private String updatedCellDr_lisence = "";
	private String updatedCellPan_no = "";
	private String updatedCellAdhaar_no = "";
	private String updatedCellPhoto = "";

	public CleanersServlet() {
		super();

	}

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {
		// Add Update Or Delete Parameters
		operation = request.getParameter("operation");

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		// Insert Form Parameters
		fname = request.getParameter("fname");
		lname = request.getParameter("lname");
		cur_add = request.getParameter("cur_add");
		per_add = request.getParameter("per_add");
		state = request.getParameter("state");
		city = request.getParameter("city");
		mobile = request.getParameter("mobile");
		phone = request.getParameter("phone");
		dr_lisence = request.getParameter("dr_lisence");
		pan_no = request.getParameter("pan_no");
		adhaar_no = request.getParameter("adhaar_no");
		photo = request.getParameter("photo");

		// Update Or Delete Parameters
		rowId = request.getParameter("updatedRow[id]");
		updatedCellFname = request.getParameter("updatedRow[fname]");
		updatedCellLname = request.getParameter("updatedRow[lname]");
		updatedCur_add = request.getParameter("updatedRow[cur_add]");
		updatedPer_add = request.getParameter("updatedRow[per_add]");
		updatedCellState = request.getParameter("updatedRow[state]");
		updatedCellCity = request.getParameter("updatedRow[city]");
		updatedCellMobile = request.getParameter("updatedRow[mobile]");
		updatedCellPhone = request.getParameter("updatedRow[phone]");
		updatedCellDr_lisence = request.getParameter("updatedRow[dr_lisence]");
		updatedCellPan_no = request.getParameter("updatedRow[pan_no]");
		updatedCellAdhaar_no = request.getParameter("updatedRow[adhaar_no]");
		updatedCellPhoto = request.getParameter("updatedRow[photo]");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("In ExpensesServlet");

		PrintWriter pw = response.getWriter();

		// get all form data into variables
		getParaValues(request, response);

		CleanersDataManager cdm = new CleanersDataManager();
		Cleaners cls = new Cleaners();

		if (operation != null) {
			// For insert set ALL Parameters except ID
			if (operation.equals("insert")) {

				System.out.println("Insert Function");

				cls.setFname(fname);
				cls.setLname(lname);
				cls.setCur_add(cur_add);
				cls.setPer_add(per_add);
				cls.setState(state);
				cls.setCity(city);
				cls.setMobile(Integer.parseInt(mobile));
				cls.setPhone(Integer.parseInt(phone));
				cls.setDr_lisence(Integer.parseInt(dr_lisence));
				cls.setPan_no(Integer.parseInt(pan_no));
				cls.setAdhaar_no(Integer.parseInt(adhaar_no));
				cls.setPhoto(photo);

				operationResp = cdm.addData(cls);
				pw.println(operationResp);

			} else if (operation.equals("update")) {
				// For update set ALL Parameters
				System.out.println("Update Function");
				cls.setId(Integer.parseInt(rowId));
				cls.setFname(updatedCellFname);
				cls.setLname(updatedCellLname);
				cls.setCur_add(updatedCur_add);
				cls.setPer_add(updatedPer_add);
				cls.setState(updatedCellState);
				cls.setCity(updatedCellCity);
				cls.setMobile(Integer.parseInt(updatedCellMobile));
				cls.setPhone(Integer.parseInt(updatedCellPhone));
				cls.setDr_lisence(Integer.parseInt(updatedCellDr_lisence));
				cls.setPan_no(Integer.parseInt(updatedCellPan_no));
				cls.setAdhaar_no(Integer.parseInt(updatedCellAdhaar_no));
				cls.setPhoto(updatedCellPhoto);

				operationResp = cdm.updateData(cls);
				pw.println(operationResp);

			} else if (operation.equals("delete")) {
				// For delete set only ID Parameter
				System.out.println("Delete Function");
				cls.setId(Integer.parseInt(rowId));
				operationResp = cdm.deleteData(cls);
				pw.println(operationResp);

			}
		}

		// Contains All Data in table
		List<Cleaners> clsList = new ArrayList<>();
		clsList = cdm.selectData();
		jsonFileWriter(clsList);
	}

	// method for creating json file
	public void jsonFileWriter(List<Cleaners> clsList) {
		try {

			Writer writer = new FileWriter(jsonFilePath+"cleaners.json");
			JsonWriter jw = new JsonWriter(writer);
			jw.beginObject();
			jw.name("data");
			jw.beginArray();
			for (Cleaners c : clsList) {
				jw.beginObject();
				jw.name("id").value(c.getId());
				jw.name("fname").value(c.getFname());
				jw.name("lname").value(c.getLname());
				jw.name("cur_add").value(c.getCur_add());
				jw.name("per_add").value(c.getPer_add());
				jw.name("state").value(c.getState());
				jw.name("city").value(c.getCity());
				jw.name("mobile").value(c.getMobile());
				jw.name("phone").value(c.getPhone());
				jw.name("dr_lisence").value(c.getDr_lisence());
				jw.name("pan_no").value(c.getPan_no());
				jw.name("adhaar_no").value(c.getAdhaar_no());
				jw.name("photo").value(c.getPhoto());

				jw.endObject();
			}
			jw.endArray();
			jw.endObject();
			jw.close();
		} catch (Exception e) {
		}
	}
}
