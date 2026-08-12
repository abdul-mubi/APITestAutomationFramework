package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.UserCredentials;
import com.api.util.CSVReaderUtil;
import com.api.util.CreateJobBeanMapper;
import com.api.util.FakerDataGenerator;
import com.api.util.JsonReaderUtil;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtils {
	
	@DataProvider(name = "LoginApiDataProvider", parallel = true)
	public static Iterator<UserBean> loginApiDataProvider() {
		return CSVReaderUtil.loadCSV("testData/UserCredentials.csv", UserBean.class);
	}
	
	@DataProvider(name = "CreateApiDataProvider")
	public static Iterator<CreateJobPayload> createApiDataProvider() {
		Iterator<CreateJobBean> iterator = CSVReaderUtil.loadCSV("testData/CreateJobData.csv", CreateJobBean.class);
		
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		while(iterator.hasNext()) {
			CreateJobPayload payload = CreateJobBeanMapper.mapper(iterator.next());
			payloadList.add(payload);
		}
		return payloadList.iterator();
	}
	
	@DataProvider(name = "CreateJobAPIFakerDataProvider")
	public static Iterator<CreateJobPayload> createJobAPIFakerDataProvider() {
		String fakerCreateJobCount = System.getProperty("fakerCount","5");
		return FakerDataGenerator.generateFakerCreateJobData(Integer.parseInt(fakerCreateJobCount));
	}
	
	@DataProvider(name = "LoginApiJsonDataProvider")
	public static Iterator<UserCredentials> loginApiJsonDataProvider(){
		return JsonReaderUtil.loadJson("testData/UserCredentials.json",UserCredentials[].class);	
	}
	
	@DataProvider(name = "CreateJobApiJsonDataProvider")
	public static Iterator<CreateJobPayload> createJobApiJsonDataProvider(){
		return JsonReaderUtil.loadJson("testData/CreateJobAPIData.json",CreateJobPayload[].class);	
	}

}
