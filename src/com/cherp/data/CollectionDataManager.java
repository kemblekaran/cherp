package com.cherp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Collection;
import com.cherp.entities.SalesLoad;

public class CollectionDataManager {
	private DBHandler handler;
	private Connection con;
	private String response = "";
	PreparedStatement ps,pst,pps;

	//insert collection data
	public String addData(Collection collection) {

		try {
			handler = DBHandler.getInstance();
			con = handler.getConnection();
			collection.setStatus(1);
			
			String query = "insert into collection(collectionDate, customer, area, collectionMode, name, depositIn, branch, chDate, chNo, toBeReceived, payNow,"
					+ " closingBal,status)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

			ps = con.prepareStatement(query);
			ps.setString(1, collection.getCollectionDate());
			ps.setString(2, collection.getCustomer());
			ps.setString(3, collection.getArea());
			ps.setString(4, collection.getCollectionMode());
			ps.setString(5, collection.getName());
			ps.setString(6, collection.getDepositIn());
			ps.setString(7, collection.getBranch());
			ps.setString(8, collection.getChDate());
			ps.setLong(9, collection.getChNo());
			ps.setDouble(10, collection.getToBeReceived());
			ps.setDouble(11, collection.getPayNow());
			ps.setDouble(12, collection.getClosingBal());
			ps.setInt(13, collection.getStatus());

			ps.executeUpdate();

			response = "data added successfully";

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	//update salesload data
	public String updateSalesLoad(Collection collection) {
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();
//			collection collection = new collection();
			
			//getting salesload data
			String plQuery = "select * from salesload where customer=?";
			SalesLoad salesload = null;
			
			pst = con.prepareStatement(plQuery);
			pst.setString(1, collection.getCustomer());
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				salesload = new SalesLoad();
				salesload.setBalanceAmount(rs.getDouble("balanceAmount"));
				salesload.setId(rs.getInt("id"));
//				System.out.println("balance...."+salesload.getBalanceAmount());
//				System.out.println("paynow.........."+collection.getPayNow());
//				System.out.println(salesload.getBalanceAmount() - collection.getPayNow() +" ....bal-pay");
			
				if(collection.getPayNow() > salesload.getBalanceAmount()){
					System.out.println("pay>bal");
					collection.setPayNow(collection.getPayNow() - salesload.getBalanceAmount());
					salesload.setBalanceAmount(0.0);
//					System.out.println(salesload.getBalanceAmount()+" <-balance will be zero");
					
					
					
				}else{
					salesload.setBalanceAmount(salesload.getBalanceAmount() - collection.getPayNow());
					
					collection.setPayNow(0.0);
//					System.out.println(collection.getPayNow()+"<- paynow will be zero");
					
					
				}
				String uquery = "update salesload set balanceAmount=? where id=?";
				ps = con.prepareStatement(uquery);
				ps.setDouble(1, salesload.getBalanceAmount());
				ps.setInt(2, salesload.getId());
				ps.executeUpdate();
			}
			response = "data updated successfully";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}
	
	
	
	//delete salesload details when balance will get zero.
	public String deleteSalesLoad(SalesLoad salesload) {
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();
			new Collection();

			String uquery = "delete from salesload  where balanceAmount=?";

			
			System.out.println("in delete datamanager");
			ps = con.prepareStatement(uquery);
			ps.setInt(1, 0);
			
			
			ps.executeUpdate();
			response = "data updated successfully";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}
	
	

	// select all data for collection 
	public List<Collection> selectData() {
		List<Collection> collectionList = new ArrayList<>();
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			String squery = "select * from collection where status=1";

			ResultSet rs = stmt.executeQuery(squery);
		
			while (rs.next()) {
//				System.out.println("collection resultset "+rs.toString());
				Collection collection = new Collection();
				collection.setId(rs.getInt("id"));
				collection.setCollectionDate(rs.getString("collectionDate"));
				collection.setCustomer(rs.getString("customer"));
				collection.setArea(rs.getString("area"));
				collection.setCollectionMode(rs.getString("collectionMode"));
				collection.setName(rs.getString("name"));
				collection.setDepositIn(rs.getString("depositIn"));
				collection.setBranch(rs.getString("branch"));
				collection.setChDate(rs.getString("chDate"));
				collection.setChNo(rs.getLong("chNo"));
				collection.setToBeReceived(rs.getDouble("toBeReceived"));
				collection.setPayNow(rs.getDouble("payNow"));
				collection.setClosingBal(rs.getDouble("closingBal"));
				collectionList.add(collection);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return collectionList;
	}
	
	
	
	//select payData to show in collection table
	public List<SalesLoad> selectSalesData() {
		
		List<SalesLoad> salesLoadList = new ArrayList<>();
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			// select if status is 1
			String squery = "select * from salesload where status=1";
			// System.out.println("Query: " + squery);

			ResultSet rs = stmt.executeQuery(squery);
			while (rs.next()) {
				SalesLoad salesload = new SalesLoad();
				
				salesload.setId(rs.getInt("id"));
				salesload.setInvoiceNo(rs.getInt("invoiceNo"));
				salesload.setDate(rs.getString("date"));
				salesload.setCustomer(rs.getString("customer"));
				salesload.setInvoiceAmount(rs.getDouble("invoiceAmount"));
				salesload.setBalanceAmount(rs.getDouble("balanceAmount"));
				salesload.setStatus(rs.getInt("status"));
				salesLoadList.add(salesload);

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return salesLoadList;
	}

}
