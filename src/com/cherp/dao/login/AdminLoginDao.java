package com.cherp.dao.login;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cherp.entities.AdminLoginInfo;
import com.cherp.utils.HibernateUtil;

public class AdminLoginDao {

	HibernateUtil hbutil = null;
	SessionFactory factory = null;
	Session session = null;

	public void createSession() {
		hbutil = HibernateUtil.getInstance();
		factory = hbutil.getSessionFactory();
		session = factory.openSession();
	}

	public int insert(AdminLoginInfo ali) {
		createSession();

		Transaction t = session.beginTransaction();

		session.save(ali);

		t.commit();

		session.close();

		return 1;
	}

	public int update(AdminLoginInfo ali) {
		createSession();

		Transaction t = session.beginTransaction();

		session.update(ali);

		t.commit();

		session.close();

		return 1;
	}

	public int delete(AdminLoginInfo ali) {
		createSession();

		Transaction t = session.beginTransaction();

		session.delete(ali);

		t.commit();

		session.close();

		return 1;
	}

	public int loginSuccess(AdminLoginInfo ali) {

		int success = 0;

		createSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<AdminLoginInfo> criteriaQuery = criteriaBuilder.createQuery(AdminLoginInfo.class);

		Root<AdminLoginInfo> root = criteriaQuery.from(AdminLoginInfo.class);

		criteriaQuery.where(criteriaBuilder.equal(root.get("username"), ali.getUsername()),
				criteriaBuilder.equal(root.get("password"), ali.getPassword()));

		List<AdminLoginInfo> list = session.createQuery(criteriaQuery).getResultList();

		if (!list.isEmpty()) {
			success = 1;
		}
		return success;
	}

}
