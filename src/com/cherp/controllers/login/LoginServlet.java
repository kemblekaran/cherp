package com.cherp.controllers.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cherp.dao.login.AdminLoginDao;
import com.cherp.entities.AdminLoginInfo;
import com.google.gson.JsonObject;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

	public LoginServlet() {
		super();

	}

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {
		username = request.getParameter("username");
		password = request.getParameter("password");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter pw = response.getWriter();
		JsonObject jo = new JsonObject();

		getParaValues(request, response);

		AdminLoginInfo adminLoginInfo = new AdminLoginInfo();
		adminLoginInfo.setUsername(username);
		adminLoginInfo.setPassword(password);

		AdminLoginDao ald = new AdminLoginDao();
		int loginSuccess = ald.loginSuccess(adminLoginInfo);

		if (loginSuccess == 1) {
			HttpSession session = request.getSession();
			session.setAttribute("UserLoginInfo", adminLoginInfo);
			jo.addProperty("status", 200);
			jo.addProperty("message", "Login Successful!");
		} else {
			jo.addProperty("status", 404);
			jo.addProperty("message", "Login Unsuccessful!");
		}

		
		pw.write(jo.toString());

		pw.close();
	}

}
