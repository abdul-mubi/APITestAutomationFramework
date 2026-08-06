package com.api.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {
	
	private DateTimeUtil() {
		//to avoid making object of this class
	}
	
	public static String getDateTime_ISO_UTC_Format() {
		return Instant.now().minus(10,ChronoUnit.DAYS).toString();
	}

}
