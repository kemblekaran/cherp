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
	private String curAdd = "";
	private String perAdd = "";
	private String state = "";
	private String city = "";
	private String mobile = "";
	private String phone = "";
	private String drLisence = "";
	private String panNo = "";
	private String adhaarNo = "";
	private String photo = "";
	private String operation = "";

	private String operationResp = "";

	private String rowId = "";
	private String updatedCellFname = "";
	private String updatedCellLname = "";
	private String updatedCurAdd = "";
	private String updatedPerAdd = "";
	private String updatedCellState = "";
	private String updatedCellCity = "";
	private String updatedCellMobile = "";
	private String updatedCellPhone = "";
	private String updatedCellDrLisence = "";
	private String updatedCellPanNo = "";
	private String updatedCellAdhaarNo = "";
	private String updatedCellPhoto = "";

	public CleanersServlet() {
		super();

	}

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {
		// Add Update Or Delete Parameters
		operation = request.getParameter("operation");
		System.out.println("Name:"+fname);

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		// Insert Form Parameters
		fname = request.getParameter("fname");
		lname = request.getParameter("lname");
		curAdd = request.getParameter("curAdd");
		perAdd = request.getParameter("perAdd");
		state = request.getParameter("state");
		city = request.getParameter("city");
		mobile = request.getParameter("mobile");
		phone = request.getParameter("phone");
		drLisence = request.getParameter("drLisence");
		panNo = request.getParameter("panNo");
		adhaarNo = request.getParameter("adhaarNo");
		photo = request.getParameter("photo");

		// Update Or Delete Parameters
		rowId = request.getParameter("updatedRow[id]");
		updatedCellFname = request.getParameter("updatedRow[fname]");
		updatedCellLname = request.getParameter("updatedRow[lname]");
		updatedCurAdd = request.getParameter("updatedRow[curAdd]");
		updatedPerAdd = request.getParameter("updatedRow[perAdd]");
		updatedCellState = request.getParameter("updatedRow[state]");
		updatedCellCity = request.getParameter("updatedRow[city]");
		updatedCellMobile = request.getParameter("updatedRow[mobile]");
		updatedCellPhone = request.getParameter("updatedRow[phone]");
		updatedCellDrLisence = request.getParameter("updatedRow[drLisence]");
		updatedCellPanNo = request.getParameter("updatedRow[panNo]");
		updatedCellAdhaarNo = request.getParameter("updatedRow[adhaarNo]");
		updatedCellPhoto = request.getParameter("updatedRow[photo]");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("In Cleansers Servlet");

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
				cls.setCurAdd(curAdd);
				cls.setPerAdd(perAdd);
				cls.setState(state);
				cls.setCity(city);
				cls.setMobile(Integer.parseInt(mobile));
				cls.setPhone(Integer.parseInt(phone));
				cls.setDrLisence(Integer.parseInt(drLisence));
				cls.setPanNo(Integer.parseInt(panNo));
				cls.setAdhaarNo(Integer.parseInt(adhaarNo));
				cls.setPhoto(photo);

				operationResp = cdm.addData(cls);
				pw.println(operationResp);

			} else if (operation.equals("update")) {
				// For update set ALL Parameters
				System.out.println("Update Function");
				cls.setId(Integer.parseInt(rowId));
				cls.setFname(updatedCellFname);
				cls.setLname(updatedCellLname);
				cls.setCurAdd(updatedCurAdd);
				cls.setPerAdd(updatedPerAdd);
				cls.setState(updatedCellState);
				cls.setCity(updatedCellCity);
				cls.setMobile(Integer.parseInt(updatedCellMobile));
				cls.setPhone(Integer.parseInt(updatedCellPhone));
				cls.setDrLisence(Integer.parseInt(updatedCellDrLisence));
				cls.setPanNo(Integer.parseInt(updatedCellPanNo));
				cls.setAdhaarNo(Integer.parseInt(updatedCellAdhaarNo));
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
			jw.beginObject();//{
			jw.name("data");
			jw.beginArray();//[
			for (Cleaners c : clsList) {
				jw.beginObject();//{
				
				jw.name("id").value(c.getId());
				jw.name("fname").value(c.getFname());
				jw.name("lname").value(c.getLname());
				jw.name("curAdd").value(c.getCurAdd());
				jw.name("perAdd").value(c.getPerAdd());
				jw.name("state").value(c.getState());
				jw.name("city").value(c.getCity());
				jw.name("mobile").value(c.getMobile());
				jw.name("phone").value(c.getPhone());
				jw.name("drLisence").value(c.getDrLisence());
				jw.name("panNo").value(c.getPanNo());
				jw.name("adhaarNo").value(c.getAdhaarNo());
				jw.name("photo").value(c.getPhoto());

				jw.endObject();//}
			}
			jw.endArray();//]
			jw.endObject();//}
			jw.close();
		} catch (Exception e) {
		}
	}
}
