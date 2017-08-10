package com.cherp.controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.data.UserDataManager;
import com.cherp.entities.User;
import com.google.gson.stream.JsonWriter;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String jsonFilePath;

	private String name = "";
	private String pwd = "";
	private String operation = "";

	private String operationResp = "";

	private String rowId = "";
	private String updatedUName = "";

	public UserServlet() {
		super();
	}

	//method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {
		//Add Update Or Delete Parameters
		operation = request.getParameter("operation");
		
		// context para for json files location
				jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");
				System.out.println(jsonFilePath);
		
		//Insert Form Parameters
		name = request.getParameter("username");
		pwd = request.getParameter("password");
		System.out.println("username="+name);
		//Update Or Delete Parameters
		rowId = request.getParameter("updatedRow[id]");
		updatedUName = request.getParameter("updatedRow[username]");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("In UserServlet");
		
		PrintWriter pw = response.getWriter();

		// get all form data into variables
		getParaValues(request, response);

		UserDataManager udm = new UserDataManager();
		User user = new User();
		System.out.println("Data = "+operation);
		System.out.println("Name="+name);
		
		if (operation != null) {
			//For insert set ALL Parameters except ID
			if (operation.equals("insert")) {
				
				System.out.println("Insert Function");
				user.setUsername(name);
				user.setPassword(pwd);
				operationResp = udm.addData(user);
				pw.println(operationResp);
				
			} else if (operation.equals("update")) {
				//For update set ALL Parameters
				System.out.println("Update Function");
				user.setId(Integer.parseInt(rowId));
				user.setUsername(updatedUName);
				operationResp = udm.updateData(user);
				pw.println(operationResp);
				
			} else if (operation.equals("delete")) {
				//For delete set only ID Parameter 
				System.out.println("Delete Function");
				user.setId(Integer.parseInt(rowId));
				operationResp = udm.deleteData(user);
				pw.println(operationResp);
				
			}
		}
		
		//Contains All Data in table
		List<User> userList = new ArrayList<>();
		userList = udm.selectData();
		jsonFileWriter(userList);

	}
	
	
	//method for creating json file
	public void jsonFileWriter(List<User> userList) {
		try (Writer writer = new FileWriter(jsonFilePath + "user.json")) {
			JsonWriter jw = new JsonWriter(writer);
			jw.beginObject();
			jw.name("data");
			jw.beginArray();
			for (User u : userList) {
				jw.beginObject();
				jw.name("id").value(u.getId());
				jw.name("username").value(u.getUsername());
				jw.endObject();
			}
			jw.endArray();
			jw.endObject();
			jw.close();
		} catch (Exception e) {
		}
	}

}
