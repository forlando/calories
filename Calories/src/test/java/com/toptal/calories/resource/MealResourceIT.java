package com.toptal.calories.resource;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.toptal.calories.model.LoggedUser;
import com.toptal.calories.model.Meal;

import static org.testng.AssertJUnit.*;

import java.util.Date;

public class MealResourceIT {

	private WebTarget target; 

	@BeforeClass
	public void beforeClass(ITestContext context) {
		this.target = ClientBuilder.newClient().target("http://localhost:8080/rest/meals");
	}
	
	@AfterClass
	public void afterClass(ITestContext context) {
	}

	@Test(groups="saveMeal", dependsOnGroups="loadUsers")
	public void testSave(ITestContext context) {
		LoggedUser loggedUser = (LoggedUser) context.getAttribute("loggedUser");
		Meal meal1 = this.target.path(loggedUser.getToken()).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(new Meal(loggedUser.getEmail(), "Meal 1", new Date(), new Date(), 500, true), MediaType.APPLICATION_JSON_TYPE)).readEntity(Meal.class);
		Meal meal2 = this.target.path(loggedUser.getToken()).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(new Meal(loggedUser.getEmail(), "Meal 2", new Date(), new Date(), 500, true), MediaType.APPLICATION_JSON_TYPE)).readEntity(Meal.class);
		Meal meal3 = this.target.path(loggedUser.getToken()).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(new Meal(loggedUser.getEmail(), "Meal 3", new Date(), new Date(), 500, true), MediaType.APPLICATION_JSON_TYPE)).readEntity(Meal.class);
		Meal meal4 = this.target.path(loggedUser.getToken()).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(new Meal(loggedUser.getEmail(), "Meal 4", new Date(), new Date(), 500, true), MediaType.APPLICATION_JSON_TYPE)).readEntity(Meal.class);
		Meal meal5 = this.target.path(loggedUser.getToken()).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(new Meal(loggedUser.getEmail(), "Meal 5", new Date(), new Date(), 500, true), MediaType.APPLICATION_JSON_TYPE)).readEntity(Meal.class);
		assertNotNull(meal1.getId());
		assertNotNull(meal2.getId());
		assertNotNull(meal3.getId());
		assertNotNull(meal4.getId());
		assertNotNull(meal5.getId());
	}

	@Test(groups="loadMeals", dependsOnGroups="saveMeal")
	public void testQuery(ITestContext context) {
		LoggedUser loggedUser = (LoggedUser) context.getAttribute("loggedUser");
		Meal[] meals = this.target.path(loggedUser.getToken()).request(MediaType.APPLICATION_JSON_TYPE).get(Meal[].class);
		assertNotNull(meals);
		assertEquals(5, meals.length);
		assertEquals("forlando@gmail.com", meals[0].getEmail());
		assertEquals("Meal 1", meals[0].getText());
		assertNotNull(meals[0].getDate());
		assertNotNull(meals[0].getTime());
		assertEquals(500,  meals[0].getCalories().intValue());
	}
}
