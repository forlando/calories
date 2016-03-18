package com.toptal.calories.resource;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.toptal.calories.model.User;

public class UserResourceTest extends BaseTest {

	private WebTarget target;
	
	@BeforeClass
	public void beforeClass() {
       this.target = ClientBuilder.newClient().target("http://localhost:8080/calories/rest/users/TOKEN");
	}
	
	@AfterClass
	public void afterClass() {
	}

	@Test(priority = 0)
	public void testSave() {
		User user = this.target.request().post(Entity.entity(new User("forlando@gmail.com", "Fabricio", "Damasceno", 2000, "Regular", null), MediaType.APPLICATION_JSON_TYPE)).readEntity(User.class);
		this.assertEquals("forlando@gmail.com", user.getEmail());
	}

	@Test(priority = 1)
	public void testQuery() {
		User[] users = this.target.request(MediaType.APPLICATION_JSON_TYPE).get(User[].class);
		this.assertEquals(1, users.length);
		this.assertEquals("forlando@gmail.com", users[0].getEmail());
		this.assertEquals("Fabricio", users[0].getFirstName());
		this.assertEquals("Damasceno", users[0].getLastName());
		this.assertEquals("Regular", users[0].getRole());
		this.assertEquals(2000, users[0].getDailyCalories());
		this.assertEquals(null, users[0].getMeals());
	}

	@Test(priority = 2)
	public void testRemove() {
		this.target.request().delete();
	}
}
