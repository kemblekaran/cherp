package com.cherp.dao.masters;

import java.util.List;

import org.hibernate.SessionFactory;

public abstract class MasterBaseDao {
	
	public abstract void createSession();

	public abstract String insert(Object obj);

	public abstract String update(Object obj);

	public abstract String delete(Object obj);

	public abstract <T> List<T> selectAll();
}
