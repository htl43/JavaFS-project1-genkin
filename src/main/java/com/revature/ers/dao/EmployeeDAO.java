package com.revature.ers.dao;

import java.util.List;

import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsReimbursment;

public interface EmployeeDAO {

	public int createReimbursement(ErsReimbursment ersReimbursement) throws BusinessException;

	public List<ErsReimbursment> getRibByEmpId(int userId) throws BusinessException;
	
}
