package com.cherp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Product;

public class ProductDataManager {
	private DBHandler handler;
	private Connection con;
	private String response = "";
	PreparedStatement ps;

	// insert data into database
	public String addData(Product prod) {

		try {

			// set status 1 for area
			prod.setStatus(1);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			String query = "insert into product(prodName,prodType,status)values( ?,?,?)";

			ps = con.prepareStatement(query);

			ps.setString(1, prod.getProd_name());
			ps.setString(2, prod.getProd_type());
			ps.setInt(3, prod.getStatus());

			ps.executeUpdate();

			response = "data added successfully";
			// System.out.println(squery);
			// check if record already exists or not
			// boolean hasRecord = hasData(stmt, squery);
			// if (hasRecord) {
			// stmt.execute(query);
			// }

		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// Updates the database
	public String updateData(Product prod) {
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			String uquery = "update product set prodName=? ,prodType=?  where id=?";

			ps = con.prepareStatement(uquery);

			ps.setString(1, prod.getProd_name());
			ps.setString(2, prod.getProd_type());
			ps.setInt(3, prod.getId());

			ps.executeUpdate();

			response = "Data Updated successfully";

		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// delete data from datatables (changes the status in db to 0 for invisibility in datatables)
	public String deleteData(Product prod) {
		try {

			// set status 1 for area
			prod.setStatus(0);
			handler = DBHandler.getInstance();
			con = handler.getConnection();

			String dquery = "update product set status=? where id=?";

			// stmt.executeUpdate(dquery);
			ps = con.prepareStatement(dquery);

			ps.setInt(1, prod.getStatus());
			ps.setInt(2, prod.getId());

			ps.executeUpdate();
			response = "Data deleted succesfully";
		} catch (Exception e) {
			e.printStackTrace();

		}
		return response;
	}

	// select all data
	public List<Product> selectData() {
		List<Product> prodList = new ArrayList<>();
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			// select if status is 1
			String squery = "select * from product where status=1";

			ResultSet rs = stmt.executeQuery(squery);
			while (rs.next()) {
				Product prod = new Product();
				prod.setId(rs.getInt("id"));
				prod.setProd_name(rs.getString("prodName"));
				prod.setProd_type(rs.getString("prodType"));

				prodList.add(prod);

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return prodList;
	}

}
