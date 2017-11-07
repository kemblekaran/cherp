package com.cherp.dao.masters;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cherp.entities.Company;
import com.cherp.utils.HibernateUtil;

public class CompanyDao extends MasterBaseDao{

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
		Company Company = (Company) obj;
		createSession();

		Transaction t = session.beginTransaction();

		session.save(Company);

		t.commit();

		session.close();

		return "Insert Successful";
	}

	@Override
	public String update(Object obj) {
		// TODO Auto-generated method stub
		Company Company = (Company) obj;
		createSession();

		Transaction t = session.beginTransaction();



		session.update(Company);

		t.commit();

		session.close();

		return "Update Successful";
	}

	@Override
	public String delete(Object obj) {
		// TODO Auto-generated method stub
		Company Company = (Company) obj;
		createSession();

		Transaction t = session.beginTransaction();


		session.delete(Company);

		t.commit();

		session.close();
		
		return "Delete Successful";
	}

	@Override
	public List<Company> selectAll() {
		// TODO Auto-generated method stub
		
		createSession();
		
		//create criteria builder
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		
		//create criteria query
		CriteriaQuery<Company> criteriaQuery = criteriaBuilder.createQuery(Company.class);
		
		//specify criteria root
		Root<Company> rootCompany = criteriaQuery.from(Company.class);

		criteriaQuery.where(criteriaBuilder.equal(rootCompany.get("status"), 1));
		
		//Execute query
		List<Company> CompanyList = session.createQuery(criteriaQuery).getResultList();
		
		//close session
		session.close();
		
		

		return CompanyList;
	}

}
