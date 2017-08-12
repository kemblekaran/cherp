package com.cherp.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cherp.dbconnection.DBHandler;

public class PurchaseDataManager {
	private DBHandler handler;
	private Connection con;
	private Statement stmt;

	public List<ResultSet> formDataGenerator() {
		String vanQuery = "select companyName from van where status=1";
		String driverQuery = "select fname from driver where status=1";
		String cleanerQuery = "select fname from cleaner where status=1";
		String companyQuery = "select name from company where status=1";

		ResultSet vanRs, driverRs, cleanerRs, companyRs;

		List<ResultSet> resultSetList = new ArrayList<>();

		handler = DBHandler.getInstance();
		con = handler.getConnection();
		try {
			stmt = con.createStatement();
			vanRs = stmt.executeQuery(vanQuery);
			driverRs = stmt.executeQuery(driverQuery);
			cleanerRs = stmt.executeQuery(cleanerQuery);
			companyRs = stmt.executeQuery(companyQuery);
			resultSetList.add(vanRs);
			resultSetList.add(driverRs);
			resultSetList.add(cleanerRs);
			resultSetList.add(companyRs);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSetList;
	}
}
