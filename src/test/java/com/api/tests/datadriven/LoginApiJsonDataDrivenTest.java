package com.api.tests.datadriven;

import static com.api.util.SpecUtil.responseSpec_OK;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.service.AuthService;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

@Listeners(com.listeners.APITestListener.class)
public class LoginApiJsonDataDrivenTest {
	private AuthService authService;
	
	@BeforeMethod
	public void setup() {
		authService = new AuthService(); 
	}
	
	@Test(description = "Verify the login api response is completely valid", 
		  groups= {"api","smoke","regression","datadriven","csv"},
		  dataProviderClass = com.dataproviders.DataProviderUtils.class,
		  dataProvider = "LoginApiJsonDataProvider")
	public void loginAPITest(UserCredentials payload) {
		authService.login(payload)
		.then()
		.spec(responseSpec_OK())
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"))
		.body("message", equalTo("Success"))
		.extract().response();
	}

}
