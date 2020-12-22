package com.revature.ers.dao.impl;

public class UserDAOImpQueries {
	
	public static final String CREATE_ERS_USER = "INSERT INTO ers.ers_users (ers_username, ers_password, "
			+ "user_first_name, user_last_name, user_email, user_role_id) VALUES(?,?,?,?,?,?)";
	
	public static final String GET_ERS_BY_USERNAME_PASSWORD = "SELECT * FROM ers.ers_users eu JOIN ers.ers_user_roles eur "
			+ "ON eu.user_role_id = eur.ers_user_role_id "
			+ "WHERE eu.ers_username=? and eu.ers_password=?;";
	
	public static final String GET_ERS_BY_ID = "SELECT * FROM ers.ers_users WHERE ers_user_id=?";
	
}
