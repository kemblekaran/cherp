package com.cherp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Expenses;
import com.cherp.entities.Location;

public class LocationDataManager {
	private DBHandler handler;
	private Connection con;
	private String response = "";
	private boolean hasRecord;

	// insert data into database
	public String addData(Location loc) {

		try {

			String query = "insert into location(loc,status) values(?,?)";

			// set status 1 for area
			loc.setStatus(1);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			PreparedStatement ps = con.prepareStatement(query);

			System.out.println(query);
			// check if record already exists or not
			// boolean hasRecord = hasData(stmt, squery);
			// if (hasRecord) {
			ps.setString(1, loc.getLocation());
			ps.setInt(2, loc.getStatus());
			ps.executeUpdate();
			// }
			response = "Data added successfully!";
		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// Updates the database
	public String updateData(Location loc) {
		try {

			String uquery = "update location set loc=? where id=?";

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			System.out.println("U:" + uquery);
			// check if record already exists or not
			// hasRecord = hasData(stmt, squery);

			PreparedStatement ps = con.prepareStatement(uquery);
			ps.setString(1, loc.getLocation());
			ps.setInt(2, loc.getId());

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
	public String deleteData(Location loc) {
		try {

			String dquery = "update location set status=? where id=?";

			// set status 1 for area
			loc.setStatus(0);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			PreparedStatement ps = con.prepareStatement(dquery);
			ps.setInt(1, loc.getStatus());
			ps.setInt(2, loc.getId());

			ps.executeUpdate();

			response = "Data deleted succesfully";

		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// select all data
	public List<Location> selectData() {
		List<Location> locList = new ArrayList<>();
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			// select if status is 1
			String squery = "select * from location where status=1";
			// System.out.println("Query: " + squery);

			ResultSet rs = stmt.executeQuery(squery);
			while (rs.next()) {
				Location loc = new Location();
				loc.setId(rs.getInt("id"));
				loc.setLocation(rs.getString("loc"));

				locList.add(loc);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return locList;
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
