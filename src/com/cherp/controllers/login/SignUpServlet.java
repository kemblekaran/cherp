package com.cherp.controllers.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.dao.login.AdminLoginDao;
import com.cherp.entities.AdminLoginInfo;
import com.google.gson.JsonObject;

public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String companyName;
	private String companyAddress;
	private String comapanyNo;

	public SignUpServlet() {
		super();

	}

	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {
		username = request.getParameter("susername");
		password = request.getParameter("spassword");
		companyName = request.getParameter("companyName");
		companyAddress = request.getParameter("companyAddress");
		comapanyNo = request.getParameter("companyNo");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter pw = response.getWriter();
		JsonObject jo = new JsonObject();

		getParaValues(request, response);

		AdminLoginInfo adminLoginInfo = new AdminLoginInfo();
		adminLoginInfo.setUsername(username);
		adminLoginInfo.setPassword(password);
		adminLoginInfo.setCompanyName(companyName);
		adminLoginInfo.setCompanyAddress(companyAddress);
		adminLoginInfo.setCompanyNumber(Long.parseLong(comapanyNo));
		adminLoginInfo.setCreatedDateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		adminLoginInfo.setStatus(1);

		AdminLoginDao ald = new AdminLoginDao();
		int success = ald.insert(adminLoginInfo);

		if (success == 1) {
			jo.addProperty("status", 200);
			jo.addProperty("message", "Account created!");
		} else {
			jo.addProperty("status", 404);
			jo.addProperty("message", "Account not created!");
		}

		pw.write(jo.toString());

		pw.close();
	}

}
