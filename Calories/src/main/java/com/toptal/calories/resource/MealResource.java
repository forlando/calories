package com.toptal.calories.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.toptal.calories.model.Meal;
import com.toptal.calories.model.MealEntity;
import com.toptal.calories.model.UserEntity;
import com.toptal.calories.service.LoginService;
import com.toptal.calories.service.MealService;
import com.toptal.calories.service.UserService;

@Component
@Path("/meals")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MealResource {

	@Autowired
	private MealService service;

	@Autowired
	private UserService userService;

	@Autowired
	private LoginService loginService;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	@POST
	@Path("/{token}")
	public Meal save(@PathParam("token") String token, Meal meal) {
		try {
			UserEntity loggedUser = this.loginService.getLoggedUser(token);
			return new Meal(this.service.save(loggedUser, new MealEntity(meal, this.userService.get(loggedUser, meal.getEmail()))));
		} catch(SecurityException exception) {
			throw new WebApplicationException(Response.status(Status.FORBIDDEN).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		} catch(Exception exception) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		}
	}
	
	@DELETE
	@Path("/{token}/{id}")
	public void remove(@PathParam("token") String token, @PathParam("id") Integer id) {
		try {
			this.service.remove(this.loginService.getLoggedUser(token), id);
		} catch(SecurityException exception) {
			throw new WebApplicationException(Response.status(Status.FORBIDDEN).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		} catch(Exception exception) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		}
	}

	@GET
	@Path("/{token}/{id}")
	public Meal get(@PathParam("token") String token, @PathParam("id") Integer id) {
		try {
			return new Meal(this.service.get(this.loginService.getLoggedUser(token), id));
		} catch(SecurityException exception) {
			throw new WebApplicationException(Response.status(Status.FORBIDDEN).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		} catch(Exception exception) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		}
	}
	
	@GET
	@Path("/{token}")
	public Meal[] query(@PathParam("token") String token, @QueryParam("fromDate") String fromDate, @QueryParam("toDate") String toDate, @QueryParam("fromTime") String fromTime, @QueryParam("toTime") String toTime) {
		try
		{
			List<MealEntity> mealsEntities = this.service.query(this.loginService.getLoggedUser(token), this.parseDate(fromDate), this.parseDate(toDate), this.parseDate(fromTime), this.parseDate(toTime));
	
			Meal[] meals = new Meal[mealsEntities.size()];

			for (int index = 0; index < meals.length; index++) {
				meals[index] = new Meal(mealsEntities.get(index));
			}
			
			return meals;
		} catch(SecurityException exception) {
			throw new WebApplicationException(Response.status(Status.FORBIDDEN).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		} catch(Exception exception) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build());
		}
	}

	private Date parseDate(String date) throws ParseException {
		if (date != null) {
			return this.dateFormat.parse(date);
		} else {
			return null;
		}
	}
}
