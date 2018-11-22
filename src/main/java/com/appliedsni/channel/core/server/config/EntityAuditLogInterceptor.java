package com.appliedsni.channel.core.server.config;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.UUID;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.appliedsni.channel.core.server.common.annotations.AuditIgnore;
import com.appliedsni.channel.core.server.common.annotations.EntityAudit;
import com.appliedsni.channel.core.server.entity.AuditLogEntity;
/**
 * @author Gauri
 *
 */

import channel.client.dao.ServerDao;

public class EntityAuditLogInterceptor extends EmptyInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String s1 = "\"";
	private static final String s2 = "\" : \"";
	private static final String s3 = "\" , ";
	private static final String versionString="mVersion";

	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		ServerDao mServerDao = (ServerDao) ChannelApplicationContext.get().getBean("serverDao");
		Class aClass = entity.getClass();
		StringBuilder previous = new StringBuilder();
		StringBuilder current = new StringBuilder();
		if (aClass.isAnnotationPresent(EntityAudit.class)) {
			int version=0;
			int i = 0;
			for (String propertyName : propertyNames) {
				try {
					if(propertyName.equals(versionString)){
						version=(int) state[i];
					}
					Field field = aClass.getDeclaredField(propertyName);
					if (!field.isAnnotationPresent(AuditIgnore.class)) {
						previous.append(s1).append(propertyName).append(s2)
								.append(state[i] != null ? state[i].toString() : null).append(s3);
					}
				} catch (NoSuchFieldException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
			UUID refId = (UUID) id;
			AuditLogEntity auditLogEntity = new AuditLogEntity(previous.toString(), current.toString(), 1, refId,
					aClass.getName());
			mServerDao.save(auditLogEntity);
		}

	}

	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		ServerDao mServerDao = (ServerDao) ChannelApplicationContext.get().getBean("serverDao");
		int version=0;
		Class aClass = entity.getClass();
		StringBuilder previous = new StringBuilder();
		StringBuilder current = new StringBuilder();
		if (aClass.isAnnotationPresent(EntityAudit.class)) {
			System.out.println("true");

			int i = 0;
			for (String propertyName : propertyNames) {
				try {
					if(propertyName.equals(versionString)){
						version=(int) currentState[i];
					}
					Field field = aClass.getDeclaredField(propertyName);
					if (!field.isAnnotationPresent(AuditIgnore.class)) {
						if ((currentState[i] != null && !currentState[i].equals(previousState[i]))
								|| (previousState[i] != null && !previousState[i].equals(currentState[i]))) {
							previous.append(s1).append(propertyName).append(s2)
									.append(previousState[i] != null ? previousState[i].toString() : null).append(s3);
							current.append(s1).append(propertyName).append(s2)
									.append(currentState[i] != null ? currentState[i].toString() : null).append(s3);
						}
					}
				} catch (NoSuchFieldException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
			UUID refId = (UUID) id;
			AuditLogEntity auditLogEntity = new AuditLogEntity(previous.toString(), current.toString(), version, refId,
					aClass.getName());
			mServerDao.save(auditLogEntity);
		}

		return false;
	}

}
