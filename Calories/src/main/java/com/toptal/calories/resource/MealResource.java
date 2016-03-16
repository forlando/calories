package com.toptal.calories.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.toptal.calories.model.Meal;
import com.toptal.calories.service.MealService;

//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;

//@Component
@Path("/meals")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MealResource {
	
//	@Autowire
	private MealService service = new MealService();
	
	@POST
	public Meal createUpdate(Meal meal) {
		return this.service.createUpdate(meal);
	}
	
	@DELETE
	@Path("/{id}")
	public Integer delete(@PathParam("id") Integer id) {
		return this.service.delete(id);
	}

	@DELETE
	public void delete() {
		this.service.delete();
	}

	@GET
	@Path("/{id}")
	public Meal get(@PathParam("id") Integer id) {
		return this.service.get(id);
	}
	
	@GET
	public Meal[] list() {
		return this.service.list();
	}
}
