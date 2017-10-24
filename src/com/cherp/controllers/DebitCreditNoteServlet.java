package com.cherp.controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherp.dao.DebitCreditNote.DebitCreditNoteDao;
import com.cherp.dao.dataentry.SalesDAO;
import com.cherp.entities.Area;
import com.cherp.entities.DebitCredit;
import com.cherp.utils.JsonCreator;
import com.google.gson.stream.JsonWriter;

public class DebitCreditNoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String jsonFilePath;

	private String noteNoLoader = "";
	
	private String debitCredit = "";
	private String debitCreditDate = "";
	private String noteNo = "";
	private String customerCompany = "";
	private String selectCustCmp = "";
	private String amount = "";
	private String remarks = "";

	private String operation = "";

	// method for getting parameters
	public void getParaValues(HttpServletRequest request, HttpServletResponse response) {

		// context para for json files location
		jsonFilePath = request.getServletContext().getInitParameter("JsonFilePath");

		noteNoLoader = request.getParameter("noteNoLoader");
		
		debitCredit = request.getParameter("debitCredit");
		debitCreditDate = request.getParameter("debitCreditDate");
		noteNo = request.getParameter("noteNo");
		customerCompany = request.getParameter("customerCompany");
		selectCustCmp = request.getParameter("selectCustCmp");
		amount = request.getParameter("amount");
		remarks = request.getParameter("remarks");

		operation = request.getParameter("operation");

		System.out.println(" parameters" + debitCredit + " " + debitCreditDate + " " + noteNo + " " + customerCompany
				+ " " + selectCustCmp + " " + amount + " " + remarks);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("In debit credit servlet");
		getParaValues(request, response);

		DebitCreditNoteDao dcd = new DebitCreditNoteDao();
		DebitCredit dc = new DebitCredit();

		if (operation != null) {

			if (operation.equals("insert")) {
				System.out.println("insert function");
				dc.setDebitCredit(debitCredit);
				dc.setDebitCreditDate(debitCreditDate);
				dc.setNoteNo(Integer.parseInt(noteNo));
				dc.setCustomerCompany(customerCompany);
				dc.setSelectCustCmp(selectCustCmp);
				dc.setAmount(Integer.parseInt(amount));
				dc.setRemarks(remarks);
				dc.setStatus(1);

				dcd.insert(dc);

			}
		}
		
		if(noteNoLoader != null) {
			if(noteNoLoader.equals("true")) {
				int NoteNumber = new DebitCreditNoteDao().getNoteNumber();
				System.out.println("invoice no in sales servlet "+NoteNumber);
				jsonNoteNoLoader(NoteNumber);
			}
		}

		// Contains All Data in table
		List<DebitCredit> debitCreditList = new ArrayList<>();
		debitCreditList = dcd.selectAll();
		new JsonCreator().createJson(debitCreditList, jsonFilePath + "debitcreditnote.json");
	}
	
	
	public void jsonNoteNoLoader(int NoteNumber) {
		try {
			Writer writer = new FileWriter(jsonFilePath + "NoteNumber.json");
			JsonWriter jw = new JsonWriter(writer);
			jw.beginObject();
			jw.name("data");
			jw.beginArray();
			jw.beginObject();
			jw.name("NoteNumber").value(NoteNumber);
			jw.endObject();
			jw.endArray();
			jw.endObject();
			jw.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
