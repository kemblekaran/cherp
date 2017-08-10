package com.cherp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.City;
import com.cherp.entities.User;

public class CityDataManager {
	private DBHandler handler;
	private Connection con;
	private String response = "";
	private boolean hasRecord;
	PreparedStatement ps;

	// insert data into database
	public String addData(City city) {

		try {

			// set status 1 for user
			city.setStatus(1);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();
			String query = "insert into city(cityName,stateName,status)values(?,?,?)";
//			String squery = "select * from city where id=?";

			// check if record already exists or not
//			 boolean hasRecord = hasData(stmt, squery);
//			 if (hasRecord) {
				 
				 ps = con.prepareStatement(query);
					ps.setString(1, city.getCityName());
					ps.setString(2, city.getStateName());
					ps.setInt(3, city.getStatus());
					ps.executeUpdate();
					
					response = "Data Added Successfully";
//			 }

		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// Updates the database
	public String updateData(City city) {
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			// Selects All the Data from the Database in squery variable
			String squery = "select * from city where id='" + city.getId() + "'";

			// Update the entire record in the database
			String uquery = "update city set cityName=?, stateName=? where id=?" ;

			// Fires the update Query to the database
			ps = con.prepareStatement(uquery);
			
			ps.setString(1, city.getCityName());
			ps.setString(2, city.getStateName());
			ps.setInt(3, city.getId());
			
			ps.executeUpdate();
			response = "Data added successfully";
			
			
		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// delete data from datatables (changes the status in db to 0 for
	// invisibility in datatables)
	public String deleteData(City city) {
		try {

			// set status 1 for city
			city.setStatus(0);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			String dquery = "update area set status=? where id=?";

			ps = con.prepareStatement(dquery);
			
			ps.setInt(1, city.getStatus());
			ps.setInt(2, city.getId());
			
			ps.executeUpdate();
			
			response = "Data deleted succesfully";
			
		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// select all data
	public List<City> selectData() {
		List<City> cityList = new ArrayList<>();
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			// select if status is 1
			String squery = "select * from city where status=1";
			// System.out.println("Query: " + squery);

			ResultSet rs = stmt.executeQuery(squery);
			while (rs.next()) {
				City city = new City();
				city.setId(rs.getInt("id"));
				city.setCityName(rs.getString("cityName"));
				city.setStateName(rs.getString("stateName"));
				cityList.add(city);
				System.out.println(city.getCityName());
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return cityList;
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
