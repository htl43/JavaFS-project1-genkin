package com.revature.ers.main;

import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsUser;
import com.revature.ers.model.ErsUserRole;
import com.revature.ers.service.UserService;

public class Main {
	
	private static UserService userService = new UserService();

	public static void main(String[] args) {
		ErsUserRole ersRole = new ErsUserRole(1, "");
		ErsUser ersUser = new ErsUser("emp2", "emp2", "Maria", "Garcia", "maga@outlook.com", ersRole);
		
		ErsUserRole mngR = new ErsUserRole(2, "");
		ErsUser mng1 = new ErsUser("mng1", "mng1", "Hieu", "Le", "hile@gmail.com", mngR);
		ErsUser mng2 = new ErsUser("mng2", "mng2", "Thomas", "Brown", "thobr@yahoo.com", mngR);
		 try {
			userService.create(ersUser);
			userService.create(mng1);
			userService.create(mng2);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

}
