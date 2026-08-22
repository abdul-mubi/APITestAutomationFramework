package com.api.service;

import static com.api.util.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Role;

import io.restassured.response.Response;

public class JobService {
	private static final String CREATE_JOB_API_END_POINT = "/job/create";
	private static final Logger LOGGER = LogManager.getLogger(JobService.class);
	
	public Response create(Role role, Object payload) {
		LOGGER.info("Making Job Create POST request for this role - {} with this payload - {}", role, payload);
		Response response = given()
		.spec(requestSpecWithAuth(role,payload))
		.when()
		.post(CREATE_JOB_API_END_POINT);
		return response;
	}

}
