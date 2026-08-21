package com.api.tests;

import static com.api.util.DateTimeUtil.getDateTime_ISO_UTC_Format;
import static com.api.util.SpecUtil.responseSpec_OK;

import java.util.ArrayList;
import java.util.List;

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
import com.api.service.JobService;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobApiTest {
	
	private CreateJobPayload payload;
	private JobService jobService;
	
	@BeforeMethod(description = "Creating payload for creat job api")
	public void setup() {
		Customer customer = new Customer("Abdul", "Hameed", "7502060003", "", "abdulmydeen1996@gmail.com", "");
		CustomerAddress customer_address = new CustomerAddress("50", "Bhandari", "Bhileshivsle", "Near yellama", "Hennur", "560077", "India", "Karnataka");
		CustomerProduct customerProduct = new CustomerProduct(getDateTime_ISO_UTC_Format(), "90245965085683", "90245965085683", "90245965085683", getDateTime_ISO_UTC_Format(), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.OVERHEATING.getCode(),"Battery Issue");
		List<Problems> problemsArray = new ArrayList<Problems>();
		problemsArray.add(problems);
		payload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), WarrantyStatus.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer, customer_address, customerProduct, problemsArray);
		jobService = new JobService();
	}
	
	@Test(description = "Verify the create job api is creating job properly", groups= {"api","smoke","regression"})
	public void createJobApiTest() {
		
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
