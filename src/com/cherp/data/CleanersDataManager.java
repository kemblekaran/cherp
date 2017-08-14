package com.cherp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Cleaners;
import com.cherp.entities.Expenses;

public class CleanersDataManager {
	private DBHandler handler;
	private Connection con;
	private String response = "";
	private boolean hasRecord;

	// insert data into database
	public String addData(Cleaners cls) {

		try {

			String query = "insert into cleaner(fname,lname,curAdd,perAdd,state,city,mobile,phone,drLisence,panNo,adhaarNo,photo,status) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

			// set status 1 for area
			cls.setStatus(1);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			PreparedStatement ps = con.prepareStatement(query);

			System.out.println(query);
			// check if record already exists or not
			// boolean hasRecord = hasData(stmt, squery);
			// if (hasRecord) {
			ps.setString(1, cls.getFname());
			ps.setString(2, cls.getLname());
			ps.setString(3, cls.getCurAdd());
			ps.setString(4, cls.getPerAdd());
			ps.setString(5, cls.getState());
			ps.setString(6, cls.getCity());
			ps.setInt(7, cls.getMobile());
			ps.setInt(8, cls.getPhone());
			ps.setInt(9, cls.getDrLisence());
			ps.setInt(10, cls.getPanNo());
			ps.setInt(11, cls.getAdhaarNo());
			ps.setString(12, cls.getPhoto());
			ps.setInt(13,cls.getStatus());
			ps.executeUpdate();
			// }
			response = "Data added successfully!";
		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// Updates the database
	public String updateData(Cleaners cls) {
		try {

			String uquery = "update cleaner set fname=?,lname=?,curAdd=?,perAdd=?,state=?,city=?,mobile=?,phone=?,drLisence=?,panNo=?,adhaarNo=?,photo=? where id=?";

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			System.out.println("U:" + uquery);
			// check if record already exists or not
			// hasRecord = hasData(stmt, squery);

			PreparedStatement ps = con.prepareStatement(uquery);
			ps.setString(1, cls.getFname());
			ps.setString(2, cls.getLname());
			ps.setString(3, cls.getCurAdd());
			ps.setString(4, cls.getPerAdd());
			ps.setString(5, cls.getState());
			ps.setString(6, cls.getCity());
			ps.setInt(7, cls.getMobile());
			ps.setInt(8, cls.getPhone());
			ps.setInt(9, cls.getDrLisence());
			ps.setInt(10, cls.getPanNo());
			ps.setInt(11, cls.getAdhaarNo());
			ps.setString(12, cls.getPhoto());
			ps.setInt(13, cls.getId());

			// if (hasRecord) {

			ps.executeUpdate();
			// }
			response = "Data added successfully!";
		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// delete data from datatables (changes the status in db to 0 for
	// invisibility
	// in datatables)
	public String deleteData(Cleaners cls) {
		try {

			String dquery = "update cleaner set status=? where id=?";

			// set status 1 for area
			cls.setStatus(0);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			PreparedStatement ps = con.prepareStatement(dquery);
			ps.setInt(1, cls.getStatus());
			ps.setInt(2, cls.getId());

			ps.executeUpdate();

			response = "Data deleted succesfully";

		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// select all data
	public List<Cleaners> selectData() {
		List<Cleaners> clsList = new ArrayList<>();
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			// select if status is 1
			String squery = "select * from cleaner where status=1";
			// System.out.println("Query: " + squery);

			ResultSet rs = stmt.executeQuery(squery);
			while (rs.next()) {
				Cleaners cls = new Cleaners();
				cls.setId(rs.getInt("id"));
				cls.setFname(rs.getString("fname"));
				cls.setLname(rs.getString("lname"));
				cls.setCurAdd(rs.getString("curAdd"));
				cls.setPerAdd(rs.getString("perAdd"));
				cls.setState(rs.getString("state"));
				cls.setCity(rs.getString("city"));
				cls.setMobile(rs.getInt("mobile"));
				cls.setPhone(rs.getInt("phone"));
				cls.setPanNo(rs.getInt("panNo"));
				cls.setAdhaarNo(rs.getInt("adhaarNo"));
				cls.setPhoto(rs.getString("photo"));
				cls.setStatus(rs.getInt("status"));

				clsList.add(cls);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return clsList;
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
