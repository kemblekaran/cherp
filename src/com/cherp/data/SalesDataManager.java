package com.cherp.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Purchase;
import com.cherp.entities.Sales;
import java.sql.PreparedStatement;

public class SalesDataManager {
	private DBHandler handler;
	private Connection con;
	private Statement stmt;
	private PreparedStatement ps;
	private String response = "";

	public List<Purchase> tableDataGenerator(Sales sale) {
		List<Purchase> purchaseList = new ArrayList<>();
		 Sales sales = new Sales();
		Purchase purchase = new Purchase();
		// // queries to select records from each table
		//
		ResultSet purchaseRs, salesRs;
		//
		// List<String> salesList = new ArrayList<>();
		//
		handler = DBHandler.getInstance();
		con = handler.getConnection();
		try {
			stmt = con.createStatement();
			String purchaseQuery = "select * from purchase where status=1";
			String vanQuery = "select * from van where companyName=?";
			ps = con.prepareStatement(vanQuery);
			ps.setString(1, sales.getVan());
			ps.executeQuery();
			//
			// // For product
			purchaseRs = stmt.executeQuery(purchaseQuery);
			//
			// while (purchaseRs.next()) {
			// sales.setDate(purchaseRs.getString("date"));
			// sales.setVan(purchaseRs.getString("vanName"));
			//
			// }
			// System.out.println("salesDate : " + sales.getDate() + ",
			// salesVanName
			// :" + sales.getVan());
			// purchaseRs.close();
			// String data = "select * from purchase where vanName=" +
			// purchaseRs.getString("vanName");
			// salesRs = stmt.executeQuery(data);
			// while (salesRs.next()) {
			// if (sales.getVan().equals(purchaseRs.getString("vanName"))) {
			// purchase.getProduct();
			// System.out.println("Product : " + purchase.getProduct());
			// }
			// }

//			String data = "select * from purchase where vanName=" + sales.getVan();
//			salesRs = stmt.executeQuery(data);
			while (purchaseRs.next()) {
				purchase.setProduct(purchaseRs.getString("product"));
				purchase.setDriver1(purchaseRs.getString("driver1"));
				System.out.println("Product :" + purchase.getProduct());
			}
			purchaseList.add(purchase);
//			System.out.println("sales list:");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return purchaseList;
	}
}
