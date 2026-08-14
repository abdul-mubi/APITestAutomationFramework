package com.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.poiji.bind.Poiji;

public class ExcelReaderUtil {
	private static XSSFWorkbook xssfWorkBook;
	
	public static <T> Iterator<T> loadExcel(String filePath, String sheetName, Class<T> clazz) {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		try {
			xssfWorkBook = new XSSFWorkbook(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFSheet xssfSheet = xssfWorkBook.getSheet(sheetName);
		List<T> list = Poiji.fromExcel(xssfSheet, clazz);
		return list.iterator();
	}

}
