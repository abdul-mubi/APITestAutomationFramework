package com.api.util;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	
	private static Properties prop = new Properties();
	private static String path = "/config/config.properties";
	public static String env;
	static {
		env = System.getProperty("env","qa");
		env = env.toLowerCase().trim();
		switch(env) {
		case "dev" -> path = "config/config.dev.properties";
		case "qa" -> path = "config/config.qa.properties";
		case "uat" -> path = "config/config.uat.properties";
		default -> path = "config/config.qa.properties";
		}
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		if (in == null) {
			throw new RuntimeException("File is not found in this path - "+path);
		}
		try  {
			prop.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

}
