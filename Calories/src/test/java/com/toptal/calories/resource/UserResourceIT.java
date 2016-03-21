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
import com.toptal.calories.model.User;

import static org.testng.AssertJUnit.*;

public class UserResourceIT {

	private WebTarget target; 

	@BeforeClass
	public void beforeClass(ITestContext context) {
		this.target = ClientBuilder.newClient().target("http://localhost:8080/rest/users");
	}
	
	@AfterClass
	public void afterClass(ITestContext context) {
	}

	@Test(groups="saveUser")
	public void testSave(ITestContext context) {
		User user = this.target.path("NEW").request().post(Entity.entity(new User("forlando@gmail.com", "Fabricio", "Damasceno", 2000, "Administrator"), MediaType.APPLICATION_JSON_TYPE)).readEntity(User.class);
		assertNotNull(user);
		assertEquals("forlando@gmail.com", user.getEmail());
		context.setAttribute("user", user);
	}

	@Test(groups="loadUsers", dependsOnGroups="saveUser")
	public void testQuery(ITestContext context) {
		LoggedUser loggedUser = (LoggedUser) context.getAttribute("loggedUser");
		User[] users = this.target.path(loggedUser.getToken()).request(MediaType.APPLICATION_JSON_TYPE).get(User[].class);
		assertEquals(1, users.length);
		assertEquals("forlando@gmail.com", users[0].getEmail());
		assertEquals("Fabricio", users[0].getFirstName());
		assertEquals("Damasceno", users[0].getLastName());
		assertEquals("Administrator", users[0].getRole());
		assertEquals(2000, users[0].getDailyCalories().intValue());
	}
}
