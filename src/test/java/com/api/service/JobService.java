package com.api.service;

import static com.api.util.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.*;

import com.api.constant.Role;

import io.restassured.response.Response;

public class JobService {
	private static final String CREATE_JOB_API_END_POINT = "/job/create";
	
	public Response create(Role role, Object payload) {
		Response response = given()
		.spec(requestSpecWithAuth(role,payload))
		.when()
		.post(CREATE_JOB_API_END_POINT);
		return response;
	}

}
