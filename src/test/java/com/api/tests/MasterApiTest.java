package com.api.tests;

import static org.hamcrest.Matchers.*;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.api.util.SpecUtil;

import static com.api.constant.Role.*;
import static com.api.util.AuthTokenProvider.*;
import io.restassured.http.Header;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import io.restassured.response.Response;
import static com.api.util.ConfigManager.*;
import static io.restassured.RestAssured.*;

public class MasterApiTest {
	@Test(description = "Verify the master api is properly generating response", groups= {"api","smoke","regression"})
	public void verifyMasterApiResponse() {
		given()
				.spec(SpecUtil.requestSpecWithOutContent(FD))//override the default content type from application/urlencoded to empty
		.when()
			.post("master")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body("$", hasKey("message"))
			.body("message", equalTo("Success"))
//			.body(matchesJsonSchemaInClasspath("response-schema/CountResponseSchema.json"))
			.body("data", instanceOf(Map.class))
			.body("data", notNullValue())
			.body("data.size()", equalTo(10))
			
			.body("data",hasKey("mst_oem"))
			.body("data.mst_oem", instanceOf(List.class))
			.body("data.mst_oem.size()", greaterThan(0))
			
			.body("data.mst_model", instanceOf(List.class))
			.body("data.mst_action_status", instanceOf(List.class))
			.body("data.mst_warrenty_status", instanceOf(List.class))
			.body("data.mst_platform", instanceOf(List.class))
			.body("data.mst_product", instanceOf(List.class))
			.body("data.mst_role", instanceOf(List.class))
			.body("data.mst_service_location", instanceOf(List.class))
			.body("data.mst_problem", instanceOf(List.class))
			.body("data.map_fst_pincode", instanceOf(List.class))
			
			
			.body("data.mst_oem.is_active", everyItem(greaterThanOrEqualTo(1)));
	}

}
