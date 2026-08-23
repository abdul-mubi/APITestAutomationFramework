package com.api.retry;

import org.apache.logging.log4j.*;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer{
	private static final int MAX_RETRY = 2;
	private int retryCount = 1;
	private static final Logger LOGGER = LogManager.getLogger(RetryAnalyzer.class);

	@Override
	public boolean retry(ITestResult result) {
		if(retryCount <= MAX_RETRY) {
			LOGGER.info("Retrying count is {}, Overall count is {} for this method {}",retryCount, MAX_RETRY, result.getName());
			LOGGER.info("Method failed for this reason - {}",result.getThrowable().getMessage());
			retryCount++;
			return true;
		}
		LOGGER.warn("Exceeded the max retry count for this methods {} ",result.getName());
		return false;
	}

}
