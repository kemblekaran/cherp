package com.cherp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Company;
import com.cherp.entities.Product;

public class CompanyDataManager {

	private DBHandler handler;
	private Connection con;
	private String response = "";
	private boolean hasRecord;
	PreparedStatement ps;

	// insert data into database
	public String addData(Company comp) {

		try {

			// set status 1 for area
			comp.setStatus(1);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();
			String query = "insert into company(name,preAdd,secAdd,mobile,phone,state,city,pinCode,ownName,panNo,opBal,status)values( ?,?,?,?,?,?,?,?,?,?,?,?)";

			// String squery = "select * from product where prodName='" +
			// prod.getProd_name() + "'";
			ps = con.prepareStatement(query);

			ps.setString(1, comp.getName());
			ps.setString(2, comp.getPreAdd());
			ps.setString(3, comp.getSecAdd());
			ps.setLong(4, comp.getMobile());
			ps.setLong(5, comp.getPhone());
			ps.setString(6, comp.getState());
			ps.setString(7, comp.getCity());
			ps.setInt(8, comp.getPinCode());
			ps.setString(9, comp.getOwnName());
			ps.setString(10, comp.getPanNo());
			ps.setInt(11, comp.getOpBal());
			ps.setInt(12, comp.getStatus());

			// System.out.println(query);
			// check if record already exists or not
			// boolean hasRecord = hasData(stmt, squery);
			// if (hasRecord) {
			// stmt.execute(query);
			ps.executeUpdate();
			response = "Data added successfully!";
			// }

		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// Updates the database
	public String updateData(Company comp) {
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			String uquery = "update company set name=?,preAdd=? ,secAdd=? ,mobile=?,phone=?,state=? ,city=? ,pinCode=? ,ownName=? ,panNo=? ,opBal=? where id=?";

			String squery = "select * from company where name='" + comp.getName() + "'";
			// String squery = "select * from company where prodName='" +
			// prod.getProd_name() + "'";

			System.out.println("U:" + uquery);
			// check if record already exists or not
			// hasRecord = hasData(stmt, squery);

			// if (hasRecord) {

			ps = con.prepareStatement(uquery);

			ps.setString(1, comp.getName());
			ps.setString(2, comp.getPreAdd());
			ps.setString(3, comp.getSecAdd());
			ps.setLong(4, comp.getMobile());
			ps.setLong(5, comp.getPhone());
			ps.setString(6, comp.getState());
			ps.setString(7, comp.getCity());
			ps.setInt(8, comp.getPinCode());
			ps.setString(9, comp.getOwnName());
			ps.setString(10, comp.getPanNo());
			ps.setInt(11, comp.getOpBal());
			ps.setInt(12, comp.getId());
			ps.executeUpdate();

			// stmt.executeUpdate(uquery);
			// }

		} catch (Exception e) {
			e.printStackTrace();

		}
		return "Data addedd successfully!";
	}

	// delete data from datatables (changes the status in db to 0 for
	// invisibility
	// in datatables)
	public String deleteData(Company comp) {
		try {

			// set status 1 for area
			comp.setStatus(0);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			String dquery = "update company set status=? where id=?";

			ps = con.prepareStatement(dquery);

			ps.setInt(1, comp.getStatus());
			ps.setInt(2, comp.getId());
			;

			ps.executeUpdate();
			// stmt.executeUpdate(dquery);
			response = "Data deleted successfully";
		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// select all data
	public List<Company> selectData() {
		List<Company> compList = new ArrayList<>();
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			// select if status is 1
			String squery = "select * from company where status=1";
			// System.out.println("Query: " + squery);

			ResultSet rs = stmt.executeQuery(squery);
			while (rs.next()) {
				Company comp = new Company();
				comp.setId(rs.getInt("id"));
				comp.setName(rs.getString("name"));
				comp.setPreAdd(rs.getString("preAdd"));
				comp.setSecAdd(rs.getString("secAdd"));
				comp.setMobile(rs.getLong("mobile"));
				comp.setPhone(rs.getLong("phone"));
				comp.setState(rs.getString("state"));
				comp.setCity(rs.getString("city"));
				comp.setPinCode(rs.getInt("pinCode"));
				comp.setOwnName(rs.getString("ownName"));
				comp.setPanNo(rs.getString("panNo"));
				comp.setOpBal(rs.getInt("opBal"));

				compList.add(comp);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return compList;
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
