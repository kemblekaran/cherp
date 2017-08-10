package com.cherp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.User;

public class UserDataManager {
	private DBHandler handler;
	private Connection con;
	private String response = "";
	private boolean hasRecord;
	PreparedStatement ps;
	// insert data into database
	public String addData(User user) {

		try {

			// set status 1 for user
			user.setStatus(1);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();
			String query = "insert into user(name,pwd,status) values( ?,?,?)";
//			String squery = "select * from user where name='" + user.getUsername() + "'";

			ps = con.prepareStatement(query);
			
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getStatus());
			
			ps.executeUpdate();
			
			response = "Data Added Succefully";
			
			// check if record already exists or not
//			boolean hasRecord = hasData(stmt, squery);
//			if (hasRecord) {
//				stmt.execute(query);
//			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// Updates the database
	public String updateData(User user) {
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			String uquery = "update user set name = ? where id = ?";
			String squery = "select * from user where name='" + user.getUsername() + "'";

			
			// check if record already exists or not
			hasRecord = hasData(stmt, squery);
			if (hasRecord) {
				System.out.println("updates started!");
				PreparedStatement pst = con.prepareStatement(uquery);
				pst.setString(1, user.getUsername());
				pst.setInt(2, user.getId());
				pst.executeUpdate();
				System.out.println("Update Finished!");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// delete data from datatables (changes the status in db to 0 for invisibility
	// in datatables)
	public String deleteData(User user) {
		try {

			// set status 1 for user
			user.setStatus(0);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			String dquery = "update user set status=? where id=?";

			ps = con.prepareStatement(dquery);

			ps.setInt(1, user.getStatus());
			ps.setInt(2, user.getId());

			ps.executeUpdate();
			response = "Data deleted succesfully";
//			stmt.executeUpdate(dquery);
			response = "Data deleted succesfully";
		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// select all data
	public List<User> selectData() {
		List<User> userList = new ArrayList<>();
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			// select if status is 1
			String squery = "select * from user where status=1";
			// System.out.println("Query: " + squery);

			ResultSet rs = stmt.executeQuery(squery);
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("name"));
				user.setPassword(rs.getString("pwd"));
				userList.add(user);

			}

			// for(User u : userList) {
			// System.out.println("Uname:"+u.getUsername()+", Pass:"+u.getPassword());
			// }
		} catch (Exception e) {
			e.printStackTrace();

		}
		return userList;
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
