package com.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.database.DatabaseManager;
import com.dataproviders.api.bean.CreateJobBean;

public class CreateJobPayloadDataDao {
	private static final String SQL_QUERY = 
			"""
			SELECT 
				jh.mst_service_location_id,
				jh.mst_platform_id,
				jh.mst_warrenty_status_id,
				jh.mst_oem_id,
				c.first_name,
				c.last_name,
				c.mobile_number,
				c.mobile_number_alt,
				c.email_id,
				c.email_id_alt,
				ca.flat_number,
				ca.apartment_name,
				ca.street_name,
				ca.landmark,
				ca.area,
				ca.pincode,
				ca.country,
				ca.state,
				cp.mst_model_id,
				cp.dop,
				cp.popurl,
				cp.imei2,
				cp.imei1,
				cp.serial_number,
				jp.mst_problem_id,
				jp.remark
				FROM tr_customer as c
				inner join tr_customer_address as ca
				on c.tr_customer_address_id = ca.id
				
				inner join tr_customer_product as cp
				on cp.tr_customer_id = c.id
				
				inner join tr_job_head as jh
				on c.id = jh.tr_customer_id
				
				inner join map_job_problem as jp
				on jp.tr_job_head_id = jh.id
				
				limit 5;
			
			""";
	
	public static List<CreateJobBean> getCreateJobPayloadData(){
		Connection conn = null;
		ResultSet rs = null;
		List<CreateJobBean> beanList = new ArrayList<CreateJobBean>();
		try {
			conn = DatabaseManager.getConnection();
			rs = conn.createStatement().executeQuery(SQL_QUERY);
			while(rs.next()) {
				CreateJobBean bean = new CreateJobBean();
				bean.setMst_service_location_id(rs.getString("mst_service_location_id"));
				bean.setMst_service_location_id(rs.getString("mst_service_location_id"));
				bean.setMst_platform_id(rs.getString("mst_platform_id"));
				bean.setMst_warrenty_status_id(rs.getString("mst_warrenty_status_id"));
				bean.setMst_oem_id("1");
				bean.setCustomer__first_name(rs.getString("first_name"));        
				bean.setCustomer__last_name(rs.getString("last_name"));
				bean.setCustomer__mobile_number(rs.getString("mobile_number"));
				bean.setCustomer__mobile_number_alt(rs.getString("mobile_number_alt"));
				bean.setCustomer__email_id(rs.getString("email_id"));
				bean.setCustomer__email_id_alt(rs.getString("email_id_alt"));
				bean.setCustomer_address__flat_number(rs.getString("flat_number"));
				bean.setCustomer_address__apartment_name(rs.getString("apartment_name"));
				bean.setCustomer_address__street_name(rs.getString("street_name"));
				bean.setCustomer_address__landmark(rs.getString("landmark"));
				bean.setCustomer_address__area(rs.getString("area"));
				bean.setCustomer_address__pincode(rs.getString("pincode"));
				bean.setCustomer_address__country(rs.getString("country"));
				bean.setCustomer_address__state(rs.getString("state"));
				bean.setCustomer_product__product_id("1");
				bean.setCustomer_product__dop(rs.getString("dop"));
				bean.setCustomer_product__popurl(rs.getString("popurl"));
				bean.setCustomer_product__imei2(rs.getString("imei2"));
				bean.setCustomer_product__imei1(rs.getString("imei1"));
				bean.setCustomer_product__serial_number(rs.getString("serial_number"));
				bean.setProblems__id(rs.getString("mst_problem_id"));
				bean.setProblems__remark(rs.getString("remark"));
				bean.setCustomer_product__mst_model_id("1");
				beanList.add(bean);

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beanList;	
	}

}
