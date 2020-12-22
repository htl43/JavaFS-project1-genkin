package com.revature.ers.service;

import org.apache.log4j.Logger;

import com.revature.ers.dao.UserDAO;
import com.revature.ers.dao.impl.UserDAOImpl;
import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsUser;

public class UserService {
	
	private static Logger log=Logger.getLogger(UserService.class);
	private UserDAO userDAO = new UserDAOImpl();
	
	public ErsUser login(String username, String password) throws BusinessException {
		ErsUser ersUser=null;
		if(username.equals("") || password.equals("")) {
			throw new BusinessException("Invalid values!! Username/Password must not be blank");
		} else {
			password = encryptPassword(password);
			ersUser = userDAO.loginAccount(username, password);
			if(ersUser!=null) {
				return ersUser;
			} else {
				throw new BusinessException("System can't find any account which associate with username and password");
			}
		}	
		
	}

	public int create(ErsUser ersUser) throws BusinessException {
		if(ersUser.getUsername().equals("") || ersUser.getPassword().equals("") || ersUser.getEmail().equals("")) {
			throw new BusinessException("Invalid values! Username/Password/Email must not be blank");
		} else {
			int c = userDAO.createAccount(ersUser);
			if(c>0) {
				return c;
			} else {
				throw new BusinessException("System can't create your account. Please check again for the field requirements");
			}
		}
		
	}

	public String encryptPassword(String password) {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		String newPassword = String.valueOf(result);
		return newPassword;
	}
}
