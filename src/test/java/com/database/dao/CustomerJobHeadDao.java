package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerJobHeadDBModel;

public class CustomerJobHeadDao {
private static final String CUSTOMER_JOB_HEAD_QUERY = "SELECT * from tr_job_head where tr_customer_id=?;";
	
	public static CustomerJobHeadDBModel getCustomerJobHeadInfo(int customerId) {
		Connection conn = null;
		CustomerJobHeadDBModel customerJobHeadDBModel = null;
		try {
			conn = DatabaseManager.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(CUSTOMER_JOB_HEAD_QUERY);
			preparedStatement.setInt(1, customerId);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				customerJobHeadDBModel = new CustomerJobHeadDBModel(rs.getInt("mst_service_location_id"), rs.getInt("mst_platform_id"), rs.getInt("mst_warrenty_status_id"), rs.getInt("mst_oem_id"), rs.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerJobHeadDBModel;
		
	}
}
