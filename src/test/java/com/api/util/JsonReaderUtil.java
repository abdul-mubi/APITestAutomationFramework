package com.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReaderUtil {
	
	public static <T> Iterator<T> loadJson(String filePath, Class<T[]> clazz) {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		ObjectMapper objectMapper = new ObjectMapper();
		T[] classArray = null;
		try {
			classArray = objectMapper.readValue(is, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<T> list = Arrays.asList(classArray);
		return list.iterator();
	}

}
