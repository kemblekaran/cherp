package com.cherp.reports;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.entities.Purchase;
import com.cherp.reports.DataManagers.VanWiseDataManager;

public class VanWiseSales extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String date = "";
	private String van = "";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		VanWiseDataManager vsdm = new VanWiseDataManager();

		date = request.getParameter("date");
		van = request.getParameter("van");

		System.out.println("van :" + van + ", date :" + date);

		List<Purchase> vanWiseSalesList = new ArrayList<>();
		Purchase purchase = new Purchase();
		
		purchase.setDate(date);
		purchase.setVanName(van);

		vanWiseSalesList = vsdm.selectVanWiseSalesData(purchase);
		System.out.println("VanWise Sales List :" + vanWiseSalesList);
	}

}
