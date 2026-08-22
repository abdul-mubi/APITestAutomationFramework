package com.listeners;


import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class APITestListener implements ITestListener {
	
	private static final Logger LOGGER = LogManager.getLogger(APITestListener.class);
	
	public void onTestStart(ITestResult result) {
		LOGGER.info("Test Class started, Class name - {}",result.getMethod().getTestClass());
	    LOGGER.info("Test method started, Method name - {}",result.getName());
	    LOGGER.info("Description of test method - {}",result.getMethod().getDescription());
	    LOGGER.info("Groups of test method - {}",Arrays.toString(result.getMethod().getGroups())); 
	}
	
	public void onTestSuccess(ITestResult result) {
		long startTime = result.getStartMillis();
		long endTime = result.getEndMillis();
		LOGGER.info("Test method completed {}, Time taken for execution is - {}ms",result.getName(),endTime-startTime);
	}
	
	public void onTestFailure(ITestResult result) {
	    LOGGER.error("Test method {} FAILED!!!",result.getName());
	    LOGGER.error(result.getThrowable());
	}
	
	public void onTestSkipped(ITestResult result) {
	    LOGGER.warn("Test method {} SKIPPED!!!",result.getName());
	    LOGGER.error(result.getThrowable());
	}
	
	public void onStart(ITestContext context) {
	    LOGGER.info("***********************************");
	    LOGGER.info("________Application Started________");
	}
	
	public void onFinish(ITestContext context) {
	    LOGGER.info("________FINISHED________");
	}
	
}
