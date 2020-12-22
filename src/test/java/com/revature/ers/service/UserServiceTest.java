package com.revature.ers.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.revature.ers.dao.UserRoleDAO;
import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsUser;
import com.revature.ers.model.ErsUserRole;

class UserServiceTest {
	
	private static UserService userService;
	
	@BeforeAll
	public static void createUserService() {
		userService = new UserService();
	}

	@Test
	void testValidLogin() {
		try {
			ErsUser empUser = userService.login("emp1", "emp1");
			assertNotNull(empUser);
			assertTrue(empUser.getRole().getUserRole().equals("EMPLOYEE"));
			ErsUser manUser = userService.login("mng1", "mng1");
			assertTrue(manUser.getRole().getUserRoleId()==2);
			assertNotNull(manUser);
		} catch (BusinessException e) {
			assertNull(e);
		}
		try {
			ErsUser manUser = userService.login("mng1", "mng1");
			assertTrue(manUser.getRole().getUserRole().equals("MANAGER"));
			assertNotNull(manUser);
		} catch (BusinessException e) {
			assertNull(e);
		}
	}
	
	@Test
	void testInvalidLogin() {
		try {
			ErsUser invalidUser = userService.login("", "");
			assertNull(invalidUser);
		} catch (BusinessException e) {
			assertNotNull(e);
		}
		try {
			ErsUser invalidUser = userService.login("emp1", "invalid-password");
			assertNull(invalidUser);
		} catch (BusinessException e) {
			assertNotNull(e);
		}
		try {
			ErsUser invalidUser = userService.login("mng1", "invalid-password");
			assertNull(invalidUser);
		} catch (BusinessException e) {
			assertNotNull(e);
		}
	}

	@Test
	void testValidCreate() {
		Random rand = new Random();
		String username = "test" + rand.nextInt(9999);
		String email = "test" + rand.nextInt(9999) + "@gmail.com";
		ErsUserRole userRole = new ErsUserRole(1, "");
		ErsUser newUser = new ErsUser(username, "test-password", "testerfn", "testerln", email, userRole);
		try {
			int c = userService.create(newUser);
			assertTrue(c>0);
		} catch (BusinessException e) {
			assertNull(e);
		}
	}
	
	@Test
	void testInvalidCreate() {
		ErsUserRole userRole = new ErsUserRole(1, "");
		//test for username emp1 is already exist
		ErsUser newUser = new ErsUser("emp1", "test-password", "tester", "tester", "testEmail@gmail.com", userRole);
		try {
			int c = userService.create(newUser);
		} catch (BusinessException e) {
			assertNotNull(e);
		}
	}

	@Test
	void testEncryptPassword() {
		String password = "testing-password";
		String ecrPassword = userService.encryptPassword(password);
		assertNotEquals(ecrPassword, password);
	}

}
