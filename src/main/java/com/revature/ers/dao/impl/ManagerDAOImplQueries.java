package com.revature.ers.dao.impl;

public class ManagerDAOImplQueries {
	
	public static final String GET_ALL_RIBS = "SELECT * FROM ers.ers_reimbursement ORDER BY reimb_id ASC";
	
	public static final String UPDATE_REIBS_BY_ID = "UPDATE ers.ers_reimbursement SET reimb_status_id=?, reimb_resolver=?,"
			+ " reimb_resolved=? WHERE reimb_id=?";
}
