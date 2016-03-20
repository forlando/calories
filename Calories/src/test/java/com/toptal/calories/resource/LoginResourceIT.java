package com.toptal.calories.resource;

//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.MediaType;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginResourceIT {

//	private WebTarget target;
	
	@BeforeClass
	public void beforeClass() {
//        this.target = ClientBuilder.newClient().target("http://localhost:8080/rest/login");
	}
	
	@AfterClass
	public void afterClass() {
	}

	@Test(priority = 0)
	public void testGet() {
//		this.target.path("forlando@gmail.com").request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
	}
}
