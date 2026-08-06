package com.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;

import static com.api.util.SpecUtil.*;

import static com.api.constant.Role.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class UserDetailsApiTest {
	
	@Test(description = "Verify the user details api response is completely valid as expectec", groups= {"api","smoke","regression"})
	public void userDetailsApiTest() {
		Response response = given()
				.spec(requestSpecWithAuth(FD))
			.when()
				.get("userdetails")
			.then()
				.spec(responseSpec_OK())
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"))
				.body("message", equalTo("Success"))
				.extract().response();
			
			System.out.println(response.jsonPath().getString("message"));
	}

}
