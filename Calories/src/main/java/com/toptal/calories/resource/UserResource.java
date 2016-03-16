package com.toptal.calories.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.toptal.calories.model.User;
import com.toptal.calories.service.UserService;

//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;

//@Component
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	
//	@Autowire
	private UserService service = new UserService();
	
	@POST
	public User createUpdate(User user) {
		return this.service.createUpdate(user);
	}
	
	@DELETE
	@Path("/{login}")
	public String delete(@PathParam("login") String login) {
		return this.service.delete(login);
	}
	
	@DELETE
	public void delete() {
		this.service.delete();
	}

	@GET
	@Path("/{login}")
	public User get(@PathParam("login") String login) {
		return this.service.get(login);
	}
	
	@GET
	public User[] list() {
		return this.service.list();
	}

}
