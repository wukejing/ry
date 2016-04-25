package com.mv.domain;

import com.mv.domain.base.BaseDomain;

/**
 * erp角色实体
 * @author Administrator
 *
 */
public class ErpRole extends BaseDomain {

	private static final long serialVersionUID = 8903162125388669096L;

	/**
	 * 角色名
	 */
	private String roleName;

	/**
	 * 备注
	 */
	private String remark;
	
	public ErpRole() {
		// 默认无参构造方法
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}