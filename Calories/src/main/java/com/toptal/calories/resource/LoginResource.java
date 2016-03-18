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

import com.toptal.calories.model.LoggedUser;
import com.toptal.calories.model.User;
import com.toptal.calories.secure.TokenManager;
import com.toptal.calories.service.UserService;

//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;

//@Component
@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource extends TokenManager<User> {
//	@Autowire
	private UserService service = new UserService();

	@GET
	@Path("/{email}")
	public LoggedUser get(@Context HttpServletRequest request, @PathParam("email") String email) {
		User user = this.service.get(email);

		if (user != null) {
			return new LoggedUser(user, this.newToken(request, user));
		} else {
			throw new WebApplicationException(404);
		}
	}

	@DELETE
	@Path("/{token}")
	public void remove(@Context HttpServletRequest request, @PathParam("token") String token) {
		this.removeToken(request, token);
	}
}
