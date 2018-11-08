package com.appliedsni.channel.core.server.security.service;


import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appliedsni.channel.core.server.security.exception.AuthenticationException;
import com.appliedsni.channel.core.server.user.dao.UserDaoImpl;
import com.appliedsni.channel.core.server.user.domain.UserEntity;

/**
 * Component for validating user credentials.
 *
 * @author gauri
 */
@Component
public class UsernamePasswordValidator {

    @Autowired
    private UserDaoImpl userDaoImpl;

    @Autowired
    private PasswordEncoder mPasswordEncoder;

    /**
     * Validate username and password.
     *
     * @param username
     * @param pPassword
     * @return
     */
    public UserEntity validateCredentials(String username, String pPassword) {

        UserEntity user = userDaoImpl.findByUsernameOrEmail(username);

        if (user == null) {
            // User cannot be found with the given username/email
            throw new AuthenticationException("Bad credentials.");
        }
/*
        if (!user.isActive()) {
            // User is not active
            throw new AuthenticationException("The user is inactive.");
        }*/
       // String password=Crypto.decrypt(user.getPasswordhash());
        if (!mPasswordEncoder.checkPassword(pPassword, user.getPasswordhash())) {
            // Invalid password
            throw new AuthenticationException("Bad credentials.");
        }

        return user;
    }
}