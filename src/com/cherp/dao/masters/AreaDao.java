package com.cherp.dao.masters;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cherp.entities.Area;
import com.cherp.entities.Purchase;
import com.cherp.utils.HibernateUtil;

public class AreaDao extends MasterBaseDao {

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
		Area area = (Area) obj;
		createSession();

		Transaction t = session.beginTransaction();

		session.save(area);

		t.commit();

		session.close();

		return "Insert Successful";
	}

	@Override
	public String update(Object obj) {
		// TODO Auto-generated method stub
		Area area = (Area) obj;
		createSession();

		Transaction t = session.beginTransaction();



		session.update(area);

		t.commit();

		session.close();

		return "Update Successful";
	}

	@Override
	public String delete(Object obj) {
		// TODO Auto-generated method stub
		Area area = (Area) obj;
		createSession();

		Transaction t = session.beginTransaction();


		session.delete(area);

		t.commit();

		session.close();
		
		return "Delete Successful";
	}

	@Override
	public List<Area> selectAll() {
		// TODO Auto-generated method stub
		
		createSession();
		
		//create criteria builder
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		
		//create criteria query
		CriteriaQuery<Area> criteriaQuery = criteriaBuilder.createQuery(Area.class);
		
		//specify criteria root
		Root<Area> rootArea = criteriaQuery.from(Area.class);
//		criteria.from(Area.class);
//		criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(rootArea.get("status"), 1),criteriaBuilder.equal(rootArea.get("code"), 45)));
		criteriaQuery.where(criteriaBuilder.equal(rootArea.get("status"),1));
		
		//Execute query
		List<Area> areaList = session.createQuery(criteriaQuery).getResultList();
		
		//close session
		session.close();
		
		

		return areaList;
	}

}
