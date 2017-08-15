package com.cherp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Van;

public class VanDataManager {

	private DBHandler handler;
	PreparedStatement ps;
	private Connection con;
	private String response = "";
	private boolean hasRecord;

	// insert data into database
	public String addData(Van van) {

		try {

			// set status 1 for area
			van.setStatus(1);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();
			String query = "insert into van(vanNumber,companyName,vanModel,ownerName,fitness,vanCapacity,insuranceNo,insStartDate,insEndDate,permitNo,permitStartDate,permitEndDate,status)values( ?,?,?,?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, van.getVanNumber());
			ps.setString(2, van.getCompanyName());
			ps.setString(3, van.getVanModel());
			ps.setString(4, van.getOwnerName());
			ps.setInt(5, van.getFitness());
			ps.setInt(6, van.getVanCapacity());
			ps.setInt(7, van.getInsuranceNo());
			ps.setString(8, van.getInsStartDate());
			ps.setString(9, van.getInsEndDate());
			ps.setInt(10, van.getPermitNo());
			ps.setString(11, van.getPermitStartDate());
			ps.setString(12, van.getPermitEndDate());
			ps.setInt(13, van.getStatus());

			ps.executeUpdate();
			response = "Data added successfully!";

		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// Updates the database
	public String updateData(Van van) {
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			String uquery = "update van set vanNumber=?,companyName=?,vanModel=?,ownerName=?,fitness=?,vanCapacity=?,insuranceNo=?,insStartDate=?,insEndDate=?,permitNo=?,permitStartDate=?,permitEndDate=?,status=? where id=?";
			ps = con.prepareStatement(uquery);

			ps.setString(1, van.getVanNumber());
			ps.setString(2, van.getCompanyName());
			ps.setString(3, van.getVanModel());
			ps.setString(4, van.getOwnerName());
			ps.setInt(5, van.getFitness());
			ps.setInt(6, van.getVanCapacity());
			ps.setInt(7, van.getInsuranceNo());
			ps.setString(8, van.getInsStartDate());
			ps.setString(9, van.getInsEndDate());
			ps.setInt(10, van.getPermitNo());
			ps.setString(11, van.getPermitStartDate());
			ps.setString(12, van.getPermitEndDate());
			ps.setInt(13, van.getStatus());
			ps.setInt(14, van.getId());

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		}
		return "Data Updated successfully!";
	}

	// delete data from datatables (changes the status in db to 0 for
	// invisibility
	// in datatables)
	public String deleteData(Van van) {
		try {
			// set status 1 for area
			van.setStatus(0);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			String dquery = "update van set status=? where id=?";

			ps = con.prepareStatement(dquery);
			ps.setInt(1, van.getStatus());
			ps.setInt(2, van.getId());

			ps.executeUpdate();

			response = "Data deleted successfully";
		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// select all data
	public List<Van> selectData() {
		List<Van> vanList = new ArrayList<>();
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			// select if status is 1
			String squery = "select * from van where status=1";
			// System.out.println("Query: " + squery);

			ResultSet rs = stmt.executeQuery(squery);
			while (rs.next()) {
				Van van = new Van();
				van.setId(rs.getInt("id"));
				van.setVanNumber(rs.getString("vanNumber"));
				van.setCompanyName(rs.getString("companyName"));
				van.setVanModel(rs.getString("vanModel"));
				van.setOwnerName(rs.getString("ownerName"));
				van.setFitness(rs.getInt("fitness"));
				van.setVanCapacity(rs.getInt("vanCapacity"));
				van.setInsuranceNo(rs.getInt("insuranceNo"));
				van.setInsStartDate(rs.getString("insStartDate"));
				van.setInsEndDate(rs.getString("insEndDate"));
				van.setPermitNo(rs.getInt("permitNo"));
				van.setPermitStartDate(rs.getString("permitStartDate"));
				van.setPermitEndDate(rs.getString("permitEndDate"));

				vanList.add(van);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return vanList;
	}
}
