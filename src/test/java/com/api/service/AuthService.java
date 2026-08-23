package com.api.service;

import static com.api.util.SpecUtil.requestSpec;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class AuthService {
	private static final String LOGIN_ENDPOINT = "/login";
	private static final Logger LOGGER = LogManager.getLogger(AuthService.class);
	
	
	@Step("Making login resquest with valid credentials")
	public Response login(Object userCredentials) {
		LOGGER.info("Making login request using this payload - {}",userCredentials);
		Response response = given()
				.spec(requestSpec(userCredentials))
				.when()
				.post(LOGIN_ENDPOINT);
		return response;
	}
}
