package com.mv.enums;

public enum CookieEnum {

	ticketValue("ticketName", "passport的ticket cookie名"),
	manTicketValue("manTicketName", "passport的ticket cookie名");

	private String key;
	private String desc;

	private CookieEnum(String key, String desc)

	{
		this.key = key;
		this.desc = desc;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
