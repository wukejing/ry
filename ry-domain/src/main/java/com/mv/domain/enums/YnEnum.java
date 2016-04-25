package com.mv.domain.enums;

public enum YnEnum {

	N(-1,"无效"),
	Y(1,"有效");
	
	private int key;
	private String value;
	
	YnEnum(int key, String value){
		this.key = key;
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
