package com.cherp.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHandler {
	private static DBHandler instance;
	private Connection con;

	private DBHandler() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/forms", "root", "coderkandy");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static DBHandler getInstance() {
		if (instance == null) {
			instance = new DBHandler();
		}

		return instance;
	}

	public Connection getConnection() {
		return con;
	}
}
