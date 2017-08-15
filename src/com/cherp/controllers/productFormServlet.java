package com.cherp.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class productFormServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String prodName =   request.getParameter("prodName");
		String prodPieces =   request.getParameter("prodPieces");
		String prodKG =   request.getParameter("prodKG");
		
		System.out.println("Name :"+prodName+", Pieces :"+prodPieces+", Kg :"+prodKG);
		
	}

}
