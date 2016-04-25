package com.mv.domain;

import java.util.List;

import com.mv.domain.base.BaseDomain;

/**
 * erp用户
 * @author Administrator
 *
 */
public class ErpUser extends BaseDomain {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 登录id
	 */
	private String loginId;
	
	/**
	 * 登录密码
	 */
	private String password;
	
	/**
	 * 用户姓名
	 */
	private String userName;
	
	/**
	 * 工号
	 */
	private String userNo;
	
	/**
	 * 角色id列表
	 */
	private List<Long> roleIds ;

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public ErpUser(){
		//默认无参构造方法
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

}