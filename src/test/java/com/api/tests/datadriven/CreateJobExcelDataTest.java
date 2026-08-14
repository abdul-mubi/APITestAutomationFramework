package com.api.tests.datadriven;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.Role;
import com.api.constant.ServiceLocation;
import com.api.constant.WarrantyStatus;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import static com.api.util.DateTimeUtil.*;
import static com.api.util.SpecUtil.*;

import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class CreateJobExcelDataTest {
	
	@Test(description = "Verify the create job api is creating job properly", groups= {"api","smoke","regression","datadriven","csv"},
			dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider = "CreateApiExcelDataProvider")
	public void createJobApiTest(CreateJobPayload payload) {
		
		given()
			.spec(requestSpecWithAuth(Role.FD,payload))
		.when()
			.post("/job/create")
		.then()
			.spec(responseSpec_OK())
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobResponseSchema.json"))
			.body("message", Matchers.equalTo("Job created successfully. "))
			.body("data.id", Matchers.instanceOf(Integer.class))
			.body("data.mst_service_location_id", Matchers.equalTo(1))
			.body("data.job_number",Matchers.startsWith("JOB_"));
	}

}
