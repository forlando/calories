package com.toptal.calories.service;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

import com.toptal.calories.model.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(locations ="/contextTest.xml")
public class LoginServiceTest extends AbstractTransactionalTestNGSpringContextTests { 

	@Autowired
	private LoginService service;
	
	@Autowired
	private UserService userService;

	@BeforeClass(dependsOnMethods={"springTestContextPrepareTestInstance"})
	public void beforeClass(ITestContext context) {
		UserEntity user = this.userService.save(null, new UserEntity("loginUser@gmail.com", "Login", "User", 2000, "Administrator"));
		context.setAttribute("loginUser", user);
	}

	@AfterClass()
	public void afterClass(ITestContext context) {
	}

	@Test(groups="login")
	public void testLogin(ITestContext context) {
		UserEntity user = (UserEntity) context.getAttribute("loginUser");
		String token = this.service.login(user);
		assertNotNull(token);
		context.setAttribute("loginToken", token);
	}

	@Test(groups="login", dependsOnMethods="testLogin")
	public void testGetLogin(ITestContext context) {
		String token = (String) context.getAttribute("loginToken");
		UserEntity user = this.service.getLoggedUser(token);
		assertNotNull(user);
	}

	@Test(groups="login", dependsOnMethods="testGetLogin")
	public void testLogout(ITestContext context) {
		String token = (String) context.getAttribute("loginToken");
		this.service.logout(token);
	}

	@Test(groups="login", dependsOnMethods="testLogout")
	public void testGetLogout(ITestContext context) {
		try {
			String token = (String) context.getAttribute("loginToken");
			UserEntity user = this.service.getLoggedUser(token);
			assertNull(user);
		} catch (SecurityException exception) {
		}
	}
}
