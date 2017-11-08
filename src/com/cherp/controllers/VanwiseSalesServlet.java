package com.cherp.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.dao.dataentry.PurchaseDao;
import com.google.gson.JsonObject;

public class VanwiseSalesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String purchaseId;
	private String settleVan;

	public VanwiseSalesServlet() {
		super();

	}

	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {
		purchaseId = request.getParameter("purchaseId");
		settleVan = request.getParameter("settleVan") == null ? "" : request.getParameter("settleVan");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter pw = response.getWriter();

		JsonObject jo = new JsonObject();

		getParaValues(request, response);

		if (settleVan.equals("1")) {

			String pdIdArry[] = purchaseId.split(",");

			int success = 0;
			System.out.println("Pid array:"+pdIdArry);
			for (String p : pdIdArry) {
				success = new PurchaseDao().update(Integer.parseInt(p), 1);
			}

			if (success == 1) {
				jo.addProperty("status", 200);
				jo.addProperty("message", "Van Settled!");
			} else {
				jo.addProperty("status", 404);
				jo.addProperty("message", "Cannot Settle Van!");
			}
		}

		pw.write(jo.toString());
		pw.close();

	}

}
