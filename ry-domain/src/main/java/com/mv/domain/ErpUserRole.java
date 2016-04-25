package com.mv.domain;

import com.mv.domain.base.BaseDomain;

/**
 * erp用户角色关联
 * 
 * @author Administrator
 *
 */
public class ErpUserRole extends BaseDomain {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 角色id
	 */
	private Long roleId;

	public ErpUserRole() {
		// 默认无参构造方法
	}

	public Long getRoleId() {
		return roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}