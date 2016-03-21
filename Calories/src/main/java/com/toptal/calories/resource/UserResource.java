package com.toptal.calories.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.toptal.calories.model.User;
import com.toptal.calories.model.UserEntity;
import com.toptal.calories.service.LoginService;
import com.toptal.calories.service.UserService;

@Component
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	@Autowired
	private UserService service;

	@Autowired
	private LoginService loginService;
	
	@POST
	@Path("/{token}")
	public UserEntity save(@PathParam("token") String token, User user) {
		try {
			return this.service.save(this.loginService.getLoggedUser(token), new UserEntity(user));
		} catch(SecurityException exception) {
			throw new WebApplicationException(Response.status(Status.FORBIDDEN).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		} catch(Exception exception) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		}
	}
	
	@DELETE
	@Path("/{token}/{email}")
	public void remove(@Context HttpServletRequest request, @PathParam("token") String token, @PathParam("email") String email) {
		try {
			this.service.remove(this.loginService.getLoggedUser(token), email);
		} catch(SecurityException exception) {
			throw new WebApplicationException(Response.status(Status.FORBIDDEN).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		} catch(Exception exception) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		}
	}

	@GET
	@Path("/{token}/{email}")
	public UserEntity get(@Context HttpServletRequest request, @PathParam("token") String token, @PathParam("email") String email) {
		try {
			return this.service.get(this.loginService.getLoggedUser(token), email);
		} catch(SecurityException exception) {
			throw new WebApplicationException(Response.status(Status.FORBIDDEN).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		} catch(Exception exception) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		}
	}
	
	@GET
	@Path("/{token}")
	public User[] query(@Context HttpServletRequest request, @PathParam("token") String token) {
		try {
			List<UserEntity> usersEntities = this.service.query(this.loginService.getLoggedUser(token));
			
			User[] users = new User[usersEntities.size()];
	
			for (int index = 0; index < users.length; index++) {
				users[index] = new User(usersEntities.get(index));
			}
			
			return users;
		} catch(SecurityException exception) {
			throw new WebApplicationException(Response.status(Status.FORBIDDEN).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		} catch(Exception exception) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		}
	}
}
