package com.api.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;

import static com.api.util.SpecUtil.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class LoginApiTest {
	@Test
	public void loginAPITest() {
		UserCredentials userCredentials = new UserCredentials("iamfd", "password");
		
		Response response = given()
			.spec(requestSpec(userCredentials))
		.when()
			.post("login")
		.then()
			.spec(responseSpec_OK())
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"))
			.body("message", equalTo("Success"))
			.extract().response();
		
		System.out.println(response.jsonPath().getString("message"));
	}

}
