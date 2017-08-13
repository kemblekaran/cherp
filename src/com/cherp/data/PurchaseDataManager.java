package com.cherp.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cherp.dbconnection.DBHandler;

public class PurchaseDataManager {
	private DBHandler handler;
	private Connection con;
	private Statement stmt;

	public Map<String, ArrayList<String>> formDataGenerator() {
		
		//queries to select records from each table
		String vanQuery = "select companyName from van where status=1";
		String driverQuery = "select fname from driver where status=1";
		String cleanerQuery = "select fname from cleaner where status=1";
		String companyQuery = "select name from company where status=1";
		String productQuery = "select prodName from product where status=1";

		ResultSet vanRs, driverRs, cleanerRs, companyRs, productRs;

		Map<String, ArrayList<String>> resultSetList = new HashMap<>();
		ArrayList<String> a1,a2,a3,a4,a5;

		handler = DBHandler.getInstance();
		con = handler.getConnection();
		try {
			stmt = con.createStatement();
			String tableName = "";
			//store table data in hasmap with table name as the key data in arraylist
			//For van
			vanRs = stmt.executeQuery(vanQuery);
			tableName = vanRs.getMetaData().getTableName(1);
			a1 = new ArrayList<String>();
			while (vanRs.next()) {
				a1.add(vanRs.getString(1));
			}
			resultSetList.put(tableName, a1);
			
			//For driver
			driverRs = stmt.executeQuery(driverQuery);
			tableName = driverRs.getMetaData().getTableName(1);
			a2 = new ArrayList<String>();
			while (driverRs.next()) {
				a2.add(driverRs.getString(1));
			}
			resultSetList.put(tableName, a2);

			//For cleaner
			cleanerRs = stmt.executeQuery(cleanerQuery);
			tableName = cleanerRs.getMetaData().getTableName(1);
			a3 = new ArrayList<String>();
			while (cleanerRs.next()) {
				a3.add(cleanerRs.getString(1));
			}
			resultSetList.put(tableName, a3);
			
			//For company
			companyRs = stmt.executeQuery(companyQuery);
			tableName = companyRs.getMetaData().getTableName(1);
			a4 = new ArrayList<String>();
			while (companyRs.next()) {
				a4.add(companyRs.getString(1));
			}
			resultSetList.put(tableName, a4);
			
			//For product
			productRs = stmt.executeQuery(productQuery);
			tableName = productRs.getMetaData().getTableName(1);
			a5 = new ArrayList<String>();
			while (productRs.next()) {
				a5.add(productRs.getString(1));
			}
			resultSetList.put(tableName, a5);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSetList;
	}
}
