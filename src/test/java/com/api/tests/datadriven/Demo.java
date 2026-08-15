package com.api.tests.datadriven;

import java.util.List;

import com.database.dao.CreateJobDataDao;
import com.dataproviders.api.bean.CreateJobBean;

public class Demo {

	public static void main(String[] args) {
		List<CreateJobBean> beanList = CreateJobDataDao.getCreateJobPayloadData();
		for(CreateJobBean bean:beanList) {
			System.out.println(bean);
		}

	}

}
