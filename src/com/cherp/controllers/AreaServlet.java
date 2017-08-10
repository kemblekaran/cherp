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
import com.cherp.data.UserDataManager;
import com.cherp.entities.Area;
import com.cherp.entities.User;
import com.google.gson.stream.JsonWriter;

public class AreaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String jsonFilePath;

	private String state = "";
	private String city = "";
	private String name = "";
	private String code;
	private String type = "";
	private String operation = "";

	private String operationResp = "";

	private String rowId = "";
	private String updatedCellState = "";
	private String updatedCellCity = "";
	private String updatedCellName = "";
	private String updatedCellCode = "";
	private String updatedCellType = "";

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		// Add Update Or Delete Parameters
		operation = request.getParameter("operation");

		// Insert Form Parameters
		state = request.getParameter("state");
		city = request.getParameter("city");
		name = request.getParameter("name");
		type = request.getParameter("type");
		code = request.getParameter("code");

		// Update Or Delete Parameters
		rowId = request.getParameter("updatedRow[id]");
		updatedCellState = request.getParameter("updatedRow[state]");
		updatedCellCity = request.getParameter("updatedRow[city]");
		updatedCellName = request.getParameter("updatedRow[name]");
		updatedCellCode = request.getParameter("updatedRow[code]");
		updatedCellType = request.getParameter("updatedRow[type]");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("In AreaServlet");

		PrintWriter pw = response.getWriter();

		// get all form data into variables
		getParaValues(request, response);

		AreaDataManager adm = new AreaDataManager();
		Area area = new Area();

		if (operation != null) {
			// For insert set ALL Parameters except ID
			if (operation.equals("insert")) {

				System.out.println("Insert Function");

				area.setState(state);
				area.setCity(city);
				area.setName(name);
				area.setCode(Integer.parseInt(code));
				area.setType(type);

				operationResp = adm.addData(area);
				pw.println(operationResp);

			} else if (operation.equals("update")) {
				// For update set ALL Parameters
				System.out.println("Update Function");
				area.setId(Integer.parseInt(rowId));
				area.setState(updatedCellState);
				area.setCity(updatedCellCity);
				area.setName(updatedCellName);
				area.setType(updatedCellType);
				area.setCode(Integer.parseInt(updatedCellCode));

				operationResp = adm.updateData(area);
				pw.println(operationResp);

			} else if (operation.equals("delete")) {
				// For delete set only ID Parameter
				System.out.println("Delete Function");
				area.setId(Integer.parseInt(rowId));
				operationResp = adm.deleteData(area);
				pw.println(operationResp);

			}
		}

		// Contains All Data in table
		List<Area> areaList = new ArrayList<>();
		areaList = adm.selectData();
		jsonFileWriter(areaList);
	}

	// method for creating json file
	public void jsonFileWriter(List<Area> areaList) {
		try {
			Writer writer = new FileWriter(jsonFilePath + "area.json");
			JsonWriter jw = new JsonWriter(writer);
			jw.beginObject();
			jw.name("data");
			jw.beginArray();
			for (Area a : areaList) {
				jw.beginObject();
				jw.name("id").value(a.getId());
				jw.name("state").value(a.getState());
				jw.name("city").value(a.getCity());
				jw.name("name").value(a.getName());
				jw.name("code").value(a.getCode());
				jw.name("type").value(a.getType());
				jw.endObject();
			}
			jw.endArray();
			jw.endObject();
			jw.close();
		} catch (Exception e) {
		}
	}

}
