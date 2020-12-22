package com.revature.ers.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.ers.dao.ReimbursmentStatusDAO;
import com.revature.ers.dao.ReimbursmentTypeDAO;
import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsReimbursmentStatus;
import com.revature.ers.model.ErsReimbursmentType;
import com.revature.ers.utilities.PostresSqlConnection;

public class ReimbursmentTypeDAOImpl implements ReimbursmentTypeDAO {

	private static Logger log=Logger.getLogger(ReimbursmentTypeDAOImpl.class);

	@Override
	public ErsReimbursmentType getReimbursmentTypeById(int id) throws BusinessException {
		try (Connection connection = PostresSqlConnection.getConnection()) {
			ErsReimbursmentType ersType=null;
			String sql = ReimbursmentTypeDAOImplQueries.GET_TYPE_BY_ID;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				ersType = new ErsReimbursmentType(resultSet.getInt("reimb_type_id"), resultSet.getString("reimb_type"));
			}
			return ersType;
		} catch (ClassNotFoundException | SQLException e) {
			log.warn(e.getMessage());
			throw new BusinessException("Internal error occured. Please contact customer "
					+ "service for more imformation");
		}
	}
	
	
}
