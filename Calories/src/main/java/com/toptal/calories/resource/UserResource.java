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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.toptal.calories.model.User;
import com.toptal.calories.model.UserEntity;
import com.toptal.calories.secure.SecurityToken;
import com.toptal.calories.service.UserService;

@Component
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource extends SecurityToken<UserEntity> {

	@Autowired
	private UserService service;

	@Context
	private HttpServletRequest request;
	
	@POST
	@Path("/{token}")
	public UserEntity save(@PathParam("token") String token, User user) {
		try {
			return this.service.save(this.readToken(this.request, token), new UserEntity(user));
		} catch(SecurityException exception) {
			exception.printStackTrace();
			throw new WebApplicationException(403);
		} catch(IllegalArgumentException exception) {
			exception.printStackTrace();
			throw new WebApplicationException(404);
		}
	}
	
	@DELETE
	@Path("/{token}/{email}")
	public void remove(@Context HttpServletRequest request, @PathParam("token") String token, @PathParam("email") String email) {
		try {
			this.service.remove(this.readToken(this.request, token), email);
		} catch(SecurityException exception) {
			exception.printStackTrace();
			throw new WebApplicationException(403);
		} catch(IllegalArgumentException exception) {
			exception.printStackTrace();
			throw new WebApplicationException(404);
		}
	}

	@GET
	@Path("/{token}/{email}")
	public UserEntity get(@Context HttpServletRequest request, @PathParam("token") String token, @PathParam("email") String email) {
		try {
			return this.service.get(this.readToken(this.request, token), email);
		} catch(SecurityException exception) {
			exception.printStackTrace();
			throw new WebApplicationException(403);
		} catch(IllegalArgumentException exception) {
			exception.printStackTrace();
			throw new WebApplicationException(404);
		}
	}
	
	@GET
	@Path("/{token}")
	public User[] query(@Context HttpServletRequest request, @PathParam("token") String token) {
		try {
			List<UserEntity> usersEntities = this.service.query(this.readToken(this.request, token));
			
			User[] users = new User[usersEntities.size()];
	
			for (int index = 0; index < users.length; index++) {
				users[index] = new User(usersEntities.get(index));
			}
			
			return users;
		} catch(SecurityException exception) {
			exception.printStackTrace();
			throw new WebApplicationException(403);
		} catch(IllegalArgumentException exception) {
			exception.printStackTrace();
			throw new WebApplicationException(404);
		}
	}
}
