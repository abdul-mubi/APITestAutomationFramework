package com.api.tests.datadriven;

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
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.request.model.UserCredentials;
import com.dataproviders.api.bean.UserBean;

import static com.api.util.DateTimeUtil.getDateTime_ISO_UTC_Format;
import static com.api.util.SpecUtil.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class LoginApiExcelDataDrivenTest {
	
	@Test(description = "Verify the login api response is completely valid", 
		  groups= {"api","smoke","regression","datadriven","csv"},
		  dataProviderClass = com.dataproviders.DataProviderUtils.class,
		  dataProvider = "LoginApiExcelDataProvider")
	public void loginAPITest(UserCredentials payload) {
		Response response = given()
			.spec(requestSpec(payload))
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
