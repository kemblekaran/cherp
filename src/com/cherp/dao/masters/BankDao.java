package com.cherp.dao.masters;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cherp.entities.Bank;
import com.cherp.utils.HibernateUtil;

public class BankDao extends MasterBaseDao {

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
		Bank Bank = (Bank) obj;
		createSession();

		Transaction t = session.beginTransaction();

		session.save(Bank);

		t.commit();

		session.close();

		return "Insert Successful";
	}

	@Override
	public String update(Object obj) {
		// TODO Auto-generated method stub
		Bank Bank = (Bank) obj;
		createSession();

		Transaction t = session.beginTransaction();



		session.update(Bank);

		t.commit();

		session.close();

		return "Update Successful";
	}

	@Override
	public String delete(Object obj) {
		// TODO Auto-generated method stub
		Bank Bank = (Bank) obj;
		createSession();

		Transaction t = session.beginTransaction();


		session.delete(Bank);

		t.commit();

		session.close();
		
		return "Delete Successful";
	}

	@Override
	public List<Bank> selectAll() {
		// TODO Auto-generated method stub
		
		createSession();
		
		//create criteria builder
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		
		//create criteria query
		CriteriaQuery<Bank> criteriaQuery = criteriaBuilder.createQuery(Bank.class);
		
		//specify criteria root
		Root<Bank> rootBank = criteriaQuery.from(Bank.class);

		criteriaQuery.where(criteriaBuilder.equal(rootBank.get("status"), 1));
		
		//Execute query
		List<Bank> BankList = session.createQuery(criteriaQuery).getResultList();
		
		//close session
		session.close();
		
		

		return BankList;
	}

}
