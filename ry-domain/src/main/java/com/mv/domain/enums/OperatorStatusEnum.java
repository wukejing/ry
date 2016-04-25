package com.mv.domain.enums;

public enum OperatorStatusEnum {
	SUCCESS(1,"成功"),
	FAILURE(-1,"失败");
	
	private int key;
	private String value;
	
	OperatorStatusEnum(int key, String value){
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
