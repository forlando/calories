package com.toptal.calories.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.toptal.calories.model.Meal;
import com.toptal.calories.model.User;
import com.toptal.calories.secure.TokenManager;
import com.toptal.calories.service.MealService;

//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;

//@Component
@Path("/meals")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MealResource extends TokenManager<User> {
//	@Autowire
	private MealService service = new MealService();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	@POST
	@Path("/{token}")
	public Meal save(@Context HttpServletRequest request, @PathParam("token") String token, Meal meal) {
		return this.service.save(meal);
	}
	
	@DELETE
	@Path("/{token}/{id}")
	public void remove(@Context HttpServletRequest request, @PathParam("token") String token, @PathParam("id") Integer id) {
		this.service.remove(id);
	}

	@DELETE
	@Path("/{token}")
	public void remove(@Context HttpServletRequest request, @PathParam("token") String token) {
		this.service.remove();
	}

	@GET
	@Path("/{token}/{id}")
	public Meal get(@Context HttpServletRequest request, @PathParam("token") String token, @PathParam("id") Integer id) {
		return this.service.get(id);
	}
	
	@GET
	@Path("/{token}")
	public Meal[] query(@Context HttpServletRequest request, @PathParam("token") String token) {
		try
		{
			Date fromDate = this.parseDate(request.getParameter("fromDate"));
			Date toDate = this.parseDate(request.getParameter("toDate"));
			Date fromTime = this.parseDate(request.getParameter("fromTime"));
			Date toTime = this.parseDate(request.getParameter("toTime"));

			return this.service.query(this.readToken(request, token), fromDate, toDate, fromTime, toTime);
		} catch(ParseException exception) {
			throw new WebApplicationException(404);
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
