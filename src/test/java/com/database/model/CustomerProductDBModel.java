package com.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomerProductDBModel {
	private String dop;
	private String serial_number;
	private String imei1;
	private String imei2;
	private String popurl;
	private int mst_model_id;
}
