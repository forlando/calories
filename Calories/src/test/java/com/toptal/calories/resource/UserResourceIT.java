package com.toptal.calories.resource;

import java.lang.reflect.Method;

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
	public void beforeClass() {
		this.target = ClientBuilder.newClient().target("http://localhost:8080/rest/users");
	}
	
	@AfterClass
	public void afterClass() {
	}

	@Test
	public void testSave(ITestContext context, Method method) {
		User user = this.target.path("NEW").request().post(Entity.entity(new User("forlando@gmail.com", "Fabricio", "Damasceno", 2000, "Regular"), MediaType.APPLICATION_JSON_TYPE)).readEntity(User.class);
		assertNotNull(user);
		assertEquals("forlando@gmail.com", user.getEmail());
		context.setAttribute("user", user);
	}

	@Test(dependsOnMethods="testGet(com.toptal.calories.resource.LoginResourceIT)")
	public void testQuery(ITestContext context, Method method) {
		LoggedUser loggedUser = (LoggedUser) context.getAttribute("loggedUser");
		User[] users = this.target.path(loggedUser.getToken()).request(MediaType.APPLICATION_JSON_TYPE).get(User[].class);
		assertEquals(1, users.length);
		assertEquals("forlando@gmail.com", users[0].getEmail());
		assertEquals("Fabricio", users[0].getFirstName());
		assertEquals("Damasceno", users[0].getLastName());
		assertEquals("Regular", users[0].getRole());
		assertEquals(2000, users[0].getDailyCalories().intValue());
	}
}
