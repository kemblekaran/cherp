package com.cherp.controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import com.cherp.dbconnection.DBHandler;
import com.google.gson.stream.JsonWriter;

public class PurchaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String dataLoader = "";
	private String jsonFilePath = "";
	
	private DBHandler handler;
	private Connection con;

	PreparedStatement ps;

	private String date = "";
	private String van = "";
	private String driver1 = "";
	private String driver2 = "";
	private String cleaner1 = "";
	private String cleaner2 = "";
	private String company = "";
	private String location = "";
	private String outstanding;
	private String challanNo;
	private String rent;
	private String product = "";
	private String pieces = "";
	private String kg = "";
	private String rate = "";
	private String amount = "";
	private String weight = "";

	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {

		date = request.getParameter("date");
		van = request.getParameter("van");
		driver1 = request.getParameter("driver1");
		driver2 = request.getParameter("driver2");
		cleaner1 = request.getParameter("cleaner1");
		cleaner2 = request.getParameter("cleaner2");
		company = request.getParameter("company");
		location = request.getParameter("location");
		outstanding = request.getParameter("outstanding");
		challanNo = request.getParameter("challanNO");
		rent = request.getParameter("rent");
		
		product = request.getParameter("product");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("In Purchase Servlet");
		
		//code for inserting data into database
		try {

			PrintWriter pw = response.getWriter();
			System.out.println("in purchase servlet");
			// get all para values
			getParaValues(request, response);
			
			System.out.println(date + " " + van + " " + driver1 + " " + driver2 + "  " + product + " "
					+ product + " " + company + " " + location + " " + outstanding + " " + challanNo + " "
					+ rent);
			
				handler = DBHandler.getInstance();
				con = handler.getConnection();
				
				
				System.out.println("in insert");
				
				String query = "insert into purchase(date,van,driver1,driver2,cleaner1,cleaner2,company,location,outstanding,challanNo,rent,product,pieces,kg,rate,amount,weight,status)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				ps = con.prepareStatement(query);

				ps.setString(1, date);
				ps.setString(2, van);
				ps.setString(3, driver1);
				ps.setString(4, driver2);
				ps.setString(5, cleaner1);
				ps.setString(6, cleaner2);
				ps.setString(9, company);
				ps.setString(10, location);
				ps.setString(11, outstanding);
				ps.setString(12, challanNo);
				ps.setString(13, rent);
				ps.setString(14, product);
				ps.setString(15, pieces);
				

				ps.executeUpdate();
				pw.write("Data added successfully");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
