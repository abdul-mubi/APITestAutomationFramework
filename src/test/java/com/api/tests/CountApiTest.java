package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.util.SpecUtil.responseSpec_OK;
import static com.api.util.SpecUtil.responseSpec_TEXT;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.service.DashboardService;

import io.qameta.allure.*;
import io.qameta.allure.Feature;

@Listeners(com.listeners.APITestListener.class)
@Epic("Job Management")
@Feature("Count API validation")
public class CountApiTest {
	private DashboardService dashboardService;
	
	@BeforeMethod
	public void setup() {
		dashboardService = new DashboardService();
	}
	
	@Story("Validate count job API")
	@Description("Verify the count job api response")
	@Severity(SeverityLevel.NORMAL)
	@Test(description = "Verify the create count api reponse is completely valid", groups= {"api","smoke","regression"})
	public void verifyCountApiResponse() {
		dashboardService.count(FD)
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
		
	}
	
	@Story("Validate count job API without token")
	@Description("Verify the count job api response without token")
	@Severity(SeverityLevel.NORMAL)
	@Test(description = "Verify the create count api reponse is properly without token", groups= {"api","regression"})
	public void verifyCountApiResponseWithoutToken() {
		dashboardService.countWithoutAuth()
		.then()
		.spec(responseSpec_TEXT(401))
		.extract().response();
	}

}
