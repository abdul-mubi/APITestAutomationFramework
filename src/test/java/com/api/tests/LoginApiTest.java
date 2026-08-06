package com.api.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.ServiceLocation;
import com.api.constant.WarrantyStatus;
import com.api.request.model.CreateJobApi;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.request.model.UserCredentials;

import static com.api.util.DateTimeUtil.getDateTime_ISO_UTC_Format;
import static com.api.util.SpecUtil.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class LoginApiTest {
	private UserCredentials userCredentials;
	
	@BeforeMethod(description = "Creating payload for login api")
	public void setup() {
		userCredentials = new UserCredentials("iamfd", "password");
	}
	
	@Test(description = "Verify the login api response is completely valid", groups= {"api","smoke","regression"})
	public void loginAPITest() {
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
