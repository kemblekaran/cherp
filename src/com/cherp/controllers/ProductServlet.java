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

import com.cherp.dao.masters.ProductDao;
import com.cherp.data.ProductDataManager;
import com.cherp.entities.Product;
import com.cherp.utils.JsonCreator;
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

		// ProductDataManager pdm = new ProductDataManager();
		ProductDao pdao = new ProductDao();
		Product prod = new Product();

		if (operation != null) {
			// For insert set ALL Parameters except ID
			if (operation.equals("insert")) {

				System.out.println("Insert Function");
				prod.setProdName(prodName);
				prod.setProdType(prodType);

				operationResp = pdao.insert(prod);
				pw.println(operationResp);

			} else if (operation.equals("update")) {
				// For update set ALL Parameters
				System.out.println("Update Function");
				prod.setId(Integer.parseInt(rowId));
				prod.setProdName(updatedCellProdName);
				prod.setProdType(updatedCellProdType);
				operationResp = pdao.update(prod);
				pw.println(operationResp);

			} else if (operation.equals("delete")) {
				// For delete set only ID Parameter
				System.out.println("Delete Function");
				prod.setId(Integer.parseInt(rowId));

				operationResp = pdao.delete(prod);
				pw.println(operationResp);

			}
		}

		// Contains All Data in table
		List<Product> prodList = new ArrayList<>();
		prodList = pdao.selectAll();
		new JsonCreator().createJson(prodList, jsonFilePath + "product.json");
	}

}
