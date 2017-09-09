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

import com.cherp.data.CityDataManager;
import com.cherp.data.UserDataManager;
import com.cherp.entities.City;
import com.cherp.entities.User;
import com.cherp.utils.JsonCreator;
import com.google.gson.stream.JsonWriter;

public class CityServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String jsonFilePath = "";
	
	private String stateName = "";
	private String cityName = "";
	private String operation = "";

	private String operationResp = "";

	private String rowId = "";
	private String updatedCellState = "";
	private String updatedCellCity = "";

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {
		// Add Update Or Delete Parameters
		operation = request.getParameter("operation");

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		// Insert Form Parameters
		stateName = request.getParameter("stateName");
		cityName = request.getParameter("cityName");

		// Update Or Delete Parameters
		rowId = request.getParameter("updatedRow[id]");
		updatedCellState = request.getParameter("updatedRow[stateName]");
		updatedCellCity = request.getParameter("updatedRow[cityName]");

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("In CityServlet");

		PrintWriter pw = response.getWriter();

		// get all form data into variables
		getParaValues(request, response);

		CityDataManager cdm = new CityDataManager();
		City city = new City();

		if (operation != null) {
			// For insert set ALL Parameters except ID
			if (operation.equals("insert")) {

				System.out.println("Insert Function");
				city.setStateName(stateName);
				city.setCityName(cityName);
				
				operationResp = cdm.addData(city);
				pw.println(operationResp);

			} else if (operation.equals("update")) {
				// For update set ALL Parameters
				System.out.println("Update Function");
				city.setId(Integer.parseInt(rowId));
				city.setCityName(updatedCellCity);
				city.setStateName(updatedCellState);
				operationResp = cdm.updateData(city);
				pw.println(operationResp);

			} else if (operation.equals("delete")) {
				// For delete set only ID Parameter
				System.out.println("Delete Function");
				city.setId(Integer.parseInt(rowId));
				operationResp = cdm.deleteData(city);
				pw.println(operationResp);

			}
		}

		// Contains All Data in table
		List<City> cityList = new ArrayList<>();
		cityList = cdm.selectData();
		new JsonCreator().createJson(cityList,jsonFilePath+"city.json");
	}


}
