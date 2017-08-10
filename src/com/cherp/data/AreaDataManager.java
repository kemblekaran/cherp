package com.cherp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Area;

public class AreaDataManager {
	private DBHandler handler;
	private Connection con;
	private String response = "";
	private boolean hasRecord;
	PreparedStatement ps;

	// insert data into database
	public String addData(Area area) {

		try {

			// set status 1 for area
			area.setStatus(1);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();
			String query = "insert into area(code,state,city,name,type,status)values(?,?,?,?,?,?)";

			String squery = "select * from area where id=?";
			
			System.out.println(query);
			// check if record already exists or not
//			boolean hasRecord = hasData(stmt, squery);
//			if (hasRecord) {
				ps = con.prepareStatement(query);
				ps.setInt(1, area.getCode());
				ps.setString(2, area.getState());
				ps.setString(3, area.getCity());
				ps.setString(4, area.getName());
				ps.setString(5, area.getType());
				ps.setInt(6, area.getStatus());
				
				ps.executeUpdate();
				
				response = "Data Added Successfully";
//			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// Updates the database
	public String updateData(Area area) {
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			String uquery = "update area set code=?,state=?,city=?,name=?,type=? where id=?";

			String squery = "select * from area where id="+ area.getId();

			// check if record already exists or not
			
			ResultSet rs;
//			if (hasRecord) {
				ps = con.prepareStatement(uquery);
				
				ps.setInt(1, area.getCode());
				ps.setString(2, area.getState());
				ps.setString(3, area.getCity());
				ps.setString(4, area.getName());
				ps.setString(5, area.getType());
				ps.setInt(6, area.getId());
				
				ps.executeUpdate();
				response = "Data added successfully";
//			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// delete data from datatables (changes the status in db to 0 for invisibility
	// in datatables)
	public String deleteData(Area area) {
		try {

			// set status 1 for area
			area.setStatus(0);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			String dquery = "update area set status=? where id=?";

			ps = con.prepareStatement(dquery);
			
			ps.setInt(1, area.getStatus());
			ps.setInt(2, area.getId());
			
			ps.executeUpdate();
			
			response = "Data deleted succesfully";
		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// select all data
	public List<Area> selectData() {
		List<Area> areaList = new ArrayList<>();
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			// select if status is 1
			String squery = "select * from area where status=1";
			// System.out.println("Query: " + squery);

			ResultSet rs = stmt.executeQuery(squery);
			while (rs.next()) {
				Area area = new Area();
				area.setId(rs.getInt("id"));
				area.setCode(rs.getInt("code"));
				area.setState(rs.getString("state"));
				area.setCity(rs.getString("city"));
				area.setName(rs.getString("name"));
				area.setType(rs.getString("type"));
				areaList.add(area);

			}

			// for(area u : areaList) {
			// System.out.println("Uname:"+u.getareaname()+", Pass:"+u.getPassword());
			// }
		} catch (Exception e) {
			e.printStackTrace();

		}
		return areaList;
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
