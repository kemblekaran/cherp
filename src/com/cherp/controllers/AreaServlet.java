package com.cherp.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.dao.masters.AreaDao;
import com.cherp.data.AreaDataManager;
import com.cherp.entities.Area;
import com.cherp.utils.JsonCreator;

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
		AreaDao adao = new AreaDao();
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

				operationResp = adao.insert(area);
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

				operationResp = adao.update(area);
				pw.println(operationResp);

			} else if (operation.equals("delete")) {
				// For delete set only ID Parameter
				System.out.println("Delete Function");
				area.setId(Integer.parseInt(rowId));
				operationResp = adao.delete(area);
				pw.println(operationResp);

			}
		}

		// Contains All Data in table
		List<Area> areaList = new ArrayList<>();
		areaList = adao.selectAll();
		new JsonCreator().createJson(areaList,jsonFilePath+"area.json");
	}

}
