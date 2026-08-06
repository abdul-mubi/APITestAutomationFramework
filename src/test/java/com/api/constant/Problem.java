package com.api.constant;

public enum Problem {
	SMARTPHONE_IS_RUNNING_SLOWING(1),
	POOR_BATTERY_LIFE(2),
	PHONE_OR_APP_CRASHES(3),
	OVERHEATING(4);
	
	int code;
	
	Problem(int code){
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}

}
