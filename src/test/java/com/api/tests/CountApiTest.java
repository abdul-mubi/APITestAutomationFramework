package com.api.tests;

import static org.hamcrest.Matchers.*;
import java.util.List;
import org.testng.annotations.Test;
import static com.api.constant.Role.*;
import static com.api.util.AuthTokenProvider.*;
import io.restassured.http.Header;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import io.restassured.response.Response;
import static com.api.util.ConfigManager.*;
import static io.restassured.RestAssured.*;

public class CountApiTest {
	
	@Test
	public void verifyCountApiResponse() {
		Header header = new Header("Authorization", getToken(FD));
		Response response = given()
			.baseUri(getProperty("BASE_URI"))
			.and()
			.header(header)
		.when()
			.get("/dashboard/count")
		.then()
			.statusCode(200)
			.time(lessThan(1000L))
			.body("message", equalTo("Success"))
			.body(matchesJsonSchemaInClasspath("response-schema/CountResponseSchema.json"))
			.body("data", instanceOf(List.class))
			.body("data", notNullValue())
			.body("data.size()", equalTo(3))
			.body("data.label", everyItem(not(blankOrNullString())))
			.body("data.count", everyItem(greaterThanOrEqualTo(0)))
			.body("data.key", containsInAnyOrder("pending_fst_assignment","created_today","pending_for_delivery"))
			.extract().response();
		
		System.out.println(response.jsonPath().getList("data"));
	}
	
	@Test
	public void verifyCountApiResponseWithoutToken() {
		given()
			.baseUri(getProperty("BASE_URI"))
		.when()
			.get("/dashboard/count")
		.then()
			.statusCode(401)
			.time(lessThan(1000L))
			.extract().response();
	}

}
