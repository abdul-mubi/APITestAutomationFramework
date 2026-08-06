package com.api.tests;

import static org.hamcrest.Matchers.*;
import java.util.List;
import org.testng.annotations.Test;
import static com.api.util.SpecUtil.*;
import static com.api.constant.Role.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class CountApiTest {
	
	@Test(description = "Verify the create count api reponse is completely valid", groups= {"api","smoke","regression"})
	public void verifyCountApiResponse() {
		Response response = given()
			.spec(requestSpecWithAuth(FD))
		.when()
			.get("/dashboard/count")
		.then()
			.spec(responseSpec_OK())
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
	
	@Test(description = "Verify the create count api reponse is properly without token", groups= {"api","regression"})
	public void verifyCountApiResponseWithoutToken() {
		given()
		.spec(requestSpec())
		.when()
			.get("/dashboard/count")
		.then()
			.spec(responseSpec_TEXT(401))
			.extract().response();
	}

}
