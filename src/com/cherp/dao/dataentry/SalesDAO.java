package com.cherp.dao.dataentry;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
	
	public int getInvoiceNo() {

		createSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);

		Root<Sales> rootPurchase = criteriaQuery.from(Sales.class);

		criteriaQuery.select(criteriaBuilder.max(rootPurchase.get("invoiceNo")));

		int invoiceNo = session.createQuery(criteriaQuery).getSingleResult();

		System.out.println("invoiceNo:" + invoiceNo);

		return invoiceNo;
	}

	@SuppressWarnings("unchecked")
	public List<Purchase> selectSales(String date, String van) {

		createSession();
		String dbQuery = "from Purchase where date='" + date + "' AND vanName='" + van + "'";
		Query query = session.createQuery(dbQuery);

		List<Purchase> salesData = new ArrayList<>();
		List<Purchase> salesTableList = new ArrayList<>();

		salesData = query.getResultList();
		
		for (Purchase pur : salesData) {
			Purchase purchase = new Purchase();
			purchase.setPurchaseId((pur.getPurchaseId()));
			purchase.setDate(date);
			purchase.setVanName(van);
			purchase.setDriver1(pur.getDriver1());
			purchase.setDriver2(pur.getDriver2());
			purchase.setCleaner1(pur.getCleaner1());
			purchase.setCleaner2(pur.getCleaner2());
			purchase.setCompany(pur.getCompany());
			purchase.setLocation(pur.getLocation());
			purchase.setOutstanding(pur.getOutstanding());
			purchase.setChallanNo(pur.getChallanNo());
			purchase.setRent(pur.getRent());
			purchase.setProduct(pur.getProduct());
			purchase.setPieces(pur.getPieces());
			purchase.setKg(pur.getKg());
			purchase.setRate(pur.getRate());
			purchase.setAmount(pur.getAmount());
			purchase.setAvgWeight(pur.getAvgWeight());
			purchase.setInvoiceNo(new SalesDAO().getInvoiceNo());
			salesTableList.add(purchase);
		}

		return salesTableList;

	}

}
