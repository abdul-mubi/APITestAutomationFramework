package com.api.util;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvUtility {
	private static Dotenv dotEnv;
	
	static {
		dotEnv = Dotenv.load();
	}
	
	private EnvUtility() {
		
	}
	
	public static String getValue(String key) {
		return dotEnv.get(key);
	}

}
