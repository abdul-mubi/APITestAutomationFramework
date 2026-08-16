package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerProblemDBModel;

public class CustomerProblemDao {
private static final String CUSTOMER_PROBLEM_QUERY = "SELECT * from map_job_problem where tr_job_head_id=?;";
	
	public static CustomerProblemDBModel getCustomerProblemInfo(int jobHeadId) {
		Connection conn = null;
		CustomerProblemDBModel customerProblemDBModel = null;
		try {
			conn = DatabaseManager.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(CUSTOMER_PROBLEM_QUERY);
			preparedStatement.setInt(1, jobHeadId);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				customerProblemDBModel = new CustomerProblemDBModel(rs.getInt("mst_problem_id"), rs.getString("remark"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerProblemDBModel;
		
	}

}
