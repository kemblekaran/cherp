package com.cherp.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.cherp.entities.Area;

public class HibernateUtil {
	private static HibernateUtil instance = null;

	private static SessionFactory sessionFactory;
	private static StandardServiceRegistry serviceRegistry;

	private HibernateUtil() {
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		config.addAnnotatedClass(Area.class);
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		sessionFactory = config.buildSessionFactory(serviceRegistry);
	}

	public static HibernateUtil getInstance() {
		if (instance == null) {
			instance = new HibernateUtil();
		}
		return instance;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
