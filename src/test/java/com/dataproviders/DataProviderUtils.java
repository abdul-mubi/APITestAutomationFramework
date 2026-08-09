package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.util.CSVReaderUtil;
import com.api.util.CreateJobBeanMapper;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtils {
	
	@DataProvider(name = "LoginApiDataProvider")
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

}
