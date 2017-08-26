package com.cherp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Data;
import com.cherp.entities.Purchase;

public class SalesDataSelectorManager {
	private PreparedStatement ps;
	private DBHandler handler;
	private Connection con;
	private ResultSet rs;

	public List<Purchase> DataSelector(Purchase purchase) {
		handler = DBHandler.getInstance();
		con = handler.getConnection();

		List<Purchase> dataSelectorList = new ArrayList<>();

		String dataSelectorQuery = "select * from purchase where vanName=? and date=?";
		try {
			ps = con.prepareStatement(dataSelectorQuery);
			ps.setString(1, purchase.getVanName());
			ps.setString(2, purchase.getDate());

			rs = ps.executeQuery();

			while (rs.next()) {
				Data data = new Data();

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

				dataSelectorList.add(purchase);

				// data.setDataSelector(dataSelector);

				System.out.println(dataSelectorList.toString());
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		return dataSelectorList;
	}

}
