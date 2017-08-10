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
import com.cherp.entities.Drivers;

public class DriverDataManager {
	private DBHandler handler;
	private Connection con;
	private String response = "";
	private boolean hasRecord;
	PreparedStatement ps;

	// insert data into database
	public String addData(Drivers driver) {

		try {

			// set status 1 for user
			driver.setStatus(1);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

//			Statement stmt = con.createStatement();
			String query = "insert into driver(fname,lname,curAdd,perAdd,state,city,mobile,"
					+ "phone,adhaarNo,panNo,drLiscense,photo,status) values( ?,?,?,?,?,?,?,?,?,?,?,?,?)";
//			 String squery = "select * from driver where fname='" + driver.getFname() +  "'";

			ps = con.prepareStatement(query);
			
			ps.setString(1, driver.getFname());
			ps.setString(2, driver.getLname());
			ps.setString(3, driver.getCurAdd());
			ps.setString(4, driver.getPerAdd());
			ps.setString(5,driver.getState());
			ps.setString(6, driver.getCity());
			ps.setInt(7, driver.getMobile());
			ps.setInt(8, driver.getPhone());
			ps.setInt(9, driver.getAdhaarNo());
			ps.setInt(10, driver.getPanNo());
			ps.setInt(11, driver.getDrLiscense());
			ps.setString(12, driver.getPhoto());
			ps.setInt(13, driver.getStatus());
			
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
	public String updateData(Drivers driver) {
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			String squery = "select * from driver where fname='" + driver.getFname() + "'";

			String uquery = "update driver set fname=? ,lname=? ,curAdd=? ,perAdd=?, state=? ,city=? ,photo=? ,mobile=? ,phone=? ,"
					+ "adhaarNo=? ,drLiscense=? ,photo=? where id=?" ;

			ps = con.prepareStatement(uquery);
			
			ps.setString(1, driver.getFname());
			ps.setString(2, driver.getLname());
			ps.setString(3, driver.getCurAdd());
			ps.setString(4, driver.getPerAdd());
			ps.setString(5,driver.getState());
			ps.setString(6, driver.getCity());
			ps.setInt(7, driver.getMobile());
			ps.setInt(8, driver.getPhone());
			ps.setInt(9, driver.getAdhaarNo());
			ps.setInt(10, driver.getPanNo());
			ps.setInt(11, driver.getDrLiscense());
			ps.setString(12, driver.getPhoto());
			ps.setInt(13, driver.getId());
			
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
	public String deleteData(Drivers driver) {
		try {

			// set status 1 for user
			driver.setStatus(0);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

//			Statement stmt = con.createStatement();

			String dquery = "update driver set status=? where id=?";

			ps = con.prepareStatement(dquery);

			ps.setInt(1, driver.getStatus());
			ps.setInt(2, driver.getId());
			
			ps.executeUpdate();
//			stmt.executeUpdate(dquery);
			response = "Data deleted succesfully";
		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// select all data
	public List<Drivers> selectData() {
		List<Drivers> driverList = new ArrayList<>();
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			// select if status is 1
			String squery = "select * from driver where status=1";
			// System.out.println("Query: " + squery);

			ResultSet rs = stmt.executeQuery(squery);
			while (rs.next()) {
				Drivers driver = new Drivers();
				driver.setId(rs.getInt("id"));
				driver.setFname(rs.getString("fname"));
				driver.setLname(rs.getString("lname"));
				driver.setCurAdd(rs.getString("curAdd"));
				driver.setPerAdd(rs.getString("perAdd"));
				driver.setState(rs.getString("state"));
				driver.setCity(rs.getString("city"));
				driver.setPanNo(rs.getInt("panNo"));
				driver.setAdhaarNo(rs.getInt("adhaarNo"));
				driver.setMobile(rs.getInt("mobile"));
				driver.setPhone(rs.getInt("phone"));
				driver.setPhoto(rs.getString("photo"));
				driver.setDrLiscense(rs.getInt("drLiscense"));

				driverList.add(driver);

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return driverList;
	}

	// It return if data exists or not
	private boolean hasData(Statement stmt, String squery) throws SQLException {
		ResultSet rs = stmt.executeQuery(squery);

		if (!rs.next()) {
			response = "Data added successfully!";
			return true;
		} else {
			response = "Data already exists!";
			return false;
		}

	}
}
