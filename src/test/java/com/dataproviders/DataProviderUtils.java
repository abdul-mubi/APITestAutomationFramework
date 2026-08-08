package com.dataproviders;

import java.util.Iterator;

import org.testng.annotations.DataProvider;

import com.api.util.CSVReaderUtil;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtils {
	
	@DataProvider(name = "LoginApiDataProvider")
	public static Iterator<UserBean> loginApiDataProvider() {
		return CSVReaderUtil.loadCSV("testData/UserCredentials.csv");
	}

}
