package com.mv.service;

import java.util.List;

import com.mv.domain.ErpRole;
import com.mv.service.base.BaseService;

public interface ErpRoleService extends BaseService<ErpRole, Long> {

	/**
	 * 角色授权
	 * 
	 * @param roleId
	 * @param privilegeList
	 */
	public void grant(Long roleId, List<Long> privilegeList);
}