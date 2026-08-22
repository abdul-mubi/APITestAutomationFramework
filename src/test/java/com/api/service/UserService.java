package com.api.service;

import static com.api.util.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Role;

import io.restassured.response.Response;

public class UserService {
	private static final String USER_DETAILS_ENDPOINT = "/userdetails";
	private static final Logger LOGGER = LogManager.getLogger(UserService.class);
	
	public Response userdetails(Role role) {
		LOGGER.info("Making userdetails GET request for this role - {}",role);
		Response response  = given()
							.spec(requestSpecWithAuth(role))
							.when()
							.get(USER_DETAILS_ENDPOINT);
		return response;
	}

}
