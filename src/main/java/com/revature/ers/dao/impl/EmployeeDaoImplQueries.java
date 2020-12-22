package com.revature.ers.dao.impl;

public class EmployeeDaoImplQueries {
	
	public static final String CREATE_ERS_REIMBURSEMENT = "INSERT INTO ers.ers_reimbursement (reimb_amount, reimb_submitted, reimb_description,"
			+ " reimb_author, reimb_status_id, reimb_type_id) VALUES (?, ?, ?, ?, ?, ?)";
	
	public static final String GET_RIB_BY_ID = "SELECT * FROM ers.ers_reimbursement WHERE reimb_author=? ORDER BY reimb_id ASC";
}
