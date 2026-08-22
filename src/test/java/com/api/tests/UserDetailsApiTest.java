package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.util.SpecUtil.responseSpec_OK;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.service.UserService;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class UserDetailsApiTest {
	private UserService userService;
	
	@BeforeMethod
	public void setup() {
		userService = new UserService();
	}
	
	@Test(description = "Verify the user details api response is completely valid as expectec", groups= {"api","smoke","regression"})
	public void userDetailsApiTest() {
		userService.userdetails(FD)
		.then()
		.spec(responseSpec_OK())
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"))
		.body("message", equalTo("Success"))
		.extract().response();
			
	}

}
