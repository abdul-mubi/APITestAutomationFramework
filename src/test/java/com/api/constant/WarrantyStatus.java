package com.api.constant;

public enum WarrantyStatus {
	IN_WARRANTY(1), OUT_WARRANTY(2);
	
    int code;
	
    WarrantyStatus(int code){
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
}
