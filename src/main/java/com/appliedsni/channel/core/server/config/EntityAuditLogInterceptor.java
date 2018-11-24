package com.appliedsni.channel.core.server.config;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.UUID;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.appliedsni.channel.core.server.common.annotations.AuditIgnore;
import com.appliedsni.channel.core.server.common.annotations.Auditable;
import com.appliedsni.channel.core.server.entity.AuditLogEntity;
/**
 * @author Gauri
 *
 */

import channel.client.dao.ServerDao;
import channel.client.function.CommonConstants;

public class EntityAuditLogInterceptor extends EmptyInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String s1 = "\"";
	private static final String s2 = "\" : \"";
	private static final String s3 = "\" , ";
	

	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		ServerDao mServerDao = (ServerDao) ChannelApplicationContext.get().getBean("serverDao");
		Class aClass = entity.getClass();
		StringBuilder previous = new StringBuilder();
		StringBuilder current = new StringBuilder();
		if (aClass.isAnnotationPresent(Auditable.class)) {
			int version=0;
			int i = 0;
			for (String propertyName : propertyNames) {
				try {
					if(propertyName.equals(CommonConstants.VERSION_STRING)){
						version=(int) state[i];
						state[i]=++version;
						i++;
						continue;
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
			AuditLogEntity auditLogEntity = new AuditLogEntity(previous.toString(), current.toString(), version, refId,
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
		if (aClass.isAnnotationPresent(Auditable.class)) {

			int i = 0;
			for (String propertyName : propertyNames) {
				try {
					if(propertyName.equals(CommonConstants.VERSION_STRING)){
						version=(int) currentState[i];
						currentState[i]=++version;
						i++;
						continue;
					}else if(propertyName.equals("mLastUpdate")){
						currentState[i]=new Date();
						i++;
						continue;
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
