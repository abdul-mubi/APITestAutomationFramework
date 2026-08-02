package com.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import org.testng.annotations.Test;
import static com.api.constant.Role.*;
import static com.api.util.AuthTokenProvider.*;
import static com.api.util.ConfigManager.*;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class UserDetailsApiTest {
	
	@Test
	public void userDetailsApiTest() {
		Header header = new Header("Authorization", getToken(FD));
		Response response = given()
				.baseUri(getProperty("BASE_URI"))
				.and()
				.contentType(ContentType.JSON)
				.and()
				.header(header)
			.when()
				.get("userdetails")
			.then()
				.statusCode(200)
				.time(lessThan(1500L))
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"))
				.body("message", equalTo("Success"))
				.log().all()
				.extract().response();
			
			System.out.println(response.jsonPath().getString("message"));
	}

}
