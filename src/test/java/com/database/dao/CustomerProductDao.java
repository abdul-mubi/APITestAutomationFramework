package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerProductDBModel;

public class CustomerProductDao {
	private static final String CUSTOMER_PRODUCT_QUERY = "SELECT * from tr_customer_product where tr_customer_id=?;";
	
	public static CustomerProductDBModel getCustomerProductInfo(int customerId) {
		Connection conn = null;
		CustomerProductDBModel customerProductDBModel = null;
		try {
			conn = DatabaseManager.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(CUSTOMER_PRODUCT_QUERY);
			preparedStatement.setInt(1, customerId);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				customerProductDBModel = new CustomerProductDBModel(rs.getString("dop"), rs.getString("serial_number"), rs.getString("imei1"), rs.getString("imei2"), rs.getString("popurl"), rs.getInt("mst_model_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerProductDBModel;
		
	}
}
