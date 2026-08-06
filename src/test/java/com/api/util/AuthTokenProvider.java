package com.api.util;

import static io.restassured.RestAssured.*;

import static com.api.constant.Role.*;

import com.api.constant.Role;
import com.api.request.model.UserCredentials;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import static com.api.util.ConfigManager.*;

public class AuthTokenProvider {
	
	public static String getToken(Role role) {
		UserCredentials userCred = null;
		if (role == FD) {
			userCred = new UserCredentials("iamfd", "password");
		}else if(role == SUP) {
			userCred = new UserCredentials("iamsup", "password");
		}else if(role == ENG) {
			userCred = new UserCredentials("iameng", "password");
		}else if(role == QC) {
			userCred = new UserCredentials("iamqc", "password");
		}
		String token = given()
							.baseUri(getProperty("BASE_URI"))
							.and()
							.contentType(ContentType.JSON)
							.and()
							.body(userCred)
						.when()
							.post("login")
						.then()
							.statusCode(200)
							.and()
							.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"))
							.and()
							.log().ifValidationFails()
							.extract().jsonPath().getString("data.token");
		return token;
							
	}

}
