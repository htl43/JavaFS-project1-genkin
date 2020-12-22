package com.revature.ers.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.revature.ers.dao.impl.ManagerDAOImpl;
import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsReimbursment;
import com.revature.ers.model.ErsReimbursmentStatus;
import com.revature.ers.model.ErsReimbursmentType;
import com.revature.ers.model.ErsUser;

class ManagerServiceTest {
	
	private static Logger log=Logger.getLogger(ManagerServiceTest.class);
	private static ManagerService managerService;
	private static UserService userService;
	private static EmployeeService employeeService;
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		managerService = new ManagerService();
		userService = new UserService();
		employeeService = new EmployeeService();
	}
	

	@Test
	void testValidGetAllRibs() {
		try {
			ErsUser mngUser = userService.login("mng1", "mng1");
			assertNotNull(mngUser);
			assertTrue(mngUser.getRole().getUserRole().equals("MANAGER"));
			List<ErsReimbursment> listRibs = managerService.getAllRibs();		
			assertTrue(listRibs.size()>0);
		} catch (BusinessException e) {
			assertNotNull(e);
		}
	}
	

	@Test
	void testValidChangeRibStatusById() {
		try {
			ErsUser empUser = userService.login("emp1", "emp1");
			assertNotNull(empUser);
			assertTrue(empUser.getRole().getUserRole().equals("EMPLOYEE"));
			ErsReimbursmentStatus ribStatus = new ErsReimbursmentStatus(100);
			ErsReimbursmentType ribStype = new ErsReimbursmentType(1);
			ErsReimbursment rib = new ErsReimbursment(300, null, null, "Buy Food", empUser, null, ribStatus, ribStype);
			int c = employeeService.submitErsReibursment(rib);
			assertTrue(c>0);
			// get the new submit of Rib which is last element return in the List of Ribs
			List<ErsReimbursment> listR = employeeService.getRibByEmpId(empUser.getUserId());
			ErsReimbursment lastR = listR.get(listR.size()-1);
			System.out.println("lastR=" + lastR);
			assertEquals(100, lastR.getStatus().getStatusId());
			
			//Set new status for new this rib
			lastR.getStatus().setStatusId(101);
			// Set the resolver for this rib
			ErsUser mngUser = userService.login("mng1", "mng1");
			assertNotNull(mngUser);
			assertTrue(mngUser.getRole().getUserRole().equals("MANAGER"));
			lastR.setResolver(mngUser);;
			c = managerService.changeRibStatusById(lastR);
			assertTrue(c>0);
		} catch (BusinessException e) {
			log.warn(e.getMessage());
			assertNull(e);
		}
	}

}
