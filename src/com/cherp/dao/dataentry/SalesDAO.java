package com.cherp.dao.dataentry;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cherp.entities.Purchase;
import com.cherp.entities.Sales;
import com.cherp.entities.SalesLoad;
import com.cherp.utils.HibernateUtil;

public class SalesDAO {
	HibernateUtil hbutil = null;
	SessionFactory factory = null;
	Session session = null;

	public void createSession() {
		hbutil = HibernateUtil.getInstance();
		factory = hbutil.getSessionFactory();
		session = factory.openSession();
	}

	public String insert(Sales sales) {
		createSession();

		Transaction t = session.beginTransaction();

		session.save(sales);

		t.commit();

		session.close();

		return "Insert Successful";
	}

	public String insertSales(SalesLoad salesload) {
		createSession();

		Transaction t = session.beginTransaction();

		session.save(salesload);

		t.commit();

		session.close();

		return "Insert Successful";
	}



	public String update(Purchase purchase) {

		createSession();
		Transaction t = session.beginTransaction();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaUpdate<Purchase> criteria = criteriaBuilder.createCriteriaUpdate(Purchase.class);

		Root<Purchase> root = criteria.from(Purchase.class);
		criteria.set(root.get("balancePieces"), purchase.getBalancePieces());
		criteria.set(root.get("balanceKG"), purchase.getBalanceKG());
		criteria.where(criteriaBuilder.equal(root.get("id"), purchase.getId()));
		session.createQuery(criteria).executeUpdate();

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

	public List<Purchase> selectAll( String van) {

		createSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);

		// specify criteria root
		Root<Purchase> rootSales = criteriaQuery.from(Purchase.class);
//		System.out.println("date in sales selectall " + date);
//		criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(rootSales.get("date"), date),
//				criteriaBuilder.equal(rootSales.get("vanName"), van)));

		criteriaQuery.where(criteriaBuilder.equal(rootSales.get("vanName"), van));
		
		List<Purchase> purchaseList = session.createQuery(criteriaQuery).getResultList();

		return purchaseList;
	}

	public int getInvoiceNo() {

		createSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);

		Root<Sales> rootSales = criteriaQuery.from(Sales.class);

		// criteriaQuery.select(criteriaBuilder.max(rootPurchase.get("invoiceNo")));
		criteriaQuery.select(criteriaBuilder.coalesce(criteriaBuilder.max(rootSales.get("invoiceNo")), 0));

		int invoiceNo = session.createQuery(criteriaQuery).getSingleResult();

		System.out.println("invoiceNo:" + invoiceNo);

		return invoiceNo;
	}

	public List<Sales> selectSales() {

		createSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Sales> criteriaQuery = criteriaBuilder.createQuery(Sales.class);

		Root<Sales> rootSales = criteriaQuery.from(Sales.class);

		criteriaQuery.where(criteriaBuilder.equal(rootSales.get("status"), 1));

		List<Sales> salesViewList = session.createQuery(criteriaQuery).getResultList();

		return salesViewList;

	}

}
