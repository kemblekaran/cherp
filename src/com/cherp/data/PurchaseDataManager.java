package com.cherp.data;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Purchase;

public class PurchaseDataManager {
	private DBHandler handler;
	private Connection con;
	private Statement stmt;
	private PreparedStatement ps;
	private String response = "";

	public Map<String, ArrayList<String>> formDataGenerator() {

		// queries to select records from each table
		String vanQuery = "select companyName from van where status=1";
		String driverQuery = "select fname from driver where status=1";
		String cleanerQuery = "select fname from cleaner where status=1";
		String companyQuery = "select name from company where status=1";
		String locationQuery = "select location from location where status=1";
		String productQuery = "select prodName from product where status=1";

		ResultSet vanRs, driverRs, cleanerRs, companyRs, locationRs, productRs;

		Map<String, ArrayList<String>> resultSetList = new HashMap<>();
		ArrayList<String> a1, a2, a3, a4, a5, a6;

		handler = DBHandler.getInstance();
		con = handler.getConnection();
		try {
			stmt = con.createStatement();
			String tableName = "";
			// store table data in hasmap with table name as the key data in arraylist
			// For van
			vanRs = stmt.executeQuery(vanQuery);
			tableName = vanRs.getMetaData().getTableName(1);
			a1 = new ArrayList<String>();
			while (vanRs.next()) {
				a1.add(vanRs.getString(1));
			}
			resultSetList.put(tableName, a1);

			// For driver
			driverRs = stmt.executeQuery(driverQuery);
			tableName = driverRs.getMetaData().getTableName(1);
			a2 = new ArrayList<String>();
			while (driverRs.next()) {
				a2.add(driverRs.getString(1));
			}
			resultSetList.put(tableName, a2);

			// For cleaner
			cleanerRs = stmt.executeQuery(cleanerQuery);
			tableName = cleanerRs.getMetaData().getTableName(1);
			a3 = new ArrayList<String>();
			while (cleanerRs.next()) {
				a3.add(cleanerRs.getString(1));
			}
			resultSetList.put(tableName, a3);

			// For company
			companyRs = stmt.executeQuery(companyQuery);
			tableName = companyRs.getMetaData().getTableName(1);
			a4 = new ArrayList<String>();
			while (companyRs.next()) {
				a4.add(companyRs.getString(1));
			}
			resultSetList.put(tableName, a4);

			// For location
			locationRs = stmt.executeQuery(locationQuery);
			tableName = locationRs.getMetaData().getTableName(1);
			a5 = new ArrayList<String>();
			while (locationRs.next()) {
				a5.add(locationRs.getString(1));
			}
			resultSetList.put(tableName, a5);

			// For product
			productRs = stmt.executeQuery(productQuery);
			tableName = productRs.getMetaData().getTableName(1);
			a6 = new ArrayList<String>();
			while (productRs.next()) {
				a6.add(productRs.getString(1));
			}
			resultSetList.put(tableName, a6);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSetList;
	}

	// code for inserting data into database
	public String insertData(Purchase purchase) {

		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();


			purchase.setStatus(1);
			String query = "insert into purchase(date,van,driver1,driver2,cleaner1,cleaner2,company,location,outstanding,challanNo,rent,product,pieces,kg,rate,amount,weight,status)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			ps = con.prepareStatement(query);
			ps.setString(1, purchase.getDate());
			ps.setString(2, purchase.getVan());
			ps.setString(3, purchase.getDriver1());
			ps.setString(4, purchase.getDriver2());
			ps.setString(5, purchase.getCleaner1());
			ps.setString(6, purchase.getCleaner2());
			ps.setString(7, purchase.getCompany());
			ps.setString(8, purchase.getLocation());
			ps.setLong(9, purchase.getOutstanding());
			ps.setLong(10, purchase.getChallanNo());
			ps.setLong(11, purchase.getRent());
			ps.setString(12, purchase.getProduct());
			ps.setInt(13, purchase.getPieces());
			ps.setLong(14, purchase.getKg());
			ps.setLong(15, purchase.getRate());
			ps.setLong(16, purchase.getAmount());
			ps.setLong(17, purchase.getAvgWeight());
			ps.setInt(18, purchase.getStatus());

			if (ps.executeUpdate() == 1) {
				response = "Data added successfully...";
			}
			else {
				response = "Data not inserted..";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Exception in purchase data insert...";
		}

		return response;
	}
}
