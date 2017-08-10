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
import com.cherp.data.ProductDataManager;
import com.cherp.entities.Area;
import com.cherp.entities.Product;
import com.google.gson.stream.JsonWriter;

public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String jsonFilePath = "";

	private String prodName = "";
	private String prodType = "";
	private String operation = "";

	private String operationResp = "";

	private String rowId = "";
	private String updatedCellProdName = "";
	private String updatedCellProdType = "";

	public ProductServlet() {
		super();

	}

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {
		// Add Update Or Delete Parameters
		operation = request.getParameter("operation");

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		// Insert Form Parameters
		prodName = request.getParameter("prodName");
		prodType = request.getParameter("prodType");

		// Update Or Delete Parameters
		rowId = request.getParameter("updatedRow[id]");
		updatedCellProdName = request.getParameter("updatedRow[prodName]");
		updatedCellProdType = request.getParameter("updatedRow[prodType]");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("In ProductServlet");

		PrintWriter pw = response.getWriter();

		// get all form data into variables
		getParaValues(request, response);

		ProductDataManager pdm = new ProductDataManager();
		Product prod = new Product();

		if (operation != null) {
			// For insert set ALL Parameters except ID
			if (operation.equals("insert")) {

				System.out.println("Insert Function");
				prod.setProd_name(prodName);
				prod.setProd_type(prodType);

				operationResp = pdm.addData(prod);
				pw.println(operationResp);

			} else if (operation.equals("update")) {
				// For update set ALL Parameters
				System.out.println("Update Function");
				prod.setId(Integer.parseInt(rowId));
				prod.setProd_name(updatedCellProdName);
				prod.setProd_type(updatedCellProdType);
				operationResp = pdm.updateData(prod);
				pw.println(operationResp);

			} else if (operation.equals("delete")) {
				// For delete set only ID Parameter
				System.out.println("Delete Function");
				prod.setId(Integer.parseInt(rowId));

				operationResp = pdm.deleteData(prod);
				pw.println(operationResp);

			}
		}

		// Contains All Data in table
		List<Product> prodList = new ArrayList<>();
		prodList = pdm.selectData();
		jsonFileWriter(prodList);
	}

	// method for creating json file
	public void jsonFileWriter(List<Product> prodList) {

		try {

			Writer writer = new FileWriter(jsonFilePath + "product.json");
			JsonWriter jw = new JsonWriter(writer);
			jw.beginObject();
			jw.name("data");
			jw.beginArray();
			for (Product p : prodList) {
				jw.beginObject();
				jw.name("id").value(p.getId());
				jw.name("prodName").value(p.getProd_name());
				jw.name("prodType").value(p.getProd_type());
				jw.endObject();
			}
			jw.endArray();
			jw.endObject();
			jw.close();
		} catch (Exception e) {
		}
	}
}
