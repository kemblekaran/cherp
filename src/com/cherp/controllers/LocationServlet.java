package com.cherp.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.data.LocationDataManager;
import com.cherp.entities.Location;
import com.cherp.json.JSONFileWriter;

public class LocationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String location = "";
	private String operation = "";

	private String operationResp = "";

	private String rowId = "";
	private String updatedCellLocation = "";

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {
		// Add Update Or Delete Parameters
		operation = request.getParameter("operation");

		// Insert Form Parameters
		location = request.getParameter("location");

		// Update Or Delete Parameters
		rowId = request.getParameter("updatedRow[id]");
		updatedCellLocation = request.getParameter("updatedRow[location]");

	}

	@SuppressWarnings("static-access")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("In LocationServlet");

		PrintWriter pw = response.getWriter();

		// get all form data into variables
		getParaValues(request, response);

		LocationDataManager ldm = new LocationDataManager();
		Location loc = new Location();

		if (operation != null) {
			// For insert set ALL Parameters except ID
			if (operation.equals("insert")) {

				System.out.println("Insert Function");

				loc.setLocation(location);

				operationResp = ldm.addData(loc);
				pw.println(operationResp);

			} else if (operation.equals("update")) {
				// For update set ALL Parameters
				System.out.println("Update Function");

				loc.setId(Integer.parseInt(rowId));
				loc.setLocation(updatedCellLocation);

				operationResp = ldm.updateData(loc);
				pw.println(operationResp);

			} else if (operation.equals("delete")) {
				// For delete set only ID Parameter
				System.out.println("Delete Function");
				loc.setId(Integer.parseInt(rowId));
				operationResp = ldm.deleteData(loc);
				pw.println(operationResp);

			}
		}

		// Contains All Data in table
		List<Location> locList = new ArrayList<>();
		locList = ldm.selectData();
		new JSONFileWriter().jsonCreator(locList, Location.class);
	}

}
