package com.mv.domain;

import com.mv.domain.base.BaseDomain;

public class Company extends BaseDomain {

	private static final long serialVersionUID = -2354545063682334954L;
	
	private String expressName;
	private String contact;
	private String tel;

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
