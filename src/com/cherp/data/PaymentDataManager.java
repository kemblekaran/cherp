package com.cherp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.PayLoad;
import com.cherp.entities.Payment;

public class PaymentDataManager {
	private DBHandler handler;
	private Connection con;
	private String response = "";
	PreparedStatement ps,pst,pps;

	//insert payment data
	public String addData(Payment payment) {

		try {
			handler = DBHandler.getInstance();
			con = handler.getConnection();
			payment.setStatus(1);
			
			String query = "insert into payment(paymentDate, company, paymentMode,name, chDate, chNo, toBePaid, payNow,"
					+ " closingBal,status)values(?,?,?,?,?,?,?,?,?,?)";

			ps = con.prepareStatement(query);
			ps.setString(1, payment.getPaymentDate());
			ps.setString(2, payment.getCompany());
			ps.setString(3, payment.getPaymentMode());
			ps.setString(4, payment.getName());
			ps.setString(5, payment.getChDate());
			ps.setLong(6, payment.getChNo());
			ps.setDouble(7, payment.getToBePaid());
			ps.setDouble(8, payment.getPayNow());
			ps.setDouble(9, payment.getClosingBal());
			ps.setInt(10, payment.getStatus());

			ps.executeUpdate();

			response = "data added successfully";

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	//update payload data
	public String updatePayLoad(Payment payment) {
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();
//			Payment payment = new Payment();
			
			//getting payload data
			String plQuery = "select * from payload where company=?";
			PayLoad payload = null;
			
			pst = con.prepareStatement(plQuery);
			pst.setString(1, payment.getCompany());
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				payload = new PayLoad();
				payload.setBalanceAmount(rs.getInt("balanceAmount"));
				payload.setId(rs.getInt("id"));
//				System.out.println("balance...."+payload.getBalanceAmount());
//				System.out.println("paynow.........."+payment.getPayNow());
//				System.out.println(payload.getBalanceAmount() - payment.getPayNow() +" ....bal-pay");
			
				if(payment.getPayNow() > payload.getBalanceAmount()){
					System.out.println("pay>bal");
					payment.setPayNow(payment.getPayNow() - payload.getBalanceAmount());
					payload.setBalanceAmount(0.0);
//					System.out.println(payload.getBalanceAmount()+" <-balance will be zero");
					
					
					
				}else{
					payload.setBalanceAmount(payload.getBalanceAmount() - payment.getPayNow());
					
					payment.setPayNow(0.0);
//					System.out.println(payment.getPayNow()+"<- paynow will be zero");
					
					
				}
				String uquery = "update payload set balanceAmount=? where id=?";
				ps = con.prepareStatement(uquery);
				ps.setDouble(1, payload.getBalanceAmount());
				ps.setInt(2, payload.getId());
				ps.executeUpdate();
			}
			response = "data updated successfully";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}
	
	
	
	//delete payload details when balance will get zero.
	public String deletePayLoad(PayLoad payload) {
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();
			new Payment();

			String uquery = "delete from payload  where balanceAmount=?";

			
			System.out.println("in delete datamanager");
			ps = con.prepareStatement(uquery);
			ps.setInt(1, 0);
			
			
			ps.executeUpdate();
			response = "data updated successfully";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}
	
	

	// select all data for payment 
	public List<Payment> selectData() {
		List<Payment> paymentList = new ArrayList<>();
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			String squery = "select * from payment where status=1";

			ResultSet rs = stmt.executeQuery(squery);
			
			while (rs.next()) {
//				System.out.println("payment resultset "+rs.toString());
				Payment payment = new Payment();
				payment.setId(rs.getInt("id"));
				payment.setPaymentDate(rs.getString("paymentDate"));
				payment.setCompany(rs.getString("company"));
				payment.setPaymentMode(rs.getString("paymentMode"));
				payment.setName(rs.getString("name"));
				payment.setChDate(rs.getString("chDate"));
				payment.setChNo(rs.getLong("chNo"));
				payment.setToBePaid(rs.getDouble("toBePaid"));
				payment.setPayNow(rs.getDouble("payNow"));
				payment.setClosingBal(rs.getDouble("closingBal"));
				paymentList.add(payment);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return paymentList;
	}
	
	
	
	//select payData to show in payment table
	public List<PayLoad> selectPayData() {
		
		List<PayLoad> payloadList = new ArrayList<>();
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();

			Statement stmt = con.createStatement();

			// select if status is 1
			String squery = "select * from payload where status=1";
			// System.out.println("Query: " + squery);

			ResultSet rs = stmt.executeQuery(squery);
			while (rs.next()) {
				PayLoad payload = new PayLoad();
				
				payload.setId(rs.getInt("id"));
				payload.setPurchaseId(rs.getInt("purchaseId"));
				payload.setDate(rs.getString("date"));
				payload.setCompany(rs.getString("company"));
				payload.setFinalAmount(rs.getInt("finalAmount"));
				payload.setBalanceAmount(rs.getInt("balanceAmount"));

				payloadList.add(payload);

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return payloadList;
	}

}
