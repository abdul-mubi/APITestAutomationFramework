package com.api.service;

import static com.api.util.SpecUtil.*;
import static io.restassured.RestAssured.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Role;

import io.restassured.response.Response;

public class DashboardService {
	private static final String COUNT_ENDPOINT = "/dashboard/count";
	private static final Logger LOGGER = LogManager.getLogger(DashboardService.class);
	
	public Response count(Role role) {
		LOGGER.info("Making count GET request");
		Response response = given()
							.spec(requestSpecWithAuth(role))
							.when()
							.get(COUNT_ENDPOINT);
		return response;
	}	
	
	public Response countWithoutAuth() {
		LOGGER.info("Making count GET request without Auth");
		Response response = given()
							.spec(requestSpec())
							.when()
							.get(COUNT_ENDPOINT);
		return response;
	}

}
