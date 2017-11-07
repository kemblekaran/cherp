package com.cherp.dao.masters;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cherp.entities.Drivers;
import com.cherp.utils.HibernateUtil;

public class DriversDao extends MasterBaseDao{

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
		Drivers Drivers = (Drivers) obj;
		createSession();

		Transaction t = session.beginTransaction();

		session.save(Drivers);

		t.commit();

		session.close();

		return "Insert Successful";
	}

	@Override
	public String update(Object obj) {
		// TODO Auto-generated method stub
		Drivers Drivers = (Drivers) obj;
		createSession();

		Transaction t = session.beginTransaction();



		session.update(Drivers);

		t.commit();

		session.close();

		return "Update Successful";
	}

	@Override
	public String delete(Object obj) {
		// TODO Auto-generated method stub
		Drivers Drivers = (Drivers) obj;
		createSession();

		Transaction t = session.beginTransaction();


		session.delete(Drivers);

		t.commit();

		session.close();
		
		return "Delete Successful";
	}

	@Override
	public List<Drivers> selectAll() {
		// TODO Auto-generated method stub
		
		createSession();
		
		//create criteria builder
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		
		//create criteria query
		CriteriaQuery<Drivers> criteriaQuery = criteriaBuilder.createQuery(Drivers.class);
		
		//specify criteria root
		Root<Drivers> rootDrivers = criteriaQuery.from(Drivers.class);

		criteriaQuery.where(criteriaBuilder.equal(rootDrivers.get("status"), 1));
		
		//Execute query
		List<Drivers> DriversList = session.createQuery(criteriaQuery).getResultList();
		
		//close session
		session.close();
		
		

		return DriversList;
	}

}
