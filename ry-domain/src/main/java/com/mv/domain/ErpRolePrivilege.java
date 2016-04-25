package com.mv.domain;

import com.mv.domain.base.BaseDomain;

/**
 * erp角色权限关联
 * @author Administrator
 *
 */
public class ErpRolePrivilege extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private Long roleId;
	private Long privilegeId;

	public ErpRolePrivilege(){
		//默认无参构造方法
	}

	/**
	 * 获取 roleId
	 * @return
	 */
	public Long getRoleId(){
		return roleId;
	}
	
	/**
	 * 设置 roleId
	 * @param roleId
	 */
	public void setRoleId(Long roleId){
		this.roleId = roleId;
	}

	/**
	 * 获取 privilegeId
	 * @return
	 */
	public Long getPrivilegeId(){
		return privilegeId;
	}
	
	/**
	 * 设置 privilegeId
	 * @param privilegeId
	 */
	public void setPrivilegeId(Long privilegeId){
		this.privilegeId = privilegeId;
	}
}