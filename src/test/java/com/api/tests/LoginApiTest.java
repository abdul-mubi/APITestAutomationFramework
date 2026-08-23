package com.api.tests;

import static com.api.util.SpecUtil.responseSpec_OK;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.service.AuthService;
import com.listeners.APITestListener;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.module.jsv.JsonSchemaValidator;

@Listeners(APITestListener.class)
@Epic("User Management")
@Feature("Authentication")
public class LoginApiTest {
	private UserCredentials userCredentials;
	private AuthService authService;
	
	@BeforeMethod(description = "Creating payload for login api")
	public void setup() {
		userCredentials = new UserCredentials("iamfd", "password");
		authService = new AuthService(); 
	}
	
	@Story("Login with valid credentials")
	@Description("Execute loginAPITest with valid user credentials")
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Verify the login api response is completely valid", groups= {"api","smoke","regression"})
	public void loginAPITest() {
		authService.login(userCredentials)
		.then()
		.spec(responseSpec_OK())
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"))
		.body("message", equalTo("Success"))
		.extract().response();
	}

}
