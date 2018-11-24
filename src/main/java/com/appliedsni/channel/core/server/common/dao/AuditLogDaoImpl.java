package com.appliedsni.channel.core.server.common.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appliedsni.channel.core.server.entity.AuditLogEntity;
import com.appliedsni.channel.core.server.handler.AccountUtils;

import channel.client.dao.ServerDao;
@Component
public class AuditLogDaoImpl {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuditLogDaoImpl.class);
	@Autowired
	ServerDao serverDao;
	public List<AuditLogEntity> getAuditLogEntity(UUID pEntityIdKey,Integer pCurrentVersion,Integer pOldVersion){
		List<AuditLogEntity> auditLogEntitys=null;
		Session session=null;
		try {
			session = serverDao.getSessionFactory().openSession();
			StringBuilder queryString = new StringBuilder("from AuditLogEntity where mEntityIdKey = :pEntityIdKey");
			if (pCurrentVersion != null && pOldVersion != null) {
				queryString.append(" and ( mVersion =:pCurrentVersion or mVersion =:pOldVersion)");
			} else {
				if (pCurrentVersion != null) {
					queryString.append(" and  mVersion =:pCurrentVersion");
				} else if (pOldVersion != null) {
					queryString.append(" and  mVersion =:pOldVersion");
				} else {
					queryString.append(" order by mVersion desc");
				}
			}
			Query query = session.createQuery(queryString.toString());
			query.setParameter("pEntityIdKey", pEntityIdKey);
			if (pCurrentVersion != null && pOldVersion != null) {
				query.setParameter("pCurrentVersion", pCurrentVersion);
				query.setParameter("pOldVersion", pOldVersion);
			} else {
				if (pCurrentVersion != null) {
					query.setParameter("pCurrentVersion", pCurrentVersion);
				} else if (pOldVersion != null) {
					query.setParameter("pOldVersion", pOldVersion);
				} else {
					query.setMaxResults(1);
				}
			}
			auditLogEntitys = query.list();
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.getMessage());
		}
		finally {
			if(session!=null)
				session.close();
		}
		return auditLogEntitys;
	} 
}
