package com.cherp.controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.data.PurchaseDataManager;
import com.google.gson.stream.JsonWriter;

public class PurchaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String dataLoader = "";
	private String jsonFilePath = "";

	public PurchaseServlet() {
		super();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("In Purchase Servlet");

		dataLoader = request.getParameter("dataLoader");
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");
		Map<String, ArrayList<String>> resultSetList = new HashMap<>();
		if (dataLoader.equals("true")) {
			resultSetList = new PurchaseDataManager().formDataGenerator();

		}

		jsonFileWriter(resultSetList);

	}

	// method for creating json file
	public void jsonFileWriter(Map<String, ArrayList<String>> resultSetList) {
		try {
			Writer writer = new FileWriter(jsonFilePath + "purchaseLoader.json");

			JsonWriter jw = new JsonWriter(writer);
			jw.beginObject();
			
			for (Map.Entry<String, ArrayList<String>> map : resultSetList.entrySet()) {
				jw.name(map.getKey());
				jw.beginArray();

				for (String value : map.getValue()) {
					jw.beginObject();
					jw.name("name").value(value);
					jw.endObject();
					System.out.println("key:" + map.getKey() + ",value:" + value);
				}
				
				jw.endArray();
			}
			
			jw.endObject();
			jw.close();
		} catch (Exception e) {
		}
	}

}
