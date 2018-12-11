package com.appliedsni.channel.core.server.user.service;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	
	@GET
	@Path("/users")  
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)	
	public List<UserEntity> getUserList(){
		return userDaoImpl.getAllUser();
	}

	@GET
	@Path("/users/{idkey}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public UserEntity getUser(@PathParam("idkey") UUID pIdKey){
		return userDaoImpl.findById(pIdKey);
	}
	  
	@POST
	@Path("/users/{upid}")  
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public UserEntity addUser(UserEntity pUserEntity) {
        return userDaoImpl.addNewUser(pUserEntity);
    }

	@POST
	@Path("/roles/{rpid}")  
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public RoleEntity addRole(RoleEntity pRoleEntity) {
        return userDaoImpl.addNewRole(pRoleEntity);
    }
	
    @PUT
	@Path("/users/{upid}/update")
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public UserEntity updateUser(UserEntity pUserEntity) {
		return userDaoImpl.updateUser(pUserEntity);
    }
	 
    @POST
	@Path("/users/{upid}/roles/{rpid}")
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public UserRoleEntity updateUserWithRole(UserRoleEntity pUserEntity) {
		return userDaoImpl.updateUserWithRole(pUserEntity);
    }
    
    @GET
    @Path("/userroles/{urpid}")
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public List<UserRoleEntity> getAllUserRoles(@PathParam("urpid") UUID pIdKey) {

		return userDaoImpl.getAllUserRole(pIdKey);
    }
}
