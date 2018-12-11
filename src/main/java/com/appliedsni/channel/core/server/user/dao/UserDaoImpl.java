package com.appliedsni.channel.core.server.user.dao;




import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.appliedsni.channel.core.server.security.service.PasswordEncoder;
import com.appliedsni.channel.core.server.user.domain.RoleEntity;
import com.appliedsni.channel.core.server.user.domain.UserEntity;
import com.appliedsni.channel.core.server.user.domain.UserRoleEntity;

import channel.client.dao.ServerDao;

/**
 * Service that provides operations for {@link UserEntity}s.
 *
 * @author gauri
 */
@Component
public class UserDaoImpl {

    @Autowired
    private ServerDao mServerDao;

    /**
     * Find a user by username or email.
     *
     * @param identifier
     * @return
     */
    public UserEntity findByUsernameOrEmail(String identifier) {
    	List<UserEntity> users=null;
    	Session session=null;
		try {
			session =mServerDao.getSessionFactory().openSession();
			Query query=session.createQuery("SELECT u FROM UserEntity u WHERE u.mEmailaddress = :identifier OR u.mEmailaddress = :identifier");
			query.setParameter("identifier", identifier);
			users = query.list();
			if (users.isEmpty()) {
			    return null;
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(session!=null)
			{
				session.close();
			}
		}
        return users.get(0);
    }

    /**
     * Find all users.
     *
     * @return
     */
    public List<UserEntity> findAll() {
         mServerDao.find("from UserEntity");
         
         return null;
    }

    /**
     * Find a user by id.
     *
     * @param userId
     * @return
     */
    public UserEntity findById(UUID pUser) {
        return (UserEntity)mServerDao.get(UserEntity.class, pUser);
    }
    
    /**
     * Find a user by username or email.
     *
     * @param identifier
     * @return
     */
    @Transactional
    public UserEntity addNewUser(UserEntity pUserEntity) {
    	
		try {
			String password = pUserEntity.getPasswordhash();
			String encryptedPassword = PasswordEncoder.hashPassword(password);
			pUserEntity.setPasswordhash(encryptedPassword);
			mServerDao.save(pUserEntity);

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
		}
        return pUserEntity;
    }
    
    @Transactional
    public RoleEntity addNewRole(RoleEntity pRoleEntity) {
    	
		try {
			
			mServerDao.save(pRoleEntity);

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
		}
        return pRoleEntity;
    }
    
    @Transactional
    public UserEntity updateUser(UserEntity pUserEntity) {
    	
		try {			
			mServerDao.update(pUserEntity);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
		}
        return pUserEntity;
    }

    @Transactional
	public UserRoleEntity updateUserWithRole(UserRoleEntity pUserRoleEntity) {
    	try {
			
			mServerDao.save(pUserRoleEntity);
            
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
		}
    	 return pUserRoleEntity;
    }
    
    @Transactional
    public List<UserEntity> getAllUser() {
    	List<Object> objList = mServerDao.find("from UserEntity order by mAdded");

		List<UserEntity> userList = new ArrayList<UserEntity>();
		
		for(Object obj : objList){
			
			userList.add((UserEntity)obj);
		}
		
		return userList;
    }
    
    
    @Transactional
    public List<UserRoleEntity> getAllUserRole(UUID pIdKey) {
  
		
		List<UserRoleEntity> userroles=null;
    	Session session=null;
		try {
			session =mServerDao.getSessionFactory().openSession();
			Query query=session.createQuery("from UserRoleEntity u WHERE u.mUserByUser = :identifier order by mAdded");
			query.setParameter("identifier", pIdKey);
			userroles = query.list();
			if (userroles.isEmpty()) {
			    return null;
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(session!=null)
			{
				session.close();
			}
		}
        return userroles;
    }
   
}
