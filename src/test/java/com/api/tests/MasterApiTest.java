package com.api.tests;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.service.MasterService;
import com.api.util.SpecUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners(com.listeners.APITestListener.class)
@Epic("User Management")
@Feature("Master Service")
public class MasterApiTest {
	private MasterService masterService;
	
	@BeforeMethod
	public void setup() {
		masterService = new MasterService();
	}
	
	@Story("Validate Master API")
	@Description("Verify the master api response")
	@Severity(SeverityLevel.NORMAL)
	@Test(description = "Verify the master api is properly generating response", groups= {"api","smoke","regression"})
	public void verifyMasterApiResponse() {
		masterService.master()
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
