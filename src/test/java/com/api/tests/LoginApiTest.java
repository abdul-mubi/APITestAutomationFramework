package com.api.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;
import com.api.pojo.UserCredentials;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class LoginApiTest {
	@Test
	public void loginAPITest() {
		UserCredentials userCredentials = new UserCredentials("iamfd", "password");
		Response response = given()
			.baseUri("http://64.227.160.186:9000/v1")
			.and()
			.contentType(ContentType.JSON)
			.and()
			.body(userCredentials)
		.when()
			.post("login")
		.then()
			.statusCode(200)
			.time(lessThan(1500L))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"))
			.body("message", equalTo("Success"))
			.log().all()
			.extract().response();
		
		System.out.println(response.jsonPath().getString("message"));
	}

}
