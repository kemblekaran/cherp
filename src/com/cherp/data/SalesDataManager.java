package com.cherp.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Area;
import com.cherp.entities.Purchase;
import com.cherp.entities.Sales;
import java.sql.PreparedStatement;

public class SalesDataManager {
	private DBHandler handler;
	private Connection con;
	private Statement stmt;
	private PreparedStatement ps;
	private String response = "";
	
	// insert data into database
		public String addData(Sales sales) {

			try {

				// set status 1 for area
				sales.setStatus(1);
				handler = DBHandler.getInstance();
				con = handler.getConnection();

				String query = "insert into sales(invoiceNo,date,van,purchaseId,customer,product,pieces,kg,rate,amount,avgWeight,status)values(?,?,?,?,?,?,?,?,?,?,?,?)";
				
				ps = con.prepareStatement(query);
				
				ps.setInt(1,sales.getInvoiceNo());
				ps.setString(2, sales.getDate());
				ps.setString(3, sales.getVan());
				ps.setInt(4, sales.getPurchaseId());
				ps.setString(5, sales.getCustomer());
				ps.setString(6, sales.getProduct());
				ps.setInt(7,sales.getPieces());
				ps.setInt(8,sales.getKg());
				ps.setInt(9,sales.getRate());
				ps.setDouble(10,sales.getAmount());
				ps.setDouble(11,sales.getAvgWeight());
				ps.setInt(12, sales.getStatus());
				
				response = "Product Sell Successfully";

			} catch (Exception e) {
				e.printStackTrace();

			}
			return response;
		}

	public List<Purchase> tableDataGenerator(Sales sale) {
		List<Purchase> purchaseList = new ArrayList<>();
		 Sales sales = new Sales();
		Purchase purchase = new Purchase();
		// // queries to select records from each table

		ResultSet purchaseRs;
	
		// List<String> salesList = new ArrayList<>();

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
			}
			purchaseList.add(purchase);
//			System.out.println("sales list:");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return purchaseList;
	}
	
	// select all data
		public List<Purchase> selectSales(Purchase purchase) {
			System.out.println("In Sales");
			List<Purchase> purchaseViewList = new ArrayList<>();
			try {

				handler = DBHandler.getInstance();
				con = handler.getConnection();
				purchase.setPurchaseId(purchase.getPurchaseId());
				String squery = "select * from purchase where purchaseId=?";

				// System.out.println("Query: " + squery);
				PreparedStatement ps = con.prepareStatement(squery);
				ps.setInt(1, purchase.getPurchaseId());

				ResultSet rs = ps.executeQuery();

				while (rs.next()) {

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
