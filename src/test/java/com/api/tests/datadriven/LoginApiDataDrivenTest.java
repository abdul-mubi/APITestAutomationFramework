package com.api.tests.datadriven;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.api.service.AuthService;
import com.dataproviders.api.bean.UserBean;
import static com.api.util.SpecUtil.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class LoginApiDataDrivenTest {
	private AuthService authService;
	
	@BeforeMethod
	public void setup() {
		authService = new AuthService(); 
	}
	
	@Test(description = "Verify the login api response is completely valid", 
		  groups= {"api","smoke","regression","datadriven","csv"},
		  dataProviderClass = com.dataproviders.DataProviderUtils.class,
		  dataProvider = "LoginApiDataProvider")
	public void loginAPITest(UserBean userbean) {
		authService.login(userbean)
		.then()
		.spec(responseSpec_OK())
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"))
		.body("message", equalTo("Success"))
		.extract().response();
	}

}
