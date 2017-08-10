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
			String query = "insert into company(name,pre_add,sec_add,mobile,phone,state,city,pin_code,own_name,pan_no,op_bal,status)values( ?,?,?,?,?,?,?,?,?,?,?,?)";

			// String squery = "select * from product where prodName='" +
			// prod.getProd_name() + "'";
			ps = con.prepareStatement(query);
			
			ps.setString(1, comp.getName());
			ps.setString(2, comp.getPre_add());
			ps.setString(3, comp.getSec_add());
			ps.setInt(4, comp.getMobile());
			ps.setInt(5, comp.getPhone());
			ps.setString(6, comp.getState());
			ps.setString(7, comp.getCity());
			ps.setInt(8, comp.getPin_code());
			ps.setString(9, comp.getOwn_name());
			ps.setInt(10, comp.getPan_no());
			ps.setInt(11, comp.getOp_bal());
			ps.setInt(12, comp.getStatus());
			
//			System.out.println(query);
			// check if record already exists or not
			// boolean hasRecord = hasData(stmt, squery);
			// if (hasRecord) {
//			stmt.execute(query);
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

			String uquery = "update company set name=?,pre_add=? ,sec_add=? ,mobile=?,phone=? ,"
					+ "state=? ,city=? ,pin_code='? ,own_name=? ,pan_no=? ,op_bal=? where id=?";

			String squery = "select * from company where name='" + comp.getName() + "'";
			// String squery = "select * from company where prodName='" +
			// prod.getProd_name() + "'";

			System.out.println("U:" + uquery);
			// check if record already exists or not
			// hasRecord = hasData(stmt, squery);

			// if (hasRecord) {
			
			ps = con.prepareStatement(uquery);
			
			ps.setString(1, comp.getName());
			ps.setString(2, comp.getPre_add());
			ps.setString(3, comp.getSec_add());
			ps.setInt(4, comp.getMobile());
			ps.setInt(5, comp.getPhone());
			ps.setString(6, comp.getState());
			ps.setString(7, comp.getCity());
			ps.setInt(8, comp.getPin_code());
			ps.setString(9, comp.getOwn_name());
			ps.setInt(10, comp.getPan_no());
			ps.setInt(11, comp.getOp_bal());
			ps.setInt(12, comp.getId());
			ps.executeUpdate();
			
//			stmt.executeUpdate(uquery);
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

			String dquery = "update company set status=? where id=?" ;
					
			ps = con.prepareStatement(dquery);

			ps.setInt(1, comp.getStatus());
			ps.setInt(2, comp.getId());;
			
			ps.executeUpdate();
//			stmt.executeUpdate(dquery);
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
				comp.setPre_add(rs.getString("pre_add"));
				comp.setSec_add(rs.getString("sec_add"));
				comp.setMobile(rs.getInt("mobile"));
				comp.setPhone(rs.getInt("phone"));
				comp.setState(rs.getString("state"));
				comp.setCity(rs.getString("city"));
				comp.setPin_code(rs.getInt("pin_code"));
				comp.setOwn_name(rs.getString("own_name"));
				comp.setPan_no(rs.getInt("pan_no"));
				comp.setOp_bal(rs.getInt("op_bal"));
				
				
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
