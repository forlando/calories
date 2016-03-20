package com.toptal.calories.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.toptal.calories.model.LoggedUser;
import com.toptal.calories.model.UserEntity;
import com.toptal.calories.secure.SecurityToken;
import com.toptal.calories.service.UserService;

@Component
@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource extends SecurityToken<UserEntity> {

	@Autowired
	private UserService service;

	@Context
	private HttpServletRequest request;

	@GET
	@Path("/{email}")
	public LoggedUser get(@PathParam("email") String email) {
		try {
			UserEntity user = this.service.get(email);
			return new LoggedUser(user, this.newToken(this.request, user));
		} catch(SecurityException exception) {
			exception.printStackTrace();
			throw new WebApplicationException(403);
		}
	}

	@DELETE
	@Path("/{token}")
	public void remove(@PathParam("token") String token) {
		try {
			this.removeToken(this.request, token);
		} catch(SecurityException exception) {
			exception.printStackTrace();
			throw new WebApplicationException(403);
		} catch(IllegalArgumentException exception) {
			exception.printStackTrace();
			throw new WebApplicationException(404);
		}
	}
}
