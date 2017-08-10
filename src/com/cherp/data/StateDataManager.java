package com.cherp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.State;
import com.cherp.entities.User;

public class StateDataManager {

	private DBHandler handler;
	private Connection con;
	private String response = "";
	PreparedStatement pst;
	// insert data into database
	public String addData(State state) {

		try {

			// set status 1 for user
			state.setStatus(1);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();
			String query = "insert into state(stateName,status) values(?,?)";
					
			String squery = "select * from state where stateName='" + state.getStateName() + "'";

			pst = con.prepareStatement(query);
			pst.setString(1, state.getStateName());
			pst.setInt(2, state.getStatus());
			pst.executeUpdate();
			System.out.println("state added suuccessfully!");
			response = "Data Added Successfully!";

		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// Updates the database
	public String updateData(State state) {
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			String uquery = "update state set stateName=? where id=?";
//			String squery = "select * from state where stateName='" + state.getStateName() + "'";
			pst = con.prepareStatement(uquery);
			pst.setString(1, state.getStateName());
			pst.setInt(2, state.getId());
			pst.executeUpdate();
			
//				stmt.executeUpdate(uquery);
				response = "Data Updated Successfully!";

		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// delete data from datatables (changes the status in db to 0 for invisibility
	// in datatables)
	public String deleteData(State state) {
		try {

			// set status 1 for user
			state.setStatus(0);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			String dquery = "update state set status=? where id=?";

			pst = con.prepareStatement(dquery);

			pst.setInt(1, state.getStatus());
			pst.setInt(2, state.getId());

			pst.executeUpdate();
//			stmt.executeUpdate(dquery);
			response = "Data deleted succesfully";
		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// select all data
	public List<State> selectData() {
		List<State> stateList = new ArrayList<>();
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			// select if status is 1
			String squery = "select * from state where status=1";
			// System.out.println("Query: " + squery);

			ResultSet rs = stmt.executeQuery(squery);
			while (rs.next()) {
				State state = new State();
				state.setId(rs.getInt("id"));
				state.setStateName(rs.getString("stateName"));
				stateList.add(state);

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return stateList;
	}
}
