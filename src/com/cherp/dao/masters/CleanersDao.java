package com.cherp.dao.masters;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cherp.entities.Cleaners;
import com.cherp.utils.HibernateUtil;

public class CleanersDao extends MasterBaseDao{

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
		Cleaners Cleaners = (Cleaners) obj;
		createSession();

		Transaction t = session.beginTransaction();

		session.save(Cleaners);

		t.commit();

		session.close();

		return "Insert Successful";
	}

	@Override
	public String update(Object obj) {
		// TODO Auto-generated method stub
		Cleaners Cleaners = (Cleaners) obj;
		createSession();

		Transaction t = session.beginTransaction();



		session.update(Cleaners);

		t.commit();

		session.close();

		return "Update Successful";
	}

	@Override
	public String delete(Object obj) {
		// TODO Auto-generated method stub
		Cleaners Cleaners = (Cleaners) obj;
		createSession();

		Transaction t = session.beginTransaction();


		session.delete(Cleaners);

		t.commit();

		session.close();
		
		return "Delete Successful";
	}

	@Override
	public List<Cleaners> selectAll() {
		// TODO Auto-generated method stub
		
		createSession();
		
		//create criteria builder
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		
		//create criteria query
		CriteriaQuery<Cleaners> criteriaQuery = criteriaBuilder.createQuery(Cleaners.class);
		
		//specify criteria root
		Root<Cleaners> rootCleaners = criteriaQuery.from(Cleaners.class);

		criteriaQuery.where(criteriaBuilder.equal(rootCleaners.get("status"), 1));
		
		//Execute query
		List<Cleaners> CleanersList = session.createQuery(criteriaQuery).getResultList();
		
		//close session
		session.close();
		
		

		return CleanersList;
	}

}
