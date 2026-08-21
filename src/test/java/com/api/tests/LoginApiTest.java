package com.api.tests;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.api.request.model.UserCredentials;
import com.api.service.AuthService;
import static com.api.util.SpecUtil.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class LoginApiTest {
	private UserCredentials userCredentials;
	private AuthService authService;
	
	@BeforeMethod(description = "Creating payload for login api")
	public void setup() {
		userCredentials = new UserCredentials("iamfd", "password");
		authService = new AuthService(); 
	}
	
	@Test(description = "Verify the login api response is completely valid", groups= {"api","smoke","regression"})
	public void loginAPITest() {
		Response response = authService.login(userCredentials)
		.then()
			.spec(responseSpec_OK())
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"))
			.body("message", equalTo("Success"))
			.extract().response();
		
		System.out.println(response.jsonPath().getString("message"));
	}

}
