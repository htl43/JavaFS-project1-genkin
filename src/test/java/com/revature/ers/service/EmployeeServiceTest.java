package com.revature.ers.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsReimbursment;
import com.revature.ers.model.ErsReimbursmentStatus;
import com.revature.ers.model.ErsReimbursmentType;
import com.revature.ers.model.ErsUser;

class EmployeeServiceTest {
	
	private static EmployeeService employeeService;
	private static UserService userService;
	
	@BeforeAll
	public static void createEmployeeService() {
		employeeService = new EmployeeService();
		userService = new UserService();
	}

	@Test
	void testValidSubmitErsReibursment() {
		try {
			ErsUser empUser = userService.login("emp1", "emp1");
			assertNotNull(empUser);
			assertTrue(empUser.getRole().getUserRole().equals("EMPLOYEE"));
			ErsReimbursmentStatus ribStatus = new ErsReimbursmentStatus(100);
			ErsReimbursmentType ribStype = new ErsReimbursmentType(1);
			ErsReimbursment rib = new ErsReimbursment(100, null, null, "something", empUser, null, ribStatus, ribStype);
			int c = employeeService.submitErsReibursment(rib);
			assertTrue(c>0);
		} catch (BusinessException e) {
			assertNull(e);
		}
	}
	
	@Test
	void testInvalidSubmitErsReibursment() {
		try {
			ErsUser empUser = userService.login("emp1", "emp1");
			assertNotNull(empUser);
			assertTrue(empUser.getRole().getUserRole().equals("EMPLOYEE"));
			ErsReimbursmentStatus ribStatus = new ErsReimbursmentStatus(100);
			ErsReimbursmentType ribStype = new ErsReimbursmentType(1);
			ErsReimbursment rib = new ErsReimbursment(-100, null, null, "something", empUser, null, ribStatus, ribStype);
			int c = employeeService.submitErsReibursment(rib);
			assertTrue(c<0);
		} catch (BusinessException e) {
			assertNotNull(e);
		}
	}

	@Test
	void testValidGetRibByEmpId() {
		try {
			ErsUser empUser = userService.login("emp1", "emp1");
			assertNotNull(empUser);
			assertTrue(empUser.getRole().getUserRole().equals("EMPLOYEE"));
			List<ErsReimbursment> listRibs = employeeService.getRibByEmpId(empUser.getUserId());		
			assertTrue(listRibs.size()>0);
		} catch (BusinessException e) {
			assertNull(e);
		}
	}
	
	@Test
	void testInvalidGetRibByEmpId() {
		try {
			int fakeUserId = 999;
			List<ErsReimbursment> listRibs = employeeService.getRibByEmpId(999);		
			assertNull(listRibs);
		} catch (BusinessException e) {
			assertNotNull(e);
		}
	}

}
