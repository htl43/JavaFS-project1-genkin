package com.revature.ers.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.ers.dao.EmployeeDAO;
import com.revature.ers.dao.impl.EmployeeDaoImpl;
import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsReimbursment;

public class EmployeeService {
	
	private static Logger log=Logger.getLogger(EmployeeService.class);
	private EmployeeDAO employeeDAO = new EmployeeDaoImpl();

	public int submitErsReibursment(ErsReimbursment ersReimbursement) throws BusinessException {
		if(ersReimbursement.getAuthor()==null ) {
			throw new BusinessException ("Submition Failed! Null sender!");
		} else if (ersReimbursement.getAmount()<0){
			throw new BusinessException ("Invalid value for the amount= " + ersReimbursement.getAmount() + 
					". The amount must not be negative number");
		} else {
			ersReimbursement.getStatus().setStatusId(100); // Set default id=100 for pending status
			int c = employeeDAO.createReimbursement(ersReimbursement);
			if(c>0) {
				return c;
			} else {
				throw new BusinessException ("System can't create the reimbursment. Please check again for the field requirements");
			}
		}
	}

	public List<ErsReimbursment> getRibByEmpId(int userId) throws BusinessException {
		List<ErsReimbursment> listR = employeeDAO.getRibByEmpId(userId);
		if(listR.size()==0) {
			throw new BusinessException("No Reimbursment Record Found for This User");
		}
		return listR;
	}

	
}
