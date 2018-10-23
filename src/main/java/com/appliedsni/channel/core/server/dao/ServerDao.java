package com.appliedsni.channel.core.server.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public class ServerDao extends HibernateDaoSupport{
		
	public Object get(Class pClass, String pId){
		return getHibernateTemplate().get(pClass, pId);
	}

	public Object get(Class pClass, Integer pId){
		return getHibernateTemplate().get(pClass, pId);
	}

	public Object get(Class pClass, UUID pId){
		return getHibernateTemplate().get(pClass, pId);
	}

	public void save(Object pEntity){
		getHibernateTemplate().save(pEntity);
	}
	
	public List<Object> find(String pQueryString, Object... pValues){
		return (List<Object>)getHibernateTemplate().find(pQueryString, pValues);
	}	
}
