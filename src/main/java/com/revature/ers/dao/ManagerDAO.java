package com.revature.ers.dao;

import java.util.List;

import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsReimbursment;

public interface ManagerDAO {

	public List<ErsReimbursment> getAllRibs() throws BusinessException;

	public int UpdateRibStatusById(int statusId, int ribId, int resolverId) throws BusinessException;
}
