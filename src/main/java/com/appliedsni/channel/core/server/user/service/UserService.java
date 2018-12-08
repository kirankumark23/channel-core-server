package com.appliedsni.channel.core.server.user.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.appliedsni.channel.core.server.user.dao.UserDaoImpl;
import com.appliedsni.channel.core.server.user.domain.RoleEntity;
import com.appliedsni.channel.core.server.user.domain.UserEntity;
import com.appliedsni.channel.core.server.user.domain.UserRoleEntity;

@Path(value = "/service")

public class UserService {

	  @Autowired
	    private UserDaoImpl userDaoImpl;
	  
	 @POST
	@Path("/addUser")  
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public UserEntity addUser(UserEntity pUserEntity) {

        return userDaoImpl.addNewUser(pUserEntity);
    }

	 @POST
		@Path("/addRole")  
	    @Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
	    public RoleEntity addRole(RoleEntity pRoleEntity) {

	        
	        return userDaoImpl.addNewRole(pRoleEntity);
	    }
	
	 @Path("/updateUser")
	    @PUT
	    @Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
	    public UserEntity updateUser(UserEntity pUserEntity) {

			return userDaoImpl.updateUser(pUserEntity);
	    }
	 
	@Path("/updateUserWithRole")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public UserRoleEntity updateUserWithRole(UserRoleEntity pUserEntity) {

		return userDaoImpl.updateUserWithRole(pUserEntity);
    }
}
