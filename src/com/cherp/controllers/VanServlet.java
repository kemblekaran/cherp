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
import com.cherp.data.VanDataManager;
import com.cherp.entities.Area;
import com.cherp.entities.Company;
import com.cherp.entities.Van;
import com.google.gson.stream.JsonWriter;

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

		VanDataManager vdm = new VanDataManager();
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

				operationResp = vdm.addData(van);
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

				operationResp = vdm.updateData(van);

				pw.println(operationResp);

			} else if (operation.equals("delete")) {
				// For delete set only ID Parameter
				System.out.println("Delete Function");
				van.setId(Integer.parseInt(rowId));
				operationResp = vdm.deleteData(van);
				pw.println(operationResp);

			}
		}

		// Contains All Data in table
		List<Van> vanList = new ArrayList<>();
		vanList = vdm.selectData();
		jsonFileWriter(vanList);
	}

	// method for creating json file
	public void jsonFileWriter(List<Van> vanList) {
		try {
			Writer writer = new FileWriter(jsonFilePath + "van.json");

			JsonWriter jw = new JsonWriter(writer);
			jw.beginObject();
			jw.name("data");
			jw.beginArray();
			for (Van v : vanList) {
				jw.beginObject();

				jw.name("id").value(v.getId());
				jw.name("vanNumber").value(v.getVanNumber());
				jw.name("companyName").value(v.getCompanyName());
				jw.name("vanModel").value(v.getVanModel());
				jw.name("ownerName").value(v.getOwnerName());
				jw.name("fitness").value(v.getFitness());
				jw.name("vanCapacity").value(v.getVanCapacity());
				jw.name("insuranceNo").value(v.getInsuranceNo());
				jw.name("insStartDate").value(v.getInsStartDate());
				jw.name("insEndDate").value(v.getInsEndDate());
				jw.name("permitNo").value(v.getPermitNo());
				jw.name("permitStartDate").value(v.getPermitStartDate());
				jw.name("permitEndDate").value(v.getPermitEndDate());
				jw.endObject();
			}
			jw.endArray();
			jw.endObject();
			jw.close();
		} catch (Exception e) {
		}
	}

}
