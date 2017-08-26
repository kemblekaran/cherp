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
import com.cherp.entities.Area;
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
			// store table data in hahsmap with table name as the key data in
			// arraylist
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
			String purchaseMaxIdQuery = "select max(purchaseId) from purchase";
			String query = "insert into purchase(purchaseId,date,vanName,driver1,driver2,cleaner1,cleaner2,company,location,outstanding,challanNo,rent,product,pieces,kg,rate,amount,avgWeight,finalAmount,status)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			ps = con.prepareStatement(purchaseMaxIdQuery);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int purchaseMaxId = rs.getInt(1);

//				System.out.println("purchaseMaxId:" + purchaseMaxId);
				if (purchase.getCombinePurchaseToggle().equals("on")) {
					if (purchaseMaxId == 0) {
						purchaseMaxId = 1;
					}
					purchase.setPurchaseId(purchaseMaxId);
				} else {
					purchase.setPurchaseId(purchaseMaxId + 1);
				}
			}
			ps = con.prepareStatement(query);
			ps.setInt(1, purchase.getPurchaseId());
			ps.setString(2, purchase.getDate());
			ps.setString(3, purchase.getVanName());
			ps.setString(4, purchase.getDriver1());
			ps.setString(5, purchase.getDriver2());
			ps.setString(6, purchase.getCleaner1());
			ps.setString(7, purchase.getCleaner2());
			ps.setString(8, purchase.getCompany());
			ps.setString(9, purchase.getLocation());
			ps.setInt(10, purchase.getOutstanding());
			ps.setLong(11, purchase.getChallanNo());
			ps.setInt(12, purchase.getRent());
			ps.setString(13, purchase.getProduct());
			ps.setInt(14, purchase.getPieces());
			ps.setInt(15, purchase.getKg());
			ps.setInt(16, purchase.getRate());
			ps.setInt(17, purchase.getAmount());
			ps.setDouble(18, purchase.getAvgWeight());
			ps.setInt(19, purchase.getFinalAmount());
			ps.setInt(20, purchase.getStatus());

			if (ps.executeUpdate() == 1) {
				response = "Data added successfully...";
			} else {
				response = "Data not inserted..";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Exception in purchase data insert...";
		}

		return response;
	}

	// select all data
	public List<Purchase> selectData() {
		List<Purchase> purchaseViewList = new ArrayList<>();
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			// select if status is 1
			String squery = "select * from purchase where status=1";
			// System.out.println("Query: " + squery);

			ResultSet rs = stmt.executeQuery(squery);
			while (rs.next()) {
				Purchase purchase = new Purchase();
				purchase.setId(rs.getInt("id"));
				purchase.setPurchaseId(rs.getInt("purchaseId"));
				purchase.setDate(rs.getString("date"));
				purchase.setVanName(rs.getString("vanName"));
				purchase.setDriver1(rs.getString("driver1"));
				purchase.setDriver2(rs.getString("driver2"));
				purchase.setCleaner1(rs.getString("cleaner1"));
				purchase.setCleaner2(rs.getString("cleaner2"));
				purchase.setCompany(rs.getString("company"));
				purchase.setLocation(rs.getString("location"));
				purchase.setOutstanding(rs.getInt("outstanding"));
				purchase.setChallanNo(rs.getLong("challanNo"));
				purchase.setRent(rs.getInt("rent"));
				purchase.setProduct(rs.getString("product"));
				purchase.setPieces(rs.getInt("pieces"));
				purchase.setKg(rs.getInt("kg"));
				purchase.setRate(rs.getInt("rate"));
				purchase.setAmount(rs.getInt("amount"));
				purchase.setAvgWeight(rs.getDouble("avgWeight"));
				purchase.setFinalAmount(rs.getInt("finalAmount"));
				
				purchaseViewList.add(purchase);

			}

			// for(area u : areaList) {
			// System.out.println("Uname:"+u.getareaname()+", Pass:"+u.getPassword());
			// }
		} catch (Exception e) {
			e.printStackTrace();

		}
		return purchaseViewList;
	}
}
