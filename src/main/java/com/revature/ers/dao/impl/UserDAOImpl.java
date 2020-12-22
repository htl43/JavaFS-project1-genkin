package com.revature.ers.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import com.revature.ers.dao.UserDAO;
import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsUser;
import com.revature.ers.model.ErsUserRole;
import com.revature.ers.utilities.PostresSqlConnection;

public class UserDAOImpl implements UserDAO {
	
	private static Logger log=Logger.getLogger(UserDAOImpl.class);

	@Override
	public ErsUser loginAccount(String username, String password) throws BusinessException {
		try (Connection connection = PostresSqlConnection.getConnection()) {
			ErsUser ersUser=null;
			String sql = UserDAOImpQueries.GET_ERS_BY_USERNAME_PASSWORD;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				ersUser = new ErsUser(
						resultSet.getInt("ers_users_id"), 
						resultSet.getString("ers_username"), 
						resultSet.getString("ers_password"), 
						resultSet.getString("user_first_name"), 
						resultSet.getString("user_last_name"), 
						resultSet.getString("user_email"), 
						null);
				ErsUserRole ersUserRole = new ErsUserRole(
						resultSet.getInt("ers_user_role_id"), 
						resultSet.getString("user_role"));
				ersUser.setRole(ersUserRole);
				
			}
			return ersUser;
		} catch (ClassNotFoundException | SQLException e) {
			log.warn(e.getMessage());
			throw new BusinessException("Internal error occured.. Kindly contact SYSADMIN");
		}
	}


	@Override
	public int createAccount(ErsUser ersUser) throws BusinessException {
		try (Connection connection = PostresSqlConnection.getConnection()) {
			int c = 0;
			String sql = UserDAOImpQueries.CREATE_ERS_USER;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, ersUser.getUsername());
			preparedStatement.setString(2, ersUser.getPassword());
			preparedStatement.setString(3, ersUser.getFirstname());
			preparedStatement.setString(4, ersUser.getLastname());
			preparedStatement.setString(5, ersUser.getEmail());
			preparedStatement.setInt(6, ersUser.getRole().getUserRoleId());
			c = preparedStatement.executeUpdate();
			return c;
		} catch (ClassNotFoundException | SQLException e) {
			log.warn(e.getMessage());
			throw new BusinessException("Internal error occured. Please contact customer "
					+ "service for more imformation");
		}
	}


	@Override
	public ErsUser getUserById(int id) throws BusinessException {
		try (Connection connection = PostresSqlConnection.getConnection()) {
			ErsUser ersUser=null;
			String sql = UserDAOImpQueries.GET_ERS_BY_ID;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				ersUser = new ErsUser(
						resultSet.getInt("ers_users_id"), 
						resultSet.getString("ers_username"), 
						resultSet.getString("ers_password"), 
						resultSet.getString("user_first_name"), 
						resultSet.getString("user_last_name"), 
						resultSet.getString("user_email"), 
						null);
				ErsUserRole ersUserRole = new ErsUserRole(
						resultSet.getInt("ers_user_role_id"), 
						resultSet.getString("user_role"));
				ersUser.setRole(ersUserRole);
				
			}
			return ersUser;
		} catch (ClassNotFoundException | SQLException e) {
			log.warn(e.getMessage());
			throw new BusinessException("Internal error occured.. Kindly contact SYSADMIN");
		}
	}

}
