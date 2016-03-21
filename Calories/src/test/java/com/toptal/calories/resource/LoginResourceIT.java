package com.toptal.calories.resource;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;


import com.toptal.calories.model.LoggedUser;
import com.toptal.calories.model.User;
public class LoginResourceIT {

	private WebTarget target; 

	@BeforeClass
	public void beforeClass(ITestContext context) {
		this.target = ClientBuilder.newClient().target("http://localhost:8080/rest/login");
	}
	
	@AfterClass
	public void afterClass(ITestContext context) {
	}

	@Test(groups="login", dependsOnGroups="saveUser")
	public void testGet(ITestContext context) {
		User user = (User) context.getAttribute("user");
		LoggedUser loggedUser = this.target.path(user.getEmail()).request(MediaType.APPLICATION_JSON_TYPE).get(LoggedUser.class);
		assertNotNull(loggedUser);
		assertNotNull(loggedUser.getToken());
		context.setAttribute("loggedUser", loggedUser);
	}
}
