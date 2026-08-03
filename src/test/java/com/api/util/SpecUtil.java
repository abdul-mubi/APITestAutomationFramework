package com.api.util;

import static com.api.util.AuthTokenProvider.getToken;
import org.hamcrest.Matchers;
import com.api.constant.Role;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {
	
	public static RequestSpecification requestSpec() {
		RequestSpecification request = new RequestSpecBuilder()
		.setBaseUri(ConfigManager.getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.log(LogDetail.URI)
		.log(LogDetail.HEADERS)
		.log(LogDetail.METHOD)
		.log(LogDetail.BODY)
		.build();
		
		return request;
	}
	
	public static RequestSpecification requestSpec(Object payload) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(ConfigManager.getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.setBody(payload)
		.log(LogDetail.URI)
		.log(LogDetail.HEADERS)
		.log(LogDetail.METHOD)
		.log(LogDetail.BODY)
		.build();
		
		return requestSpecification;
	}
	
	public static RequestSpecification requestSpecWithAuth(Role role) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(ConfigManager.getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.addHeader("Authorization", getToken(role))
		.log(LogDetail.URI)
		.log(LogDetail.HEADERS)
		.log(LogDetail.METHOD)
		.log(LogDetail.BODY)
		.build();
		
		return requestSpecification;
	}
	
	public static RequestSpecification requestSpecWithOutContent(Role role) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
		.setBaseUri(ConfigManager.getProperty("BASE_URI"))
		.setContentType("")
		.setAccept(ContentType.JSON)
		.addHeader("Authorization", getToken(role))
		.log(LogDetail.URI)
		.log(LogDetail.HEADERS)
		.log(LogDetail.METHOD)
		.log(LogDetail.BODY)
		.build();
		
		return requestSpecification;
	}
	
	public static ResponseSpecification responseSpec_OK() {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
		.expectContentType(ContentType.JSON)
		.expectStatusCode(200)
		.expectResponseTime(Matchers.lessThan(1500L))
		.log(LogDetail.ALL)
		.build();
		
		
		return responseSpecification;
	}
	
	public static ResponseSpecification responseSpec_JSON(int status) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
		.expectContentType(ContentType.JSON)
		.expectStatusCode(status)
		.expectResponseTime(Matchers.lessThan(1500L))
		.log(LogDetail.ALL)
		.build();
		
		
		return responseSpecification;
	}
	
	public static ResponseSpecification responseSpec_TEXT(int status) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
		.expectStatusCode(status)
		.expectResponseTime(Matchers.lessThan(1500L))
		.log(LogDetail.ALL)
		.build();
		
		
		return responseSpecification;
	}

}
