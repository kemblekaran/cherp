package com.cherp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Customer;
import com.cherp.entities.User;

public class CustomerDataManager {

	private DBHandler handler;
	private Connection con;
	private String response = "";
	private boolean hasRecord;
	PreparedStatement ps;

	// insert data into database
	public String addData(Customer cust) {

		try {

			// set status 1 for user
			cust.setStatus(1);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();
			String query = "insert into customer(fname,lname,shopName,curAdd,perAdd,state,city,area,mobile,phone,opBal,status) "
					+ "values( ?,?,?,?,?,?,?,?,?,?,?,?)";
			// String squery = "select * from customer where fname='" + cust.getFname() +
			// "'";

			ps = con.prepareStatement(query);
			
			ps.setString(1, cust.getFname());
			ps.setString(2, cust.getLname());
			ps.setString(3, cust.getShopName());
			ps.setString(4, cust.getCurAdd());
			ps.setString(5,cust.getPerAdd());
			ps.setString(6, cust.getState());
			ps.setString(7, cust.getCity());
			ps.setString(8, cust.getArea());
			ps.setLong(9, cust.getMobile());
			ps.setLong(10, cust.getPhone());
			ps.setInt(11, cust.getOpBal());
			ps.setInt(12, cust.getStatus());
			
			ps.executeUpdate();
			
			// check if record already exists or not
			// boolean hasRecord = hasData(stmt, squery);
			// if (hasRecord) {
//			stmt.execute(query);
			response = "Data addedd succesfully";
			// }

		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// Updates the database
	public String updateData(Customer cust) {
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			String squery = "select * from customer where fname=?";

			String uquery = "update customer set fname=? ,lname=? ,shopName=? ,curAdd=? ,perAdd=? ,state=? ,"
					+ "city=? ,area=?  ,mobile=? ,phone=? ,opBal=? where id=?";
			
			ps = con.prepareStatement(uquery);
			
			ps.setString(1, cust.getFname());
			ps.setString(2, cust.getLname());
			ps.setString(3, cust.getShopName());
			ps.setString(4, cust.getCurAdd());
			ps.setString(5,cust.getPerAdd());
			ps.setString(6, cust.getState());
			ps.setString(7, cust.getCity());
			ps.setString(8, cust.getArea());
			ps.setLong(9, cust.getMobile());
			ps.setLong(10, cust.getPhone());
			ps.setInt(11, cust.getOpBal());
			ps.setInt(12, cust.getId());
			
			ps.executeUpdate();
			
			System.out.println(uquery);
			// check if record already exists or not
			// hasRecord = hasData(stmt, squery);
			// if (hasRecord) {
//			stmt.executeUpdate(uquery);
			response = "Data addedd succesfully";
			// }

		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// delete data from datatables (changes the status in db to 0 for
	// invisibility
	// in datatables)
	public String deleteData(Customer cust) {
		try {

			// set status 1 for user
			cust.setStatus(0);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			String dquery = "update customer set status=?  where id=?";
					
			ps = con.prepareStatement(dquery);

			ps.setInt(1, cust.getStatus());
			ps.setInt(2, cust.getId());

			ps.executeUpdate();

//			stmt.executeUpdate(dquery);
			response = "Data deleted succesfully";
		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// select all data
	public List<Customer> selectData() {
		List<Customer> custList = new ArrayList<>();
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			// select if status is 1
			String squery = "select * from customer where status=1";
			// System.out.println("Query: " + squery);

			ResultSet rs = stmt.executeQuery(squery);
			while (rs.next()) {
				Customer cust = new Customer();
				cust.setId(rs.getInt("id"));
				cust.setFname(rs.getString("fname"));
				cust.setLname(rs.getString("lname"));
				cust.setShopName(rs.getString("shopName"));
				cust.setCurAdd(rs.getString("curAdd"));
				cust.setPerAdd(rs.getString("perAdd"));
				cust.setState(rs.getString("state"));
				cust.setCity(rs.getString("city"));
				cust.setArea(rs.getString("area"));
				cust.setMobile(rs.getLong("mobile"));
				cust.setPhone(rs.getLong("phone"));
				cust.setOpBal(rs.getInt("opBal"));

				custList.add(cust);

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return custList;
	}
}
