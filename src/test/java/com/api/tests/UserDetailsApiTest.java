package com.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import org.testng.annotations.Test;

import com.api.util.SpecUtil;

import static com.api.constant.Role.*;
import static com.api.util.AuthTokenProvider.*;
import static com.api.util.ConfigManager.*;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserDetailsApiTest {
	
	@Test
	public void userDetailsApiTest() {
		Response response = given()
				.spec(SpecUtil.requestSpecWithAuth(FD))
			.when()
				.get("userdetails")
			.then()
				.spec(SpecUtil.responseSpec_OK())
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"))
				.body("message", equalTo("Success"))
				.extract().response();
			
			System.out.println(response.jsonPath().getString("message"));
	}

}
