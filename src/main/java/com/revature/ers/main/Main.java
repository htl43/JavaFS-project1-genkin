package com.revature.ers.main;

import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsUser;
import com.revature.ers.model.ErsUserRole;
import com.revature.ers.service.UserService;

public class Main {
	
	private static UserService userService = new UserService();

	public static void main(String[] args) {
//		ErsUserRole ersRole = new ErsUserRole(2, "");
//		ErsUser ersUser = new ErsUser("mng2", "mng2", "Ben", "Franklin", "benfr@gmail.com", ersRole);
//		String encrPass = userService.encryptPassword(ersUser.getPassword());
//		ersUser.setPassword(encrPass);
//		
//		 try {
//			userService.create(ersUser);
//			
//		} catch (BusinessException e) {
//			e.printStackTrace();
//		}
	}

}
