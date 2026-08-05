package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import com.api.constant.Role;
import com.api.pojo.CreateJobApi;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.util.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class CreateJobApiTest {
	@Test
	public void createJobApiTest() {
		Customer customer = new Customer("Abdul", "Hameed", "7502060003", "", "abdulmydeen1996@gmail.com", "");
		CustomerAddress customer_address = new CustomerAddress("50", "Bhandari", "Bhileshivsle", "Near yellama", "Hennur", "560077", "India", "Karnataka");
		CustomerProduct customerProduct = new CustomerProduct("2025-12-31T18:30:00.000Z", "98245965485683", "98245965485683", "98245965485683", "2025-12-31T18:30:00.000Z", 1, 2);
		Problems problems = new Problems(2,"Battery Issue");
		List<Problems> problemsArray = new ArrayList<Problems>();
		problemsArray.add(problems);
		CreateJobApi payload = new CreateJobApi(0, 2, 1, 1, customer, customer_address, customerProduct, problemsArray);
		
		given()
			.spec(SpecUtil.requestSpecWithAuth(Role.FD,payload))
		.when()
			.post("/job/create")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobResponseSchema.json"))
			.body("message", Matchers.equalTo("Job created successfully. "))
			.body("data.id", Matchers.instanceOf(Integer.class))
			.body("data.mst_service_location_id", Matchers.equalTo(1))
			.body("data.job_number",Matchers.startsWith("JOB_"));
	}

}
