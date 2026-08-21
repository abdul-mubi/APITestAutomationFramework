package com.api.tests.datadriven;

import static com.api.util.SpecUtil.responseSpec_OK;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.service.JobService;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobCSVDataTest {
	private JobService jobService;
	
	@BeforeMethod(description = "Instantiate the jobService class")
	public void setup() {
		jobService = new JobService();
	}
	
	@Test(description = "Verify the create job api is creating job properly", groups= {"api","smoke","regression","datadriven","csv"},
			dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider = "CreateApiDataProvider")
	public void createJobApiTest(CreateJobPayload payload) {
		
		jobService.create(Role.FD, payload)
		.then()
			.spec(responseSpec_OK())
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobResponseSchema.json"))
			.body("message", Matchers.equalTo("Job created successfully. "))
			.body("data.id", Matchers.instanceOf(Integer.class))
			.body("data.mst_service_location_id", Matchers.equalTo(1))
			.body("data.job_number",Matchers.startsWith("JOB_"));
	}

}
