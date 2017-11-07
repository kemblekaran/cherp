package com.cherp.dao.DebitCreditNote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cherp.dbconnection.DBHandler;
import com.cherp.entities.Collection;
import com.cherp.entities.DebitCredit;
import com.cherp.entities.PayLoad;
import com.cherp.entities.Payment;
import com.cherp.entities.Sales;
import com.cherp.entities.SalesLoad;
import com.cherp.utils.HibernateUtil;

public class DebitCreditNoteDao {

	HibernateUtil hbutil = null;
	SessionFactory factory = null;
	Session session = null;

	private DBHandler handler;
	private Connection con;
	private String response = "";
	PreparedStatement ps,pst,pps;
	
	
	public void createSession() {
		hbutil = HibernateUtil.getInstance();
		factory = hbutil.getSessionFactory();
		session = factory.openSession();
	}
	
	
	public String insert(DebitCredit debitCredit) {
		createSession();

		Transaction t = session.beginTransaction();

		session.save(debitCredit);

		t.commit();

		session.close();

		return "Insert Successful";
	}

	//update payload
	
	public String updatePayLoad(DebitCredit debitcredit) {
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();
//			Payment payment = new Payment();
			
			//getting payload data
			String plQuery = "select * from payload where company=?";
			PayLoad payload = null;
			System.out.println("%%%%%%%debit Dao");
			pst = con.prepareStatement(plQuery);
			pst.setString(1, debitcredit.getSelectCustCmp());
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				payload = new PayLoad();
				payload.setBalanceAmount(rs.getInt("balanceAmount"));
				payload.setId(rs.getInt("id"));
//				System.out.println("balance...."+payload.getBalanceAmount());
//				System.out.println("paynow.........."+payment.getPayNow());
//				System.out.println(payload.getBalanceAmount() - payment.getPayNow() +" ....bal-pay");
			
				if(debitcredit.getAmount() > payload.getBalanceAmount()){
					System.out.println("pay>bal");
					debitcredit.setAmount(debitcredit.getAmount() - payload.getBalanceAmount());
					payload.setBalanceAmount(0.0);
//					System.out.println(payload.getBalanceAmount()+" <-balance will be zero");
					
					
					
				}else{
					payload.setBalanceAmount(payload.getBalanceAmount() - debitcredit.getAmount());
					
					debitcredit.setAmount(0.0);
//					System.out.println(payment.getPayNow()+"<- paynow will be zero");
					
					
				}
				
				System.out.println("In debitcredit note ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				
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
	
	public String updateSalesLoad(DebitCredit debitcredit) {
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();
//			collection collection = new collection();
			
			//getting salesload data
			String plQuery = "select * from salesload where customer=?";
			SalesLoad salesload = null;
			
			pst = con.prepareStatement(plQuery);
			pst.setString(1, debitcredit.getSelectCustCmp());
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				salesload = new SalesLoad();
				salesload.setBalanceAmount(rs.getInt("balanceAmount"));
				salesload.setId(rs.getInt("id"));
//				System.out.println("balance...."+salesload.getBalanceAmount());
//				System.out.println("paynow.........."+collection.getPayNow());
//				System.out.println(salesload.getBalanceAmount() - collection.getPayNow() +" ....bal-pay");
			
				if(debitcredit.getAmount() > salesload.getBalanceAmount()){
					System.out.println("pay>bal");
					debitcredit.setAmount(debitcredit.getAmount() - salesload.getBalanceAmount());
					salesload.setBalanceAmount(0.0);
//					System.out.println(salesload.getBalanceAmount()+" <-balance will be zero");
					
					
					
				}else{
					salesload.setBalanceAmount(salesload.getBalanceAmount() - debitcredit.getAmount());
					
					debitcredit.setAmount(0.0);
//					System.out.println(collection.getPayNow()+"<- paynow will be zero");
					
					
				}
				String uquery = "update salesload set balanceAmount=? where id=?";
				ps = con.prepareStatement(uquery);
				ps.setDouble(1, salesload.getBalanceAmount());
				ps.setInt(2, salesload.getId());
				ps.executeUpdate();
			}
			response = "data updated successfully";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}
	
	
	//delete salesload details when balance will get zero.
	public String deleteSalesLoad(SalesLoad salesload) {
		try {

			handler = DBHandler.getInstance();
			con = handler.getConnection();
			new Collection();

			String uquery = "delete from salesload  where balanceAmount=?";

			
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
	
	//select max note number
	public int getNoteNumber() {

		createSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);

		Root<DebitCredit> rootDebitCredit = criteriaQuery.from(DebitCredit.class);

		// criteriaQuery.select(criteriaBuilder.max(rootPurchase.get("invoiceNo")));
		criteriaQuery.select(criteriaBuilder.coalesce(criteriaBuilder.max(rootDebitCredit.get("id")), 0));

		int noteNo = session.createQuery(criteriaQuery).getSingleResult();

		System.out.println("noteNo:" + noteNo);

		return noteNo;
	}
	

		public List<DebitCredit> selectAll() {

		createSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<DebitCredit> criteriaQuery = criteriaBuilder.createQuery(DebitCredit.class);

		Root<DebitCredit> rootDebitCredit = criteriaQuery.from(DebitCredit.class);

		criteriaQuery.where(criteriaBuilder.equal(rootDebitCredit.get("status"), 1));

		List<DebitCredit> purchaseList = session.createQuery(criteriaQuery).getResultList();

		return purchaseList;
	}
}
