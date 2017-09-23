package com.cherp.dao.dataentry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.PayLoad;
import com.cherp.entities.Payment;
import com.cherp.entities.Purchase;
import com.cherp.utils.HibernateUtil;

public class PaymentDao {

	HibernateUtil hbutil = null;
	SessionFactory factory = null;
	Session session = null;

//	private DBHandler handler;
//	private Connection con;
//	private String response = "";
//	private Statement stmt;
//	PreparedStatement ps, pst, pps;

	public void createSession() {
		hbutil = HibernateUtil.getInstance();
		factory = hbutil.getSessionFactory();
		session = factory.openSession();
	}

	// inserting payment data into database
	public String insert(Payment payment) {
		createSession();

		Transaction t = session.beginTransaction();

		session.save(payment);

		t.commit();

		session.close();

		return "Insert Successful";
	}

	

//	public String updatePayLoad(Payment payment) {
//		try {
//
//			handler = DBHandler.getInstance();
//			con = handler.getConnection();
//			// Payment payment = new Payment();
//
//			// getting payload data
//			String plQuery = "select * from payload where company=?";
//			PayLoad payload = null;
//
//			pst = con.prepareStatement(plQuery);
//			pst.setString(1, payment.getCompany());
//			ResultSet rs = pst.executeQuery();
//			while (rs.next()) {
//				payload = new PayLoad();
//				payload.setBalanceAmount(rs.getInt("balanceAmount"));
//				payload.setId(rs.getInt("id"));
//				// System.out.println("balance...."+payload.getBalanceAmount());
//				// System.out.println("paynow.........."+payment.getPayNow());
//				// System.out.println(payload.getBalanceAmount() - payment.getPayNow() +"
//				// ....bal-pay");
//
//				if (payment.getPayNow() > payload.getBalanceAmount()) {
//					System.out.println("pay>bal");
//					payment.setPayNow(payment.getPayNow() - payload.getBalanceAmount());
//					payload.setBalanceAmount(0.0);
//					// System.out.println(payload.getBalanceAmount()+" <-balance will be zero");
//
//				} else {
//					payload.setBalanceAmount(payload.getBalanceAmount() - payment.getPayNow());
//
//					payment.setPayNow(0.0);
//					// System.out.println(payment.getPayNow()+"<- paynow will be zero");
//
//				}
//				String uquery = "update payload set balanceAmount=? where id=?";
//				ps = con.prepareStatement(uquery);
//				ps.setDouble(1, payload.getBalanceAmount());
//				ps.setInt(2, payload.getId());
//				ps.executeUpdate();
//			}
//			response = "data updated successfully";
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return response;
//
//	}
//
//	// delete payload details when balance will get zero.
//	public String deletePayLoad(PayLoad payload) {
//		try {
//
//			handler = DBHandler.getInstance();
//			con = handler.getConnection();
//			Payment payment = new Payment();
//
//			String uquery = "delete from payload  where balanceAmount=?";
//
//			System.out.println("in delete datamanager");
//			ps = con.prepareStatement(uquery);
//			ps.setInt(1, 0);
//
//			ps.executeUpdate();
//			response = "data updated successfully";
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return response;
//
//	}

	// select payment data
		public List<Payment> selectData() {

			createSession();

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

			CriteriaQuery<Payment> criteriaQuery = criteriaBuilder.createQuery(Payment.class);

			Root<Payment> rootPayment = criteriaQuery.from(Payment.class);

			criteriaQuery.where(criteriaBuilder.equal(rootPayment.get("status"), 1));

			List<Payment> paymentList = session.createQuery(criteriaQuery).getResultList();
			
			return paymentList;
		}
		
	// select payload data
	public List<PayLoad> selectPayData() {

		createSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<PayLoad> criteriaQuery = criteriaBuilder.createQuery(PayLoad.class);

		Root<PayLoad> rootPayLoad = criteriaQuery.from(PayLoad.class);

		criteriaQuery.where(criteriaBuilder.equal(rootPayLoad.get("status"), 1));

		List<PayLoad> payloadList = session.createQuery(criteriaQuery).getResultList();
		
		return payloadList;
	}

}
