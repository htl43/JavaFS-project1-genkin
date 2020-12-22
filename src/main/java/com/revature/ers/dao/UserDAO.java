package com.revature.ers.dao;

import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsUser;
import com.revature.ers.model.ErsUserRole;

public interface UserDAO {
	
	public ErsUser loginAccount(String username, String password) throws BusinessException;
	public int createAccount(ErsUser ersUser) throws BusinessException;
	public ErsUser getUserById(int id) throws BusinessException;
}
