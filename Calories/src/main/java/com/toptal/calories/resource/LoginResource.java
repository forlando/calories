package com.toptal.calories.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.toptal.calories.model.LoggedUser;
import com.toptal.calories.model.UserEntity;
import com.toptal.calories.service.LoginService;
import com.toptal.calories.service.UserService;

@Component
@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

	@Autowired
	private LoginService service;

	@Autowired
	private UserService userService;

	@GET
	@Path("/{email}")
	public LoggedUser get(@PathParam("email") String email) {
		try {
			UserEntity user = this.userService.get(email);
			return new LoggedUser(user, this.service.login(user));
		} catch(SecurityException exception) {
			throw new WebApplicationException(Response.status(Status.UNAUTHORIZED).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		} catch(Exception exception) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		}
	}

	@DELETE
	@Path("/{token}")
	public void remove(@PathParam("token") String token) {
		try {
			this.service.logout(token);
		} catch(SecurityException exception) {
			throw new WebApplicationException(Response.status(Status.FORBIDDEN).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		} catch(Exception exception) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		}
	}
}
