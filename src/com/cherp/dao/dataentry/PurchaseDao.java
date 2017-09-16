package com.cherp.dao.dataentry;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cherp.entities.Purchase;
import com.cherp.utils.HibernateUtil;

public class PurchaseDao {

	HibernateUtil hbutil = null;
	SessionFactory factory = null;
	Session session = null;

	public void createSession() {
		hbutil = HibernateUtil.getInstance();
		factory = hbutil.getSessionFactory();
		session = factory.openSession();
	}

	public String insert(Purchase purchase) {
		createSession();

		Transaction t = session.beginTransaction();

		session.save(purchase);

		t.commit();

		session.close();

		return "Insert Successful";
	}

	public String update(Purchase purchase) {

		createSession();

		Transaction t = session.beginTransaction();

		session.update(purchase);

		t.commit();

		session.close();

		return "Update Successful";
	}

	public String delete(Purchase purchase) {
		// TODO Auto-generated method stub

		createSession();

		Transaction t = session.beginTransaction();

		session.delete(purchase);

		t.commit();

		session.close();

		return "Delete Successful";
	}

	public List<Purchase> selectAll() {

		createSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);

		criteriaQuery.from(Purchase.class);

		List<Purchase> purchaseList = session.createQuery(criteriaQuery).getResultList();

		return purchaseList;
	}

	public int getPurchaseId() {

		createSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);

		Root<Purchase> rootPurchase = criteriaQuery.from(Purchase.class);

		criteriaQuery.select(criteriaBuilder.max(rootPurchase.get("purchaseId")));

		int purchaseId = session.createQuery(criteriaQuery).getSingleResult();

		System.out.println("PuchaseId:" + purchaseId);

		return purchaseId;
	}
}
