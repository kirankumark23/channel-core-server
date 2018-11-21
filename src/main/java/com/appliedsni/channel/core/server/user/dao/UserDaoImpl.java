package com.appliedsni.channel.core.server.user.dao;




import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.appliedsni.channel.core.server.user.domain.UserEntity;

import channel.client.dao.ServerDao;

import java.util.List;
import java.util.Optional;

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
        return null;
    }

    /**
     * Find a user by id.
     *
     * @param userId
     * @return
     */
    public Optional<UserEntity> findById(Long userId) {
        return null;
    }
}
