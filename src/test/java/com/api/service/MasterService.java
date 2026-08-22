package com.api.service;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.util.SpecUtil;

import io.restassured.response.Response;

public class MasterService {
	private static final String MASTER_ENDPOINT = "master";
	private static final Logger LOGGER = LogManager.getLogger(MasterService.class);
	
	public Response master() {
		LOGGER.info("Making master POST request without payload");
		Response response = given()
							.spec(SpecUtil.requestSpecWithOutContent(FD))//override the default content type from application/urlencoded to empty
							.when()
							.post(MASTER_ENDPOINT);
		return response;
	}
}
