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

import com.cherp.data.StateDataManager;
import com.cherp.entities.Area;
import com.cherp.entities.State;
import com.cherp.utils.JsonCreator;
import com.google.gson.stream.JsonWriter;

public class StateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String jsonFilePath = "";

	private String stateName = "";

	private String operation = "";

	private String operationResp = "";

	private String rowId = "";
	private String updatedCellStateName = "";

	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {

		operation = request.getParameter("operation");

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		stateName = request.getParameter("stateName");

		rowId = request.getParameter("updatedRow[id]");
		updatedCellStateName = request.getParameter("updatedRow[stateName]");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("In State Servlet");

		PrintWriter pw = response.getWriter();

		getParaValues(request, response);

		StateDataManager std = new StateDataManager();
		State state = new State();

		if (operation != null) {
			System.out.println("above if condn===");
			if (operation.equals("insert")) {
				System.out.println("above if condn");
				System.out.println("Insert Function");

				state.setStateName(stateName);

				operationResp = std.addData(state);
				pw.println(operationResp);

			} else if (operation.equals("update")) {
				// For update set ALL Parameters
				System.out.println("Update Function");
				state.setId(Integer.parseInt(rowId));
				state.setStateName(updatedCellStateName);

				operationResp = std.updateData(state);
				pw.println(operationResp);

			} else if (operation.equals("delete")) {
				// For delete set only ID Parameter
				System.out.println("Delete Function");
				state.setId(Integer.parseInt(rowId));
				operationResp = std.deleteData(state);
				pw.println(operationResp);

			}
		}

		// Contains All Data in table
		List<State> stateList = new ArrayList<>();
		stateList = std.selectData();
		new JsonCreator().createJson(stateList,jsonFilePath+"state.json");
	}



}
