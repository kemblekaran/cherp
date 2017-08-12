package com.cherp.controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.data.PurchaseDataManager;
import com.cherp.entities.Area;
import com.google.gson.Gson;
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
		List<ResultSet> resultSetList = new ArrayList<>();
		if (dataLoader.equals("true")) {
			resultSetList = new PurchaseDataManager().formDataGenerator();
			
		}
		
		jsonFileWriter(resultSetList);

	}

	// method for creating json file
	public void jsonFileWriter(List<ResultSet> resultSetList) {
		try {
			Writer writer = new FileWriter(jsonFilePath + "purchaseData.json");
			
			JsonWriter jw = new JsonWriter(writer);
			jw.beginObject();
			System.out.println("ArrayList Size:"+resultSetList.size());
			System.out.println("Table name:"+resultSetList.get(1).getMetaData().getTableName(1));
			for (ResultSet rs : resultSetList) {
				
				jw.name("name");
				jw.beginArray();
				jw.beginObject();
				while (rs.next()) {
					jw.name("name").value(rs.getString(1));
					System.out.println(rs.getString(1));
				}
				jw.endObject();
				jw.endArray();
			}
			jw.endObject();
			jw.close();
		} catch (Exception e) {
		}
	}

}
