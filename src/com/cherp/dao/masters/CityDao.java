package com.cherp.dao.masters;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cherp.entities.City;
import com.cherp.utils.HibernateUtil;

public class CityDao extends MasterBaseDao{

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
		City City = (City) obj;
		createSession();

		Transaction t = session.beginTransaction();

		session.save(City);

		t.commit();

		session.close();

		return "Insert Successful";
	}

	@Override
	public String update(Object obj) {
		// TODO Auto-generated method stub
		City City = (City) obj;
		createSession();

		Transaction t = session.beginTransaction();



		session.update(City);

		t.commit();

		session.close();

		return "Update Successful";
	}

	@Override
	public String delete(Object obj) {
		// TODO Auto-generated method stub
		City City = (City) obj;
		createSession();

		Transaction t = session.beginTransaction();


		session.delete(City);

		t.commit();

		session.close();
		
		return "Delete Successful";
	}

	@Override
	public List<City> selectAll() {
		// TODO Auto-generated method stub
		
		createSession();
		
		//create criteria builder
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		
		//create criteria query
		CriteriaQuery<City> criteriaQuery = criteriaBuilder.createQuery(City.class);
		
		//specify criteria root
		Root<City> rootCity = criteriaQuery.from(City.class);

		criteriaQuery.where(criteriaBuilder.equal(rootCity.get("status"), 1));
		
		//Execute query
		List<City> CityList = session.createQuery(criteriaQuery).getResultList();
		
		//close session
		session.close();
		
		

		return CityList;
	}

}
