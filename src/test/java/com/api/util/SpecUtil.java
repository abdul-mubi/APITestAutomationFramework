package com.api.util;

import static com.api.util.AuthTokenProvider.getToken;

import org.hamcrest.Matchers;

import com.api.constant.Role;
import com.api.filters.SensitiveDataFilter;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {
	
	public static RequestSpecification requestSpec() {
		RequestSpecification request = new RequestSpecBuilder()
		.setBaseUri(ConfigManager.getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.addFilter(new SensitiveDataFilter())
		.build();
		
		return request;
	}
	
	public static RequestSpecification requestSpec(Object payload) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(ConfigManager.getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.setBody(payload)
		.addFilter(new SensitiveDataFilter())
		.build();
		
		return requestSpecification;
	}
	
	public static RequestSpecification requestSpecWithAuth(Role role) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(ConfigManager.getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.addHeader("Authorization", getToken(role))
		.addFilter(new SensitiveDataFilter())
		.build();
		
		return requestSpecification;
	}
	
	public static RequestSpecification requestSpecWithAuth(Role role, Object payload) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(ConfigManager.getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.addHeader("Authorization", getToken(role))
		.setBody(payload)
		.addFilter(new SensitiveDataFilter())
		.build();
		
		return requestSpecification;
	}
	
	public static RequestSpecification requestSpecWithOutContent(Role role) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(ConfigManager.getProperty("BASE_URI"))
		.setContentType("")
		.setAccept(ContentType.JSON)
		.addHeader("Authorization", getToken(role))
		.addFilter(new SensitiveDataFilter())
		.build();
		
		return requestSpecification;
	}
	
	public static ResponseSpecification responseSpec_OK() {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
		.expectContentType(ContentType.JSON)
		.expectStatusCode(200)
		.expectResponseTime(Matchers.lessThan(1500L))
		.build();
		
		
		return responseSpecification;
	}
	
	public static ResponseSpecification responseSpec_JSON(int status) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
		.expectContentType(ContentType.JSON)
		.expectStatusCode(status)
		.expectResponseTime(Matchers.lessThan(1500L))
		.build();
		
		
		return responseSpecification;
	}
	
	public static ResponseSpecification responseSpec_TEXT(int status) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
		.expectStatusCode(status)
		.expectResponseTime(Matchers.lessThan(1500L))
		.build();
		
		
		return responseSpecification;
	}

}
