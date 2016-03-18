package com.toptal.calories.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.toptal.calories.model.User;
import com.toptal.calories.secure.TokenManager;
import com.toptal.calories.service.UserService;

//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;

//@Component
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource extends TokenManager<User> {
//	@Autowire
	private UserService service = new UserService();
	
	@POST
	@Path("/{token}")
	public User save(@Context HttpServletRequest request, @PathParam("token") String token, User user) {
		return this.service.save(user);
	}
	
	@DELETE
	@Path("/{token}/{email}")
	public void remove(@Context HttpServletRequest request, @PathParam("token") String token, @PathParam("email") String email) {
		this.service.remove(email);
	}
	
	@DELETE
	@Path("/{token}")
	public void remove(@Context HttpServletRequest request, @PathParam("token") String token) {
		this.service.remove();
	}

	@GET
	@Path("/{token}/{email}")
	public User get(@Context HttpServletRequest request, @PathParam("token") String token, @PathParam("email") String email) {
		return this.service.get(email);
	}
	
	@GET
	@Path("/{token}")
	public User[] query(@Context HttpServletRequest request, @PathParam("token") String token) {
		return this.service.query();
	}
}
