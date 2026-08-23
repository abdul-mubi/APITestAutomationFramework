package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.util.SpecUtil.responseSpec_OK;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.service.UserService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.module.jsv.JsonSchemaValidator;

@Listeners(com.listeners.APITestListener.class)
@Epic("User Management")
@Feature("User Details")
public class UserDetailsApiTest {
	private UserService userService;
	
	@BeforeMethod
	public void setup() {
		userService = new UserService();
	}
	
	@Story("Validate User Details API")
	@Description("Verify the user details api response")
	@Severity(SeverityLevel.NORMAL)
	@Test(description = "Verify the user details api response is completely valid as expectec", groups= {"api","smoke","regression"})
	public void userDetailsApiTest() {
		userService.userdetails(FD)
		.then()
		.spec(responseSpec_OK())
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"))
		.body("message", equalTo("Success"))
		.extract().response();
			
	}

}
