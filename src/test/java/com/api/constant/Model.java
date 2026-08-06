package com.api.constant;

public enum Model {
	NEXUS_2_BLUE(1), GALLEXY(2);
	
	int code; //By default IV is private in Enum
	Model(int code){ //By default Constructor is private in Enum
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}

}
