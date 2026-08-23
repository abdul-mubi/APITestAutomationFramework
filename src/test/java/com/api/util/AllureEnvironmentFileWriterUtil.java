package com.api.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AllureEnvironmentFileWriterUtil {
	private static final Logger	LOGGER = LogManager.getLogger(AllureEnvironmentFileWriterUtil.class);
	
	private AllureEnvironmentFileWriterUtil() {
		
	}
	
	public static void createAllureEnvironmentFile() {
		String folderPath = "target/allure-results";
		
		File file = new File(folderPath);
		if(!file.mkdirs()) {
			LOGGER.info("Folder - 'allure-result' is already present, No need to create");
		}else {
			LOGGER.warn("Folder - 'allure-result' is not present, Created folder");
		}
		
		Properties prop = new Properties();
		prop.setProperty("ENV", ConfigManager.env);
		prop.setProperty("BASE_URI", ConfigManager.getProperty("BASE_URI"));
		prop.setProperty("OS_NAME", System.getProperty("os.name"));
		prop.setProperty("OS_VERSION", System.getProperty("os.version"));
		prop.setProperty("JAVA_VERSION", System.getProperty("java.version"));
		prop.setProperty("JAVA_PATH", System.getProperty("java.home"));
		
		FileWriter fw;
		try {
			fw = new FileWriter(folderPath+"/environment.properties");
			prop.store(fw, "Environment related information for allur reporting");
			LOGGER.info("Created environment.properties file at this location - {}",folderPath);
		} catch (IOException e) {
			LOGGER.error("Unable to create the environment.properties file",e);
			e.printStackTrace();
		}
	}

}
