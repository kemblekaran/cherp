package com.cherp.dao.masters;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cherp.entities.Customer;
import com.cherp.utils.HibernateUtil;

public class CustomerDao extends MasterBaseDao{

	HibernateUtil hbutil = null;
	SessionFactory factory = null;
	Session session = null;

	@Override
	public void createSession() {
		// TODO Auto-generated method stub
		hbutil = HibernateUtil.getInstance();
		factory = hbutil.getSessionFactory();
		session = factory.openSession();
	}

	@Override
	public String insert(Object obj) {
		// TODO Auto-generated method stub
		Customer Customer = (Customer) obj;
		createSession();

		Transaction t = session.beginTransaction();

		session.save(Customer);

		t.commit();

		session.close();

		return "Insert Successful";
	}

	@Override
	public String update(Object obj) {
		// TODO Auto-generated method stub
		Customer Customer = (Customer) obj;
		createSession();

		Transaction t = session.beginTransaction();



		session.update(Customer);

		t.commit();

		session.close();

		return "Update Successful";
	}

	@Override
	public String delete(Object obj) {
		// TODO Auto-generated method stub
		Customer Customer = (Customer) obj;
		createSession();

		Transaction t = session.beginTransaction();


		session.delete(Customer);

		t.commit();

		session.close();
		
		return "Delete Successful";
	}

	@Override
	public List<Customer> selectAll() {
		// TODO Auto-generated method stub
		
		createSession();
		
		//create criteria builder
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		
		//create criteria query
		CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
		
		//specify criteria root
		Root<Customer> rootCustomer = criteriaQuery.from(Customer.class);

		criteriaQuery.where(criteriaBuilder.equal(rootCustomer.get("status"), 1));
		
		//Execute query
		List<Customer> CustomerList = session.createQuery(criteriaQuery).getResultList();
		
		//close session
		session.close();
		
		

		return CustomerList;
	}

}
