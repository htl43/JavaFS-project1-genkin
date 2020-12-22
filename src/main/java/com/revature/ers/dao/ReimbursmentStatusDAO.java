package com.revature.ers.dao;

import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsReimbursmentStatus;

public interface ReimbursmentStatusDAO {
	
	public ErsReimbursmentStatus getReimbursementStatusById(int id) throws BusinessException;
}
