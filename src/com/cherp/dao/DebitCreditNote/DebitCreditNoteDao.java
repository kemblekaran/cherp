package com.cherp.dao.DebitCreditNote;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cherp.entities.DebitCredit;
import com.cherp.entities.Purchase;
import com.cherp.entities.Sales;
import com.cherp.utils.HibernateUtil;

public class DebitCreditNoteDao {

	HibernateUtil hbutil = null;
	SessionFactory factory = null;
	Session session = null;

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

	//select max note number
	public int getNoteNumber() {

		createSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);

		Root<DebitCredit> rootDebitCredit = criteriaQuery.from(DebitCredit.class);

		// criteriaQuery.select(criteriaBuilder.max(rootPurchase.get("invoiceNo")));
		criteriaQuery.select(criteriaBuilder.coalesce(criteriaBuilder.max(rootDebitCredit.get("noteNo")), 0));

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
