package com.api.service;

import static com.api.util.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import com.api.constant.Role;

import io.restassured.response.Response;

public class UserService {
	private static final String USER_DETAILS_ENDPOINT = "/userdetails";
	
	public Response userdetails(Role role) {
		Response response  = given()
							.spec(requestSpecWithAuth(role))
							.when()
							.get(USER_DETAILS_ENDPOINT);
		return response;
	}

}
