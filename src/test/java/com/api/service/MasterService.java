package com.api.service;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.*;

import com.api.util.SpecUtil;

import io.restassured.response.Response;

public class MasterService {
	private static final String MASTER_ENDPOINT = "master";
	
	public Response master() {
		Response response = given()
							.spec(SpecUtil.requestSpecWithOutContent(FD))//override the default content type from application/urlencoded to empty
							.when()
							.post(MASTER_ENDPOINT);
		return response;
	}
}
