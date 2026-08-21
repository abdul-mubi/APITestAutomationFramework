package com.api.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.github.javafaker.Faker;

public class FakerDataGenerator {

	@SuppressWarnings("deprecation")
	private static Faker faker = new Faker(new Locale("en-IND"));
	private static final String COUNTRY = "India";
	private static int[] problemId = {1,2,3,4,5,6,7,8,9,10,11,12,15,16,17,19,20,22,24,26,27,28,29};
	private static Random random = new Random();
	private static int mst_service_location_id = 0;
	private static int mst_platform_id = 2;
	private static int mst_warrenty_status_id = 1;
	private static int mst_oem_id = 1;
	private static int product_id = 1;
	private static int mst_model_id = 1;
	
	private FakerDataGenerator() {
		
	}
	
	public static Iterator<CreateJobPayload> generateFakerCreateJobData(int count) {
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		for (int i=0; i<count;i++) {
			CreateJobPayload payload = new CreateJobPayload(mst_service_location_id, mst_platform_id, mst_warrenty_status_id, mst_oem_id, generateFakerCustomerData(), generateFakerCustomerAddressData(), generateFakerCustomerProduct(), generateFakerProblem());
			payloadList.add(payload);
		}
		return payloadList.iterator();
	}
	
	public static List<Problems> generateFakerProblem() {
		int problemCount = random.nextInt(3);
		int id;
		String remark;
		Problems problems;
		List<Problems> problemsArray = new ArrayList<Problems>();
		for(int i=0; i<=problemCount; i++) {
			id = problemId[random.nextInt(23)];
			remark = faker.lorem().sentence(5);
			problems = new Problems(id,remark);
			problemsArray.add(problems);
		}
		return problemsArray;
	}
	
	public static CustomerProduct generateFakerCustomerProduct() {
		String dop = DateTimeUtil.getDateTime_ISO_UTC_Format();
		String imei_serial_number = faker.numerify("##############");
		
		CustomerProduct customerProduct = new CustomerProduct(dop, imei_serial_number, imei_serial_number, imei_serial_number, dop, product_id, mst_model_id);
		return customerProduct;
	}
	
	public static Customer generateFakerCustomerData() {
		String fname = faker.name().firstName();
		String lname = faker.name().lastName();
		String phoneNumber = faker.numerify("7#########");
		String altPhoneNumber = faker.numerify("7#########");
		String emailID = faker.internet().emailAddress();
		String altEmailID = faker.internet().emailAddress();
		Customer customer = new Customer(fname, lname, phoneNumber, altPhoneNumber, emailID, altEmailID);
		return customer;
	}
	
	public static CustomerAddress generateFakerCustomerAddressData() {
		String flat_number = faker.numerify("###");
		String apartment_name = faker.address().streetName();
		String street_name  = faker.address().streetName();
		String landmark  = faker.address().streetName();
		String area  = faker.address().streetName();
		String pincode  = faker.number().digits(6);
		String state = faker.address().state();
		CustomerAddress customer_address = new CustomerAddress(flat_number, apartment_name, street_name, landmark, area, pincode, COUNTRY, state);
		return customer_address;
	}
	
}
