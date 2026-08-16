package com.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomerProblemDBModel {
	private int mst_problem_id;
	private String remark;
}
