package com.cherp.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.dao.masters.VanDao;
import com.cherp.entities.Van;
import com.cherp.utils.JsonCreator;

public class VanServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String jsonFilePath;

	private String vanNumber = "";
	private String companyName = "";
	private String vanModel = "";
	private String ownerName = "";
	private String fitness = "";
	private String vanCapacity = "";
	private String insuranceNo = "";
	private String insStartDate = "";
	private String insEndDate = "";
	private String permitNo = "";
	private String permitStartDate = "";
	private String permitEndDate = "";

	private String operation = "";

	private String operationResp = "";

	private String rowId = "";

	private String updatedCellVanNumber = "";
	private String updatedCellCompanyName = "";
	private String updatedCellVanModel = "";
	private String updatedCellOwnerName = "";
	private String updatedCellFitness = "";
	private String updatedCellVanCapacity = "";
	private String updatedCellInsuranceNo = "";
	private String updatedCellInsStartDate = "";
	private String updatedCellInsEndDate = "";
	private String updatedCellPermitNo = "";
	private String updatedCellPermitStartDate = "";
	private String updatedCellPermitEndDate = "";

	public VanServlet() {
		super();

	}

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {
		// Add Update Or Delete Parameters
		operation = request.getParameter("operation");

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		// Insert Form Parameters

		vanNumber = request.getParameter("vanNumber");
		companyName = request.getParameter("companyName");
		vanModel = request.getParameter("vanModel");
		ownerName = request.getParameter("ownerName");
		fitness = request.getParameter("fitness");
		vanCapacity = request.getParameter("vanCapacity");
		insuranceNo = request.getParameter("insuranceNo");
		insStartDate = request.getParameter("insStartDate");
		insEndDate = request.getParameter("insEndDate");
		permitNo = request.getParameter("permitNo");
		permitStartDate = request.getParameter("permitStartDate");
		permitEndDate = request.getParameter("permitEndDate");

		// Update Or Delete Parameters
		rowId = request.getParameter("updatedRow[id]");
		updatedCellVanNumber = request.getParameter("updatedRow[vanNumber]");
		updatedCellCompanyName = request.getParameter("updatedRow[companyName]");
		updatedCellVanModel = request.getParameter("updatedRow[vanModel]");
		updatedCellOwnerName = request.getParameter("updatedRow[ownerName]");
		updatedCellFitness = request.getParameter("updatedRow[fitness]");
		updatedCellVanCapacity = request.getParameter("updatedRow[vanCapacity]");
		updatedCellInsuranceNo = request.getParameter("updatedRow[insuranceNo]");
		updatedCellInsStartDate = request.getParameter("updatedRow[insStartDate]");
		updatedCellInsEndDate = request.getParameter("updatedRow[insEndDate]");
		updatedCellPermitNo = request.getParameter("updatedRow[permitNo]");
		updatedCellPermitStartDate = request.getParameter("updatedRow[permitStartDate]");
		updatedCellPermitEndDate = request.getParameter("updatedRow[permitEndDate]");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("In Van Servlet");

		PrintWriter pw = response.getWriter();

		// get all form data into variables
		getParaValues(request, response);

		// VanDataManager vdm = new VanDataManager();
		VanDao vdao = new VanDao();
		Van van = new Van();

		if (operation != null) {
			// For insert set ALL Parameters except ID
			if (operation.equals("insert")) {

				System.out.println("Insert Function");
				van.setVanNumber(vanNumber);
				van.setCompanyName(companyName);
				van.setVanModel(vanModel);
				van.setOwnerName(ownerName);
				van.setFitness(Integer.parseInt(fitness));
				van.setVanCapacity(Integer.parseInt(vanCapacity));
				van.setInsuranceNo(Integer.parseInt(insuranceNo));
				van.setInsStartDate(insStartDate);
				van.setInsEndDate(insEndDate);
				van.setPermitNo(Integer.parseInt(permitNo));
				van.setPermitStartDate(permitStartDate);
				van.setPermitEndDate(permitEndDate);
				van.setStatus(1);
				operationResp = vdao.insert(van);
				pw.println(operationResp);

			} else if (operation.equals("update")) {
				// For update set ALL Parameters
				System.out.println("Update Function");
				van.setId(Integer.parseInt(rowId));
				van.setVanNumber(updatedCellVanNumber);
				van.setCompanyName(updatedCellCompanyName);
				van.setVanModel(updatedCellVanModel);
				van.setOwnerName(updatedCellOwnerName);
				van.setFitness(Integer.parseInt(updatedCellFitness));
				van.setVanCapacity(Integer.parseInt(updatedCellVanCapacity));
				van.setInsuranceNo(Integer.parseInt(updatedCellInsuranceNo));
				van.setInsStartDate(updatedCellInsStartDate);
				van.setInsEndDate(updatedCellInsEndDate);
				van.setPermitNo(Integer.parseInt(updatedCellPermitNo));
				van.setPermitStartDate(updatedCellPermitStartDate);
				van.setPermitEndDate(updatedCellPermitEndDate);
				van.setStatus(1);
				operationResp = vdao.update(van);

				pw.println(operationResp);

			} else if (operation.equals("delete")) {
				// For delete set only ID Parameter
				System.out.println("Delete Function");
				van.setId(Integer.parseInt(rowId));
				operationResp = vdao.delete(van);
				pw.println(operationResp);

			}
		}

		// Contains All Data in table
		List<Van> vanList = new ArrayList<>();
		vanList = vdao.selectAll();
		new JsonCreator().createJson(vanList, jsonFilePath + "van.json");
	}

}
