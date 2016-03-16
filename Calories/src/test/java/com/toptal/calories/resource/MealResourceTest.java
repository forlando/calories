package com.toptal.calories.resource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.toptal.calories.model.Meal;

public class MealResourceTest {

	private WebTarget target;
	
	@BeforeClass
	public void beforeClass() {
        this.target = ClientBuilder.newClient().target("http://localhost:8080/calories/rest/meals");
	}
	
	@AfterClass
	public void afterClass() {
	}

	@Test(priority = 0)
	public void testCreateUpdate() {
		Meal meal = this.target.request().post(Entity.entity(new Meal("Lunch", new Date(), new Date(), 500), MediaType.APPLICATION_JSON_TYPE)).readEntity(Meal.class);
		this.assertEquals(1, meal.getId());
	}

	@Test(priority = 1)
	public void testList() {
		Meal[] meals = this.target.request(MediaType.APPLICATION_JSON_TYPE).get(Meal[].class);
		this.assertEquals(1, meals.length);
		this.assertEquals(1, meals[0].getId());
		this.assertEquals("Lunch", meals[0].getText());
		this.assertEquals("16/03/2016",  meals[0].getDate());
		this.assertEquals("13:00:00",  meals[0].getTime());
		this.assertEquals(500,  meals[0].getCalories());
	}

	@Test(priority = 2)
	public void testDelete() {
		// this.target.request().delete();
	}

	private void assertEquals(Object expected, Object actual) {
		boolean value = expected.equals(actual);
		assert value;
	}
}
