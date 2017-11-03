package com.cherp.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.dao.masters.UserDao;
import com.cherp.entities.User;
import com.cherp.utils.JsonCreator;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String jsonFilePath;

	private String username = "";
	private String password = "";
	private String operation = "";

	private String operationResp = "";

	private String rowId = "";
	private String updatedCellUsername = "";

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
		username = request.getParameter("username");
		password = request.getParameter("password");
		
		System.out.println("username="+username);
		
		//Update Or Delete Parameters
		rowId = request.getParameter("updatedRow[id]");
		updatedCellUsername = request.getParameter("updatedRow[username]");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("In UserServlet");
		
		PrintWriter pw = response.getWriter();

		// get all form data into variables
		getParaValues(request, response);

//		UserDataManager udm = new UserDataManager();
		UserDao udao = new UserDao();
		User user = new User();
		
		System.out.println("Data = "+operation);
		
		if (operation != null) {
			//For insert set ALL Parameters except ID
			if (operation.equals("insert")) {
				
				System.out.println("Insert Function");
				
				user.setUsername(username);
				user.setPassword(password);
				
				operationResp = udao.insert(user);
				pw.println(operationResp);
				
			} else if (operation.equals("update")) {
				//For update set ALL Parameters
				System.out.println("Update Function");
				user.setId(Integer.parseInt(rowId));
				user.setUsername(updatedCellUsername);
				user.setStatus(1);
				operationResp = udao.update(user);
				pw.println(operationResp);
				
			} else if (operation.equals("delete")) {
				//For delete set only ID Parameter 
				System.out.println("Delete Function");
				user.setId(Integer.parseInt(rowId));
				operationResp = udao.delete(user);
				pw.println(operationResp);
				
			}
		}
		
		//Contains All Data in table
		List<User> userList = new ArrayList<>();
		userList = udao.selectAll();
		new JsonCreator().createJson(userList,jsonFilePath+"user.json");

	}
	
	


}
