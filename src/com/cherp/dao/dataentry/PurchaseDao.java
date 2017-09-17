package com.cherp.dao.dataentry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cherp.entities.Cleaners;
import com.cherp.entities.Company;
import com.cherp.entities.Drivers;
import com.cherp.entities.Location;
import com.cherp.entities.Product;
import com.cherp.entities.Purchase;
import com.cherp.entities.Van;
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

		Root<Purchase> rootPurchase = criteriaQuery.from(Purchase.class);

		criteriaQuery.where(criteriaBuilder.equal(rootPurchase.get("status"), 1));

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

	@SuppressWarnings("unchecked")
	public  Map<String, ArrayList<String>> formDataGenerator() {
		Map<String, ArrayList<String>> formGeneratorMap = new HashMap<>();
		createSession();

		String vanQuery = "select companyName from Van where status=1";
		String driverQuery = "select fname from Drivers where status=1";
		String cleanerQuery = "select fname from Cleaners where status=1";
		String companyQuery = "select name from Company where status=1";
		String locationQuery = "select location from Location where status=1";
		String productQuery = "select prodName from Product where status=1";
		
		String tableName = "";
		Table table = null;
		
		//add van list to hashmap
		List<String> vanList = session.createQuery(vanQuery).getResultList();
		table = Van.class.getAnnotation(Table.class);
		tableName = table.name();
		formGeneratorMap.put(tableName, (ArrayList<String>) vanList);
		
		//add drivers list to hashmap
		List<String> driverList = session.createQuery(driverQuery).getResultList();
		table = Drivers.class.getAnnotation(Table.class);
		tableName = table.name();
		formGeneratorMap.put(tableName, (ArrayList<String>) driverList);
		
		//add cleaners list to hashmap
		List<String> cleanerList = session.createQuery(cleanerQuery).getResultList();
		table = Cleaners.class.getAnnotation(Table.class);
		tableName = table.name();
		formGeneratorMap.put(tableName, (ArrayList<String>) cleanerList);
		
		//add company list to hashmap
		List<String> companyList = session.createQuery(companyQuery).getResultList();
		table = Company.class.getAnnotation(Table.class);
		tableName = table.name();
		formGeneratorMap.put(tableName, (ArrayList<String>) companyList);
		
		//add location list to hashmap
		List<String> locationList = session.createQuery(locationQuery).getResultList();
		table = Location.class.getAnnotation(Table.class);
		tableName = table.name();
		formGeneratorMap.put(tableName, (ArrayList<String>) locationList);
		
		//add product list to hashmap
		List<String> productList = session.createQuery(productQuery).getResultList();
		table = Product.class.getAnnotation(Table.class);
		tableName = table.name();
		formGeneratorMap.put(tableName, (ArrayList<String>) productList);
		
		
		
		

		return  formGeneratorMap;
	}
}