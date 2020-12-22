package com.revature.ers.dao.impl;

public class UserRoleDAOImplQueries {
	
	public static final String CREATE_ERS_USER_ROLE = "INSERT INTO ers.ers_users_roles (user_role) VALUES(?)";
	
	public static final String GET_ERS_USER_ROLE_BY_ID = "SELECT * FROM ers.ers_users_roles eur WHERE eur.ers_user_role_id=?";
}
