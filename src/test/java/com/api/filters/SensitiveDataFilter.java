package com.api.filters;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SensitiveDataFilter implements Filter{
	private static final Logger LOGGER = LogManager.getLogger(SensitiveDataFilter.class);

	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {
		LOGGER.info("Request URI - {}", requestSpec.getURI());
		LOGGER.info("Request METHOD - {}", requestSpec.getMethod());
		maskSensitiveInfoOnRequestHeader(requestSpec);
		LOGGER.info("Request BODY - {}", maskSensitiveInfoOnRequestBody(requestSpec));
		
		Response response = ctx.next(requestSpec, responseSpec);
		return response;
	}
	
	private String maskSensitiveInfoOnRequestBody(FilterableRequestSpecification requestSpec) {
		String payload = requestSpec.getBody();
		if (payload != null) {
			if (requestSpec.getBody().toString().contains("password")) {
				payload = payload.replaceAll("\"password\"\s*:\s*\"[^\"]+\"", "\"password\" : [REDACTED]");
			}
		}
		return payload;
	}
	
	private void maskSensitiveInfoOnRequestHeader(FilterableRequestSpecification requestSpec) {
		LOGGER.info("Request HEADER - ");
		List<Header> headerList = requestSpec.getHeaders().asList();
		for(Header header:headerList) {
			if (header.getName().equalsIgnoreCase("Authorization")) {
				LOGGER.info("{} : {}",header.getName(), "[REDACTED]");
			}else {
				LOGGER.info("{} : {}",header.getName(), header.getValue());
			}
		}
	}
	

}
