package com.toptal.calories.resource;

import java.util.Date;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.toptal.calories.model.Meal;

public class MealResourceTest extends BaseTest {

	private WebTarget target;
	
	@BeforeClass
	public void beforeClass() {
        this.target = ClientBuilder.newClient().target("http://localhost:8080/calories/rest/meals/TOKEN");
	}
	
	@AfterClass
	public void afterClass() {
	}

	@Test(priority = 0)
	public void testSave() {
		Meal meal = this.target.request().post(Entity.entity(new Meal("forlando@gmail.com", "Lunch", new Date(), new Date(), 500, true), MediaType.APPLICATION_JSON_TYPE)).readEntity(Meal.class);
		this.assertEquals(1, meal.getId());
	}

	@Test(priority = 1)
	public void testQuery() {
		Meal[] meals = this.target.request(MediaType.APPLICATION_JSON_TYPE).get(Meal[].class);
		this.assertEquals(1, meals.length);
		this.assertEquals("forlando@gmail.com", meals[0].getEmail());
		this.assertEquals("Lunch", meals[0].getText());
		this.assertEquals(500,  meals[0].getCalories());
	}

	@Test(priority = 2)
	public void testRemove() {
		this.target.request().delete();
	}
}
