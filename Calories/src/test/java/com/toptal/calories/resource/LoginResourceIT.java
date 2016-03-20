package com.toptal.calories.resource;

import java.lang.reflect.Method;

import org.testng.ITestContext;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.toptal.calories.model.LoggedUser;
import com.toptal.calories.model.User;

import static org.testng.AssertJUnit.*;

public class LoginResourceIT {

	private WebTarget target;
	
	@BeforeClass
	public void beforeClass() {
        this.target = ClientBuilder.newClient().target("http://localhost:8080/rest/login");
	}
	
	@AfterClass
	public void afterClass() {
	}

	@Test(dependsOnMethods="testSave(com.toptal.calories.resource.UserResourceIT)")
	public void testGet(ITestContext context, Method method) {
		User user = (User) context.getAttribute("user");
		LoggedUser loggedUser = this.target.path(user.getEmail()).request(MediaType.APPLICATION_JSON_TYPE).get(LoggedUser.class);
		assertNotNull(loggedUser);
		assertNotNull(loggedUser.getToken());
		context.setAttribute("loggedUser", loggedUser);
	}
}
