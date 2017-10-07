package com.cherp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Bank;

public class BankDataManager {
	private DBHandler handler;
	private Connection con;
	private String response = "";
	private boolean hasRecord;
	PreparedStatement ps;

	// insert data into database
	public String addData(Bank bank) {

		try {

			// set status 1 for user
			bank.setStatus(1);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			String query = "insert into bank(bankName,branchName,accType,accNo,ifscCode,opBal,address,status) values(?,?,?,?,?,?,?,?)";

			ps = con.prepareStatement(query);

			ps.setString(1, bank.getBankName());
			ps.setString(2, bank.getBranchName());
			ps.setString(3, bank.getAccType());
			ps.setLong(4, bank.getAccNo());
			ps.setString(5, bank.getIfscCode());
			ps.setLong(6, bank.getOpBal());
			ps.setString(7, bank.getAddress());
			ps.setInt(8, bank.getStatus());

			ps.executeUpdate();
			response = "Data Added successfully";

		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// Updates the database
	public String updateData(Bank bank) {
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();


			String uquery = "update bank set bankName=?,branchName=?,accType=?,accNo=?,ifscCode=?,opBal=?,address=? where id = ?";
			String squery = "select * from bank where bankName='" + bank.getBankName() + "'";

			// check if record already exists or not
			// hasRecord = hasData(stmt, squery);
			// if (hasRecord) {
			System.out.println("updates started!");

			PreparedStatement ps = con.prepareStatement(uquery);

			ps.setString(1, bank.getBankName());
			ps.setString(2, bank.getBranchName());
			ps.setString(3, bank.getAccType());
			ps.setLong(4, bank.getAccNo());
			ps.setString(5, bank.getIfscCode());
			ps.setLong(6, bank.getOpBal());
			ps.setString(7, bank.getAddress());
			ps.setInt(8, bank.getId());

			ps.executeUpdate();
			response = "Data Updated successfully";
			System.out.println("Update Finished!");
			// }

		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// delete data from datatables (changes the status in db to 0 for invisibility
	// in datatables)
	public String deleteData(Bank bank) {
		try {

			// set status 1 for user
			bank.setStatus(0);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			String dquery = "update bank set status=? where id=?";

			ps = con.prepareStatement(dquery);

			ps.setInt(1, bank.getStatus());
			ps.setInt(2, bank.getId());

			ps.executeUpdate();
			response = "Data deleted succesfully";
		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// select all data
	public List<Bank> selectData() {
		List<Bank> bankList = new ArrayList<>();
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			// select if status is 1
			String squery = "select * from bank where status=1";
			// System.out.println("Query: " + squery);

			ResultSet rs = stmt.executeQuery(squery);
			while (rs.next()) {
				Bank bank = new Bank();
				bank.setId(rs.getInt("id"));
				bank.setBankName(rs.getString("bankName"));
				bank.setBranchName(rs.getString("branchName"));
				bank.setAccType(rs.getString("accType"));
				bank.setAccNo(rs.getLong("accNo"));
				bank.setIfscCode(rs.getString("ifscCode"));
				bank.setOpBal(rs.getInt("opBal"));
				bank.setAddress(rs.getString("address"));

				bankList.add(bank);

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return bankList;
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
