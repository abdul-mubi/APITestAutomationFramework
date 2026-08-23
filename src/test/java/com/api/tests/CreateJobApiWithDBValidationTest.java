package com.api.tests;

import static com.api.util.DateTimeUtil.getDateTime_ISO_UTC_Format;
import static com.api.util.SpecUtil.responseSpec_OK;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
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
import com.api.response.model.CreateJobResponseModel;
import com.api.service.JobService;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerJobHeadDao;
import com.database.dao.CustomerProblemDao;
import com.database.dao.CustomerProductDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerJobHeadDBModel;
import com.database.model.CustomerProblemDBModel;
import com.database.model.CustomerProductDBModel;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.module.jsv.JsonSchemaValidator;

@Listeners(com.listeners.APITestListener.class)
@Epic("Job Management")
@Feature("Creating job")
public class CreateJobApiWithDBValidationTest {
	
	private CreateJobPayload payload;
	private JobService jobService;
	
	@BeforeMethod(description = "Creating payload for creat job api")
	public void setup() {
		Customer customer = new Customer("Abdul123", "Hameed", "7502060003", "", "abdulmydeen1996@gmail.com", "");
		CustomerAddress customer_address = new CustomerAddress("50", "Bhandari", "Bhileshivsle", "Near yellama", "Hennur", "560077", "India", "Karnataka");
		CustomerProduct customerProduct = new CustomerProduct(getDateTime_ISO_UTC_Format(), "92099960787683", "92099960787683", "92099960787683", getDateTime_ISO_UTC_Format(), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.OVERHEATING.getCode(),"Battery Issue");
		List<Problems> problemsArray = new ArrayList<Problems>();
		problemsArray.add(problems);
		payload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), WarrantyStatus.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer, customer_address, customerProduct, problemsArray);
		jobService = new JobService();
	}
	
	@Story("Validate create job API")
	@Description("Verify the create job api response and also DB data")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Verify the create job api is creating job properly and perform DB validation", groups= {"api","smoke","regression"})
	public void createJobApiTest() {
		
		CreateJobResponseModel createJobResponseModel = jobService.create(Role.FD, payload)
														.then()
														.spec(responseSpec_OK())
														.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobResponseSchema.json"))
														.body("message", Matchers.equalTo("Job created successfully. "))
														.body("data.id", Matchers.instanceOf(Integer.class))
														.body("data.mst_service_location_id", Matchers.equalTo(1))
														.body("data.job_number",Matchers.startsWith("JOB_"))
														.extract().as(CreateJobResponseModel.class);
		
		int customerId = createJobResponseModel.getData().getTr_customer_id();
		
		//Customer job head validation
		CustomerJobHeadDBModel customerJobHeadDBModel = CustomerJobHeadDao.getCustomerJobHeadInfo(customerId);
		Assert.assertEquals(customerJobHeadDBModel.getMst_oem_id(), payload.mst_oem_id());
		Assert.assertEquals(customerJobHeadDBModel.getMst_platform_id(), payload.mst_platform_id());
		Assert.assertEquals(customerJobHeadDBModel.getMst_service_location_id(), payload.mst_service_location_id());
		Assert.assertEquals(customerJobHeadDBModel.getMst_warrenty_status_id(), payload.mst_warrenty_status_id());
		
		//customer validation
		CustomerDBModel customerDBModel = CustomerDao.getCustomerInfo(customerId);
		Assert.assertEquals(customerDBModel.getFirst_name(), payload.customer().first_name());
		Assert.assertEquals(customerDBModel.getLast_name(), payload.customer().last_name());
		Assert.assertEquals(customerDBModel.getMobile_number(), payload.customer().mobile_number());
		Assert.assertEquals(customerDBModel.getMobile_number_alt(), payload.customer().mobile_number_alt());
		Assert.assertEquals(customerDBModel.getEmail_id(), payload.customer().email_id());
		Assert.assertEquals(customerDBModel.getEmail_id_alt(), payload.customer().email_id_alt());
		
		//Customer address validation
		CustomerAddressDBModel customerAddressDBModel = CustomerAddressDao.getCustomerAddressInfo(Integer.parseInt(customerDBModel.getTr_customer_address_id()));
		Assert.assertEquals(customerAddressDBModel.getFlat_number(), payload.customer_address().flat_number());
		Assert.assertEquals(customerAddressDBModel.getApartment_name(), payload.customer_address().apartment_name());
		Assert.assertEquals(customerAddressDBModel.getLandmark(), payload.customer_address().landmark());
		Assert.assertEquals(customerAddressDBModel.getArea(), payload.customer_address().area());
		Assert.assertEquals(customerAddressDBModel.getPincode(), payload.customer_address().pincode());
		Assert.assertEquals(customerAddressDBModel.getCountry(), payload.customer_address().country());
		Assert.assertEquals(customerAddressDBModel.getState(), payload.customer_address().state());
		
		//Customer product validation
		CustomerProductDBModel customerProductDBModel = CustomerProductDao.getCustomerProductInfo(customerId);
		Assert.assertEquals(customerProductDBModel.getDop(), payload.customer_product().dop().split("T")[0]);
		Assert.assertEquals(customerProductDBModel.getPopurl(), payload.customer_product().popurl());
		Assert.assertEquals(customerProductDBModel.getImei1(), payload.customer_product().imei1());
		Assert.assertEquals(customerProductDBModel.getImei2(), payload.customer_product().imei2());
		Assert.assertEquals(customerProductDBModel.getMst_model_id(), payload.customer_product().mst_model_id());
		Assert.assertEquals(customerProductDBModel.getSerial_number(), payload.customer_product().serial_number());
		
		//Customer problem validation
		CustomerProblemDBModel customerProblemDBModel = CustomerProblemDao.getCustomerProblemInfo(customerJobHeadDBModel.getId());
		Assert.assertEquals(customerProblemDBModel.getMst_problem_id(), payload.problems().get(0).id());
		Assert.assertEquals(customerProblemDBModel.getRemark(), payload.problems().get(0).remark());
		
		
		
	}

}
