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
import com.cherp.data.ExpensesDataManager;
import com.cherp.entities.Area;
import com.cherp.entities.Expenses;
import com.google.gson.stream.JsonWriter;

public class ExpensesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String jsonFilePath = "";

	private String id = "";
	private String description = "";
	private String operation = "";

	private String operationResp = "";

	private String rowId = "";
	private String updatedCellDescription = "";

	public ExpensesServlet() {
		super();

	}

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {
		// Add Update Or Delete Parameters
		operation = request.getParameter("operation");

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		// Insert Form Parameters
		description = request.getParameter("description");

		// Update Or Delete Parameters
		rowId = request.getParameter("updatedRow[id]");
		updatedCellDescription = request.getParameter("updatedRow[description]");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("In ExpensesServlet");

		PrintWriter pw = response.getWriter();

		// get all form data into variables
		getParaValues(request, response);

		ExpensesDataManager edm = new ExpensesDataManager();
		Expenses exp = new Expenses();

		if (operation != null) {
			// For insert set ALL Parameters except ID
			if (operation.equals("insert")) {

				System.out.println("Insert Function");

				exp.setDescription(description);

				operationResp = edm.addData(exp);
				pw.println(operationResp);

			} else if (operation.equals("update")) {
				// For update set ALL Parameters
				System.out.println("Update Function");
				exp.setId(Integer.parseInt(rowId));
				exp.setDescription(updatedCellDescription);

				operationResp = edm.updateData(exp);
				pw.println(operationResp);

			} else if (operation.equals("delete")) {
				// For delete set only ID Parameter
				System.out.println("Delete Function");
				exp.setId(Integer.parseInt(rowId));
				operationResp = edm.deleteData(exp);
				pw.println(operationResp);

			}
		}

		// Contains All Data in table
		List<Expenses> expList = new ArrayList<>();
		expList = edm.selectData();
		jsonFileWriter(expList);
	}

	// method for creating json file
	public void jsonFileWriter(List<Expenses> expList) {
		try {
			Writer writer = new FileWriter(jsonFilePath + "expenses.json");
			JsonWriter jw = new JsonWriter(writer);
			jw.beginObject();
			jw.name("data");
			jw.beginArray();
			for (Expenses e : expList) {
				jw.beginObject();
				jw.name("id").value(e.getId());
				jw.name("description").value(e.getDescription());

				jw.endObject();
			}
			jw.endArray();
			jw.endObject();
			jw.close();
		} catch (Exception e) {
		}
	}

}
