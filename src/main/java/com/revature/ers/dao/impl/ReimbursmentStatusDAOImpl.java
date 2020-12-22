package com.revature.ers.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.revature.ers.dao.ReimbursmentStatusDAO;
import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsReimbursmentStatus;
import com.revature.ers.service.UserService;
import com.revature.ers.utilities.PostresSqlConnection;

public class ReimbursmentStatusDAOImpl implements ReimbursmentStatusDAO {
	
	private static Logger log=Logger.getLogger(ReimbursmentStatusDAOImpl.class);
	
	@Override
	public ErsReimbursmentStatus getReimbursementStatusById(int id) throws BusinessException {
		try (Connection connection = PostresSqlConnection.getConnection()) {
			ErsReimbursmentStatus ersStatus=null;
			String sql = ReimbursmentSatusDAOImplQueries.GET_STATUS_BY_ID;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				ersStatus = new ErsReimbursmentStatus(resultSet.getInt("reimb_status_id"), resultSet.getString("reimb_status"));
			}
			return ersStatus;
		} catch (ClassNotFoundException | SQLException e) {
			log.warn(e.getMessage());
			throw new BusinessException("Internal error occured. Please contact customer "
					+ "service for more imformation");
		}
	}

}
