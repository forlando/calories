package com.toptal.calories.service;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

import com.toptal.calories.model.UserEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(locations ="/contextTest.xml")
public class UserServiceTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	private UserService service;

	@BeforeClass(dependsOnMethods={"springTestContextPrepareTestInstance"})
	public void beforeClass(ITestContext context) {
		UserEntity user = this.service.save(null, new UserEntity("user@gmail.com", "User", "Administrator", 2000, "Administrator"));
		context.setAttribute("user", user);
	}

	@AfterClass()
	public void afterClass(ITestContext context) {
	}

	@Test(groups="users")
	public void testSave(ITestContext context) {
		UserEntity user = (UserEntity) context.getAttribute("user");
		UserEntity user1 = this.service.save(user, new UserEntity("user.user@gmail.com", "User", "Administrator", 2000, "Regular"));
		assertNotNull(user1);
		assertNotNull(user1.getId());
		context.setAttribute("user1", user1);
	}

	@Test(groups="users", dependsOnMethods="testSave")
	public void testGet(ITestContext context) {
		UserEntity user = (UserEntity) context.getAttribute("user");
		UserEntity user1 = (UserEntity) context.getAttribute("user1");
		UserEntity user2 = this.service.get(user, user1.getEmail());
		assertNotNull(user2);
		assertEquals(user1.getEmail(), user2.getEmail());
		assertEquals(user1.getFirstName(), user2.getFirstName());
		assertEquals(user1.getLastName(), user2.getLastName());
		assertEquals(user1.getDailyCalories(), user2.getDailyCalories());
		assertEquals(user1.getRole(), user2.getRole());
	}

	@Test(groups="users", dependsOnMethods="testGet")
	public void testQuery(ITestContext context) {
		UserEntity user = (UserEntity) context.getAttribute("user");
		List<UserEntity> users = this.service.query(user);
		assertNotNull(users);
	}

	@Test(groups="users", dependsOnMethods="testQuery")
	public void testRemove(ITestContext context) {
		UserEntity user = (UserEntity) context.getAttribute("user");
		UserEntity user1 = (UserEntity) context.getAttribute("user1");
		this.service.remove(user, user1.getEmail());
	}
}
