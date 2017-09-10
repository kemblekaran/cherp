package com.cherp.reports.DataManagers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Purchase;

public class VanWiseDataManager {

	private DBHandler handler;
	private Connection con;
	
	// select all data
	public List<Purchase> selectVanWiseSalesData(Purchase purchase) {
		List<Purchase> vanWiseSalesList = new ArrayList<>();
		
		System.out.println("In vanWiseSales");
		
		try {
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			// select if status is 1
			String squery = "select purchaseId,driver1,driver2,cleaner1,cleaner2 from purchase where date=? and vanName=?";
			System.out.println("Purchase Date : " + purchase.getDate());
			System.out.println("Van Number :" +purchase.getVanName());
			PreparedStatement ps = con.prepareStatement(squery);

			ps.setString(1, purchase.getDate());
			ps.setString(2, purchase.getVanName());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getDouble("amount"));
				purchase.setPurchaseId(rs.getInt("purchaseId"));
				purchase.setDriver1(rs.getString("driver1"));
				purchase.setDriver2(rs.getString("driver2"));
				purchase.setCleaner1(rs.getString("cleaner1"));
				purchase.setCleaner2(rs.getString("cleaner2"));
				purchase.setAmount(rs.getDouble("amount"));

				vanWiseSalesList.add(purchase);

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return vanWiseSalesList;
	}
}
