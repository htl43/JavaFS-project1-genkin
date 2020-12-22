package com.revature.ers.dao;

import java.util.List;

import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsUserRole;


public interface UserRoleDAO {
	
	public boolean createRole(String roleName) throws BusinessException;
	public ErsUserRole getRoleById(int id) throws BusinessException;
	public List<ErsUserRole> getAllRole() throws BusinessException;
}
