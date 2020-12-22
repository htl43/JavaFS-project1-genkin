package com.revature.ers.dao;

import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsReimbursmentType;

public interface ReimbursmentTypeDAO {
	
	public ErsReimbursmentType getReimbursmentTypeById(int id) throws BusinessException;
}
