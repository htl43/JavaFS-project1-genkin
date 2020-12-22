package com.revature.ers.service;

import java.util.List;

import com.revature.ers.dao.ManagerDAO;
import com.revature.ers.dao.impl.ManagerDAOImpl;
import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsReimbursment;

public class ManagerService {
	
	private ManagerDAO managerDAO = new ManagerDAOImpl();

	public List<ErsReimbursment> getAllRibs() throws BusinessException {
		List<ErsReimbursment> listR = managerDAO.getAllRibs();
		if(listR.size()==0) {
			throw new BusinessException("No Reimbursement Record Found in System");
		}
		return listR;
	}

	public int changeRibStatusById(ErsReimbursment rib) throws BusinessException {
		if(rib.getReimbId()<=0) {
			throw new BusinessException("Invalid of Sending Reimbursement Id");
		} else {
			int c = managerDAO.UpdateRibStatusById(rib.getStatus().getStatusId(), rib.getReimbId(), rib.getResolver().getUserId());
			if(c<=0) {
				throw new BusinessException("Record not found. Unable to update status for the reimbursement");
			}
			return c;
		}
		
	}

}
