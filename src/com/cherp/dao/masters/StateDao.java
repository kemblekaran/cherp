package com.cherp.dao.masters;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cherp.entities.State;
import com.cherp.utils.HibernateUtil;

public class StateDao extends MasterBaseDao{
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
		State state = (State) obj;
		createSession();

		Transaction t = session.beginTransaction();

		session.save(state);

		t.commit();

		session.close();

		return "Insert Successful";
	}

	@Override
	public String update(Object obj) {
		// TODO Auto-generated method stub
		State state = (State) obj;
		createSession();

		Transaction t = session.beginTransaction();



		session.update(state);

		t.commit();

		session.close();

		return "Update Successful";
	}

	@Override
	public String delete(Object obj) {
		// TODO Auto-generated method stub
		State state = (State) obj;
		createSession();

		Transaction t = session.beginTransaction();


		session.delete(state);

		t.commit();

		session.close();
		
		return "Delete Successful";
	}

	@Override
	public List<State> selectAll() {
		// TODO Auto-generated method stub
		
		createSession();
		
		//create criteria builder
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		
		//create criteria query
		CriteriaQuery<State> criteria = criteriaBuilder.createQuery(State.class);
		
		//specify criteria root
		criteria.from(State.class);
		
		//Execute query
		List<State> stateList = session.createQuery(criteria).getResultList();
		
		//close session
		session.close();
		
		

		return stateList;
	}

}
