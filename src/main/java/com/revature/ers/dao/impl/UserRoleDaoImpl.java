package com.revature.ers.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.ers.dao.UserRoleDAO;
import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsUserRole;
import com.revature.ers.utilities.PostresSqlConnection;

public class UserRoleDaoImpl implements UserRoleDAO {
	
	private static Logger log=Logger.getLogger(UserRoleDaoImpl.class);

	@Override
	public boolean createRole(String roleName) throws BusinessException {
		return false;
	}

	@Override
	public ErsUserRole getRoleById(int id) throws BusinessException {
		try (Connection connection = PostresSqlConnection.getConnection()) {
			String sql = UserRoleDAOImplQueries.GET_ERS_USER_ROLE_BY_ID;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				ErsUserRole ersUserRole;
				ersUserRole = new ErsUserRole(resultSet.getInt("ers_user_role_id"), resultSet.getString("user_role"));
				return ersUserRole;
			}else {
				throw new BusinessException("Sorry. System can't find any record that matchs with user role id= " +id);
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.warn(e.getMessage());
			throw new BusinessException("Internal error occured. Please contact customer "
					+ "service for more imformation");
		}
	}

	@Override
	public List<ErsUserRole> getAllRole() throws BusinessException {
		return null;
	}

}
