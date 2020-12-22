package com.revature.ers.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.ers.dao.ManagerDAO;
import com.revature.ers.dao.ReimbursmentStatusDAO;
import com.revature.ers.dao.ReimbursmentTypeDAO;
import com.revature.ers.exception.BusinessException;
import com.revature.ers.model.ErsReimbursment;
import com.revature.ers.model.ErsReimbursmentStatus;
import com.revature.ers.model.ErsReimbursmentType;
import com.revature.ers.model.ErsUser;
import com.revature.ers.utilities.PostresSqlConnection;

public class ManagerDAOImpl implements ManagerDAO {
	
	private static Logger log=Logger.getLogger(ManagerDAOImpl.class);
	private ReimbursmentTypeDAO rbsType = new ReimbursmentTypeDAOImpl();
	private ReimbursmentStatusDAO rbsStatus = new ReimbursmentStatusDAOImpl();

	@Override
	public List<ErsReimbursment> getAllRibs() throws BusinessException {
		List<ErsReimbursment> listR = new ArrayList<>();
		try (Connection connection = PostresSqlConnection.getConnection()) {	
			String sql = ManagerDAOImplQueries.GET_ALL_RIBS;
			PreparedStatement preSt = connection.prepareStatement(sql);
			ResultSet resultSet = preSt.executeQuery();
			while(resultSet.next()) {
				ErsReimbursment reimb = new ErsReimbursment(
						resultSet.getInt("reimb_id"), 
						resultSet.getDouble("reimb_amount"), 
						resultSet.getTimestamp("reimb_submitted"), 
						resultSet.getTimestamp("reimb_resolved"), 
						resultSet.getString("reimb_description"), 
						null, 
						null, 
						null, 
						null
						);
				if(resultSet.getInt("reimb_author")!=0) {
					ErsUser author = new ErsUser();
					author.setUserId(resultSet.getInt("reimb_author"));
					reimb.setAuthor(author);
				}
				if(resultSet.getInt("reimb_resolver")!=0) {
					ErsUser resolver = new ErsUser();
					resolver.setUserId(resultSet.getInt("reimb_resolver"));
					reimb.setResolver(resolver);
				}
				if(resultSet.getInt("reimb_type_id")!=0) {
					ErsReimbursmentType reimbType = rbsType.getReimbursmentTypeById(resultSet.getInt("reimb_type_id"));
					reimb.setType(reimbType);
				}
				if(resultSet.getInt("reimb_status_id")!=0) {
					ErsReimbursmentStatus reimbStatus = rbsStatus.getReimbursementStatusById(resultSet.getInt("reimb_status_id"));
					reimb.setStatus(reimbStatus);
				}			
				listR.add(reimb);
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.warn(e.getMessage());
			throw new BusinessException("Internal error occured. Please contact customer "
					+ "service for more imformation");
		}
		return listR;
	}

	@Override
	public int UpdateRibStatusById(int statusId, int ribId, int resolverId) throws BusinessException {
		int c = 0;
		try (Connection connection = PostresSqlConnection.getConnection()) {
			long timeNow = System.currentTimeMillis();
			Timestamp curreTimestamp = new Timestamp(timeNow);
			String sql = ManagerDAOImplQueries.UPDATE_REIBS_BY_ID;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, statusId);
			preparedStatement.setInt(2, resolverId);
			preparedStatement.setTimestamp(3, curreTimestamp);
			preparedStatement.setInt(4, ribId);		
			c = preparedStatement.executeUpdate();	
		} catch (ClassNotFoundException | SQLException e) {
			log.warn(e.getMessage());
			throw new BusinessException("Internal error occured. Please contact customer "
					+ "service for more imformation");
		}	
		return c;
	}

}
